package com.rperaza.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rperaza.app.repository.AsesorRepositorio;
import com.rperaza.app.repository.TesisRepositorio;
import com.rperaza.app.repository.TesistaRepositorio;
import com.rperaza.app.model.Tesis;
import com.rperaza.app.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class TesisControlador {
    private Tesis tesis;
    
    @Autowired
    TesisRepositorio tesisRepositorio;
    
    @Autowired
    TesistaRepositorio tesistaRepositorio;
    
    @Autowired
    AsesorRepositorio asesorRepositorio;

    @GetMapping("/tesis")
    public Page<Tesis> getTesis(Pageable pageable) {
        return tesisRepositorio.findAll(pageable);
    }

    @GetMapping("/tesis/{id}")
    public Tesis getTesisById(@PathVariable(value = "id") Integer tesisId) {
        return tesisRepositorio.findById(tesisId)
                .orElseThrow(() -> new ResourceNotFoundException("Tesis", "id", tesisId));
    }
    
    @GetMapping("/tesista/{tesistaId}/tesis")
    public Page<Tesis> getTesisByTesistaId(@PathVariable (value = "tesistaId") Integer tesistaId, Pageable pageable) {
        return tesisRepositorio.findByTesistaId(tesistaId, pageable);
        }
    
    @GetMapping("/asesor/{asesorId}/tesis")
    public Page<Tesis> getTesisByAsesorId(@PathVariable (value = "asesorId") Integer asesorId, Pageable pageable) {
        return tesisRepositorio.findByAsesorId(asesorId, pageable);
        }
    
    @PostMapping("tesista/{tesistaId}/asesor/{asesorId}/tesis")
    public Tesis createTesis(@PathVariable (value = "tesistaId") Integer tesistaId,@PathVariable (value = "asesorId") Integer asesorId,
                                 @Validated @RequestBody Tesis tesis) {
        this.tesis = tesis;
        tesistaRepositorio.findById(tesistaId).map(tesista -> {
            this.tesis.setTesista(tesista);
            return this.tesis;
        }).orElseThrow(() -> new ResourceNotFoundException("Tesista ","id",tesistaId));
        
        asesorRepositorio.findById(asesorId).map(asesor -> {
            this.tesis.setAsesor(asesor);
            return this.tesis;
        }).orElseThrow(() -> new ResourceNotFoundException("Asesor ","id",asesorId));
        
        return tesisRepositorio.save(tesis);
    }

    @PutMapping("/tesista/{tesistaId}/asesor/{asesorId}/tesis/{tesisId}")
    public Tesis updateTesis(@PathVariable (value = "tesistaId") Integer tesistaId,
                                 @PathVariable (value = "asesorId") Integer asesorId,
                                 @PathVariable (value = "tesisId") Integer tesisId,
                                 @Validated @RequestBody Tesis tesisRequest) {
        if(!tesistaRepositorio.existsById(tesistaId)) {
            throw new ResourceNotFoundException("Tesista ","id",tesistaId);
        }
        
        if(!asesorRepositorio.existsById(asesorId)) {
            throw new ResourceNotFoundException("Asesor ","id",asesorId);
        }

        return tesisRepositorio.findById(tesisId).map(tesis -> {
            tesis.setTitulo(tesisRequest.getTitulo());
            tesis.setFecha(tesisRequest.getFecha());
            return tesisRepositorio.save(tesis);
        }).orElseThrow(() -> new ResourceNotFoundException("Tesis ", "id", tesisId));
    }

    @DeleteMapping("/tesis/{id}")
    public ResponseEntity<?> deleteTesis(@PathVariable(value = "id") Integer tesisId) {
        Tesis tesis = tesisRepositorio.findById(tesisId)
                .orElseThrow(() -> new ResourceNotFoundException("Tesis", "id", tesisId));

        tesisRepositorio.delete(tesis);

        return ResponseEntity.ok().build();
    }
}

package com.rperaza.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.rperaza.app.model.Asesor;
import com.rperaza.app.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class AsesorControlador {
    @Autowired
    AsesorRepositorio asesorRepositorio;
    
    @Autowired
    TesisRepositorio tesisRepositorio;

    @GetMapping("/asesor")
    public List<Asesor> getAsesores() {
        return asesorRepositorio.findAll();
    }

    @PostMapping("/asesor")
    public Asesor createAsesor(@Validated @RequestBody Asesor asesor) {
        return asesorRepositorio.save(asesor);
    }

    @GetMapping("/asesor/{id}")
    public Asesor getAsesorById(@PathVariable(value = "id") Integer asesorId) {
        return asesorRepositorio.findById(asesorId)
                .orElseThrow(() -> new ResourceNotFoundException("Asesor", "id", asesorId));
    }

    @PutMapping("/asesor/{id}")
    public Asesor updateAsesor(@PathVariable(value = "id") Integer asesorId,
                                            @Validated @RequestBody Asesor nuevoAsesor) {

        Asesor asesor = asesorRepositorio.findById(asesorId)
                .orElseThrow(() -> new ResourceNotFoundException("Asesor", "id", asesorId));

        asesor.setClaveAsesor(nuevoAsesor.getClaveAsesor());
        asesor.setArea(nuevoAsesor.getArea());
        asesor.setNombre(nuevoAsesor.getNombre());
        asesor.setApellidoPaterno(nuevoAsesor.getApellidoPaterno());
        asesor.setApellidoMaterno(nuevoAsesor.getApellidoMaterno());

        Asesor asesorActualizado = asesorRepositorio.save(asesor);
        return asesorActualizado;
    }

    @DeleteMapping("/asesor/{id}")
    public ResponseEntity<?> deleteAsesor(@PathVariable(value = "id") Integer asesorId) {
        Asesor asesor = asesorRepositorio.findById(asesorId)
                .orElseThrow(() -> new ResourceNotFoundException("Asesor", "id", asesorId));

        asesorRepositorio.delete(asesor);

        return ResponseEntity.ok().build();
    }
}
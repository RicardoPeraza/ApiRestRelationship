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


import com.rperaza.app.repository.TesisRepositorio;
import com.rperaza.app.repository.TesistaRepositorio;

import com.rperaza.app.model.Tesista;
import com.rperaza.app.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api")
public class TesistaControlador {
    @Autowired
    TesistaRepositorio tesistaRepositorio;
    
    @Autowired
    TesisRepositorio tesisRepositorio;

    @GetMapping("/tesista")
    public List<Tesista> getTesistas() {
        return tesistaRepositorio.findAll();
    }

    @PostMapping("/tesista")
    public Tesista createTesista(@Validated @RequestBody Tesista tesista) {
        return tesistaRepositorio.save(tesista);
    }

    @GetMapping("/tesista/{id}")
    public Tesista getTesistaById(@PathVariable(value = "id") Integer tesistaId) {
        return tesistaRepositorio.findById(tesistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tesista", "id", tesistaId));
    }

    @PutMapping("/tesista/{id}")
    public Tesista updateTesista(@PathVariable(value = "id") Integer tesistaId,
                                            @Validated @RequestBody Tesista nuevoTesista) {

        Tesista tesista = tesistaRepositorio.findById(tesistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tesista", "id", tesistaId));

        tesista.setNumeroCuenta(nuevoTesista.getNumeroCuenta());
        tesista.setNombre(nuevoTesista.getNombre());
        tesista.setApellidoPaterno(nuevoTesista.getApellidoPaterno());
        tesista.setApellidoMaterno(nuevoTesista.getApellidoMaterno());

        Tesista tesistaActualizado = tesistaRepositorio.save(tesista);
        return tesistaActualizado;
    }

    @DeleteMapping("/tesista/{id}")
    public ResponseEntity<?> deleteTesista(@PathVariable(value = "id") Integer tesistaId) {
        Tesista tesista = tesistaRepositorio.findById(tesistaId)
                .orElseThrow(() -> new ResourceNotFoundException("Tesista", "id", tesistaId));

        tesistaRepositorio.delete(tesista);

        return ResponseEntity.ok().build();
    }
}

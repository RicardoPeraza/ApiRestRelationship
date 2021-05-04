package com.rperaza.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rperaza.app.model.Asesor;

@Repository
public interface AsesorRepositorio extends JpaRepository<Asesor, Integer> {
}
package com.rperaza.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rperaza.app.model.Tesista;

@Repository
public interface TesistaRepositorio extends JpaRepository<Tesista, Integer> {
}
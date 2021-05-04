package com.rperaza.app.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rperaza.app.model.Tesis;

@Repository
public interface TesisRepositorio extends JpaRepository<Tesis, Integer> {
	Page<Tesis> findByTesistaId(Integer tesistaId, org.springframework.data.domain.Pageable pageable);

	Page<Tesis> findByAsesorId(Integer asesorId, org.springframework.data.domain.Pageable pageable);
}

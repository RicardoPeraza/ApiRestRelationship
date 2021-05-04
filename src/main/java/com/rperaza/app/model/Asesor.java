package com.rperaza.app.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "asesores")
public class Asesor extends Persona implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String claveAsesor;

	private String area;

	@OneToMany(mappedBy = "asesor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tesis> tesis = new ArrayList<>();

	// getters y setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClaveAsesor() {
		return claveAsesor;
	}

	public void setClaveAsesor(String claveAsesor) {
		this.claveAsesor = claveAsesor;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<Tesis> getTesis() {
		return tesis;
	}

	public void setTesis(List<Tesis> tesis) {
		this.tesis = tesis;
	}

}
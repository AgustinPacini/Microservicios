package com.formacionbdi.microservicios.app.deportes.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="deportes_registros")
public class DeporteRegistro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="registro_id", unique = true)
	private Long registroId;
	
	@JsonIgnoreProperties(value= {"deporteRegistros"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="deporte_id")
	private Deporte deporte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRegistroId() {
		return registroId;
	}

	public void setRegistroId(Long registroId) {
		this.registroId = registroId;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	@Override
	public boolean equals(Object obj) {

		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof DeporteRegistro)) {
			return false;
		}

		DeporteRegistro a = (DeporteRegistro) obj;
		
		return this.registroId != null && this.registroId.equals(a.getRegistroId());
	}
	
	
}

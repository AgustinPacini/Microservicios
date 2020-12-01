package com.formacionbdi.microservicios.app.respuestas.models.entity;

import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@Document(collection = "respuestas")
public class Respuesta {
	
	@Id
	private String id;
	
	private String texto;

	//@Transient
	private Registro registro;

	private Long registroId;

	//@Transient
	private Pregunta pregunta;
	
	private Long preguntaId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Registro getRegistro() {
		return registro;
	}

	public void setRegistro(Registro registro) {
		this.registro = registro;
	}

	public Pregunta getPregunta() {
		return pregunta;
	}

	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}

	public Long getRegistroId() {
		return registroId;
	}

	public void setRegistroId(Long registroId) {
		this.registroId = registroId;
	}

	public Long getPreguntaId() {
		return preguntaId;
	}

	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}
	
	
}

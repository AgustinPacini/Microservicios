package com.formacionbdi.microservicios.app.respuestas.services;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {

	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public Iterable<Respuesta> findRespuestaByRegistroByExamen(Long registroId, Long examenId);
	
	public Iterable<Long> findExamenesIdsConRespuestasByRegistro(Long registroId);
	
	public Iterable<Respuesta> findByRegistroId(Long registroId);
}

package com.formacionbdi.microservicios.app.respuestas.models.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {

	@Query("{'registroId': ?0, 'preguntaId': { $in: ?1} }")
	public Iterable<Respuesta> findRespuestaByRegistroByPreguntaIds(Long registroId, Iterable<Long> preguntaIds);
	
	@Query("{'registroId': ?0}")
	public Iterable<Respuesta> findByRegistroId(Long registroId);
	
	//@Query("select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.registroId=?1 and e.id=?2")
	//public Iterable<Respuesta> findRespuestaByRegistroByExamen(Long registroId, Long examenId);
	
	//@Query("select e.id from Respuesta r join r.pregunta p join p.examen e where r.registroId=?1 group by e.id")
	//public Iterable<Long> findExamenesIdsConRespuestasByRegistro(Long registroId);
	
	@Query("{'registroId': ?0, 'pregunta.examen.id': ?1}")
	public Iterable<Respuesta> findRespuestaByRegistroByExamen(Long registroId, Long examenId);
	
	@Query(value = "{'registroId': ?0}", fields = "{'pregunta.examen.id': 1}")
	public Iterable<Respuesta> findExamenesIdsConRespuestasByRegistro(Long registroId);
}

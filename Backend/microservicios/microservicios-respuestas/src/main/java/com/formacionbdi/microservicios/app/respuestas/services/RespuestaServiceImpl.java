package com.formacionbdi.microservicios.app.respuestas.services;

//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.formacionbdi.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.models.repository.RespuestaRepository;
//import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
//import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository repository;
	
	//@Autowired
	//private ExamenFeignClient examenClient;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return repository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByRegistroByExamen(Long registroId, Long examenId) {
		/*Examen examen = examenClient.obtenerExamenPorId(examenId);
		List<Pregunta> preguntas = examen.getPreguntas();
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByRegistroByPreguntaIds(registroId, preguntaIds);
		respuestas = respuestas.stream().map(r ->{
			preguntas.forEach(p ->{
				if(p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());*/
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByRegistroByExamen(registroId, examenId);
		return respuestas;
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByRegistro(Long registroId) {
		/*List<Respuesta> respuestasRegistro = (List<Respuesta>) repository.findByRegistroId(registroId);
		List<Long> examenIds = Collections.emptyList();
		
		if(respuestasRegistro.size() > 0) {
		  List<Long> preguntaIds = respuestasRegistro.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());
		  examenIds = examenClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
		}*/
		List<Respuesta> respuestasRegistro = (List<Respuesta>) repository.findExamenesIdsConRespuestasByRegistro(registroId);
		List<Long> examenIds = respuestasRegistro
				.stream()
				.map(r -> r.getPregunta().getExamen().getId())
				.distinct()
				.collect(Collectors.toList());
		
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByRegistroId(Long registroId) {
		return repository.findByRegistroId(registroId);
	}

}

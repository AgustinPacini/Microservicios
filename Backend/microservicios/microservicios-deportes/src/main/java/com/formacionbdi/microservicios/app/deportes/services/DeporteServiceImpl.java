package com.formacionbdi.microservicios.app.deportes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.deportes.clients.RegistroFeignClient;
import com.formacionbdi.microservicios.app.deportes.clients.RespuestaFeignClient;
import com.formacionbdi.microservicios.app.deportes.models.entity.Deporte;
import com.formacionbdi.microservicios.app.deportes.models.repository.DeporteRepository;
import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;

@Service
public class DeporteServiceImpl extends CommonServiceImpl<Deporte, DeporteRepository> implements DeporteService {

	@Autowired
	private RespuestaFeignClient client;
	
	@Autowired
	private RegistroFeignClient clientRegistro;
	
	@Override
	@Transactional(readOnly = true)
	public Deporte findDeporteByRegistroId(Long id) {
		return repository.findDeporteByRegistroId(id);
	}

	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasRegistro(Long registroId) {
		return client.obtenerExamenesIdsConRespuestasRegistro(registroId);
	}

	@Override
	public Iterable<Registro> obtenerRegistrosPorDeporte(Iterable<Long> ids) {
		return clientRegistro.obtenerRegistrosPorDeporte(ids);
	}

	@Override
	@Transactional
	public void eliminarDeporteRegistroPorId(Long id) {
		repository.eliminarDeporteRegistroPorId(id);
	}

}

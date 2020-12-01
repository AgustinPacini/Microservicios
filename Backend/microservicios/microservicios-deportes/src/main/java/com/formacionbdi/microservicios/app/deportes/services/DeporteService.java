package com.formacionbdi.microservicios.app.deportes.services;

import com.formacionbdi.microservicios.app.deportes.models.entity.Deporte;
import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;
import com.formacionbdi.microservicios.commons.services.CommonService;

public interface DeporteService extends CommonService<Deporte> {

	public Deporte findDeporteByRegistroId(Long id);
	
	public Iterable<Long> obtenerExamenesIdsConRespuestasRegistro(Long registroId);
	
	public Iterable<Registro> obtenerRegistrosPorDeporte(Iterable<Long> ids);
	
	public void eliminarDeporteRegistroPorId(Long id);
}

package com.formacionbdi.microservicios.app.usuarios.services;

import java.util.List;

import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;
import com.formacionbdi.microservicios.commons.services.CommonService;

public interface RegistroService extends CommonService<Registro>{
	
	public List<Registro> findByNombreOrApellido(String term);
	
	public Iterable<Registro> findAllById(Iterable<Long> ids);
	
	public void eliminarDeporteRegistroPorId(Long id);
}

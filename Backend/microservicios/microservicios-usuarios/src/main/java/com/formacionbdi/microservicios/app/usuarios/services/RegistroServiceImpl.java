package com.formacionbdi.microservicios.app.usuarios.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.usuarios.client.DeporteFeignClient;
import com.formacionbdi.microservicios.app.usuarios.models.repository.RegistroRepository;
import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;

@Service
public class RegistroServiceImpl extends CommonServiceImpl<Registro, RegistroRepository> implements RegistroService {

	@Autowired
	private DeporteFeignClient clientDeporte;
	
	@Override
	@Transactional(readOnly = true)
	public List<Registro> findByNombreOrApellido(String term) {
		return repository.findByNombreOrApellido(term);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Registro> findAllById(Iterable<Long> ids) {
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarDeporteRegistroPorId(Long id) {
		clientDeporte.eliminarDeporteRegistroPorId(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.eliminarDeporteRegistroPorId(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Registro> findAll() {
		return repository.findAllByOrderByIdAsc();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Registro> findAll(Pageable pageable) {
		return repository.findAllByOrderByIdAsc(pageable);
	}
	

}

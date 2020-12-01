package com.formacionbdi.microservicios.app.usuarios.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservicio-deportes")
public interface DeporteFeignClient {

	@DeleteMapping("/eliminar-registro/{id}")
	public void eliminarDeporteRegistroPorId(@PathVariable Long id);
}

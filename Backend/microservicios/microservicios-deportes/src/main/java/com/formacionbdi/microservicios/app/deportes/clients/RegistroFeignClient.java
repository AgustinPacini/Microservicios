package com.formacionbdi.microservicios.app.deportes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;

@FeignClient(name = "microservicio-usuarios")
public interface RegistroFeignClient {

	@GetMapping("/registros-por-deporte")
	public Iterable<Registro> obtenerRegistrosPorDeporte(@RequestParam Iterable<Long> ids);
}

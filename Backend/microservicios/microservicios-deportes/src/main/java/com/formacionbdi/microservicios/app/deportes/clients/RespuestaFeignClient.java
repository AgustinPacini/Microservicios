package com.formacionbdi.microservicios.app.deportes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservicio-respuestas")
public interface RespuestaFeignClient {

	@GetMapping("/registro/{registroId}/examenes-respondidos")
	public Iterable<Long> obtenerExamenesIdsConRespuestasRegistro(@PathVariable Long registroId);
}

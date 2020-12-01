package com.formacionbdi.microservicios.app.deportes.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.app.deportes.models.entity.Deporte;

public interface DeporteRepository extends PagingAndSortingRepository<Deporte, Long>{

	@Query("select c from Deporte c join fetch c.deporteRegistros a where a.registroId=?1")
	public Deporte findDeporteByRegistroId(Long id);
	
	@Modifying
	@Query("delete from DeporteRegistro ca where ca.registroId=?1")
	public void eliminarDeporteRegistroPorId(Long id);
}

package com.formacionbdi.microservicios.app.usuarios.models.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;

public interface RegistroRepository extends PagingAndSortingRepository<Registro, Long> {

	@Query("select a from Registro a where upper(a.nombre) like upper(concat('%', ?1, '%')) or upper(a.apellido) like upper(concat('%', ?1, '%'))")
	public List<Registro> findByNombreOrApellido(String term);
	
	public Iterable<Registro> findAllByOrderByIdAsc();
	
	public Page<Registro> findAllByOrderByIdAsc(Pageable pageable);
}

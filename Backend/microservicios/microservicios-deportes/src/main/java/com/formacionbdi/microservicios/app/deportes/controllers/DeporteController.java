package com.formacionbdi.microservicios.app.deportes.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.deportes.models.entity.Deporte;
import com.formacionbdi.microservicios.app.deportes.models.entity.DeporteRegistro;
import com.formacionbdi.microservicios.app.deportes.services.DeporteService;
import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

@RestController
public class DeporteController extends CommonController<Deporte, DeporteService>{

	@Value("${config.balanceador.test}")
	private String balanceadorTest;
	
	@DeleteMapping("/eliminar-registro/{id}")
	public ResponseEntity<?> eliminarDeporteRegistroPorId(@PathVariable Long id){
		service.eliminarDeporteRegistroPorId(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@Override
	public ResponseEntity<?> listar(){
		List<Deporte> deportes = ((List<Deporte>) service.findAll()).stream().map(c ->{
			c.getDeporteRegistros().forEach(ca ->{
				Registro registro  = new Registro();
				registro.setId(ca.getRegistroId());
				c.addRegistro(registro);
			});
			return c;
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(deportes);
	}
	
	@GetMapping("/pagina")
	@Override
	public ResponseEntity<?> listar(Pageable pageable){
		Page<Deporte> deportes = service.findAll(pageable).map(deporte ->{
			deporte.getDeporteRegistros().forEach(ca ->{
				Registro registro  = new Registro();
				registro.setId(ca.getRegistroId());
				deporte.addRegistro(registro);
			});
			return deporte;
		});
		
		return ResponseEntity.ok().body(deportes);
	}
	
	@GetMapping("/{id}")
	@Override
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<Deporte> o = service.findById(id);
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Deporte deporte = o.get();
		
		if(deporte.getDeporteRegistros().isEmpty() == false) {
			
			List<Long> ids = deporte.getDeporteRegistros().stream().map(ca -> ca.getRegistroId())
					.collect(Collectors.toList());
			
			List<Registro> registros = (List<Registro>) service.obtenerRegistrosPorDeporte(ids);
			
			deporte.setRegistros(registros);
			
		}
		
		return ResponseEntity.ok().body(deporte);
	}
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("deportes", service.findAll());
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Deporte deporte, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
	    Optional<Deporte> o = this.service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Deporte dbDeporte = o.get();
	    dbDeporte.setNombre(deporte.getNombre());
	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbDeporte));
	}
	
	@PutMapping("/{id}/asignar-registros")
	public ResponseEntity<?> asignarRegistros(@RequestBody List<Registro> registros, @PathVariable Long id){
	    Optional<Deporte> o = this.service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Deporte dbDeporte = o.get();
	    
	    registros.forEach(a -> {
	    	DeporteRegistro deporteRegistro = new DeporteRegistro();
	    	deporteRegistro.setRegistroId(a.getId());
	    	deporteRegistro.setDeporte(dbDeporte);
	    	dbDeporte.addDeporteRegistro(deporteRegistro);
	    });
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbDeporte));
	}
	
	@PutMapping("/{id}/eliminar-registro")
	public ResponseEntity<?> eliminarRegistro(@RequestBody Registro registro, @PathVariable Long id){
	    Optional<Deporte> o = this.service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Deporte dbDeporte = o.get();
	    DeporteRegistro deporteRegistro = new DeporteRegistro();
	    deporteRegistro.setRegistroId(registro.getId());
	    dbDeporte.removeDeporteRegistro(deporteRegistro);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbDeporte));
	}
	
	@GetMapping("/registro/{id}")
	public ResponseEntity<?> buscarPorRegistroId(@PathVariable Long id){
		Deporte deporte = service.findDeporteByRegistroId(id);
		
		if(deporte != null) {
			
			List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasRegistro(id);
			
			if (examenesIds != null && examenesIds.size() > 0) {
				List<Examen> examenes = deporte.getExamenes().stream().map(examen -> {
					if (examenesIds.contains(examen.getId())) {
						examen.setRespondido(true);
					}
					return examen;
				}).collect(Collectors.toList());

				deporte.setExamenes(examenes);
			}
		}
		return ResponseEntity.ok(deporte);
	}
	
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examen> examenes, @PathVariable Long id){
	    Optional<Deporte> o = this.service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Deporte dbDeporte = o.get();
	    
	    examenes.forEach(e -> {
	    	dbDeporte.addExamen(e);
	    });
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbDeporte));
	}
	
	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examen examen, @PathVariable Long id){
	    Optional<Deporte> o = this.service.findById(id);
	    if(!o.isPresent()) {
	    	return ResponseEntity.notFound().build();
	    }
	    Deporte dbDeporte = o.get();
	    
	    dbDeporte.removeExamen(examen);
	    
	    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbDeporte));
	}
}

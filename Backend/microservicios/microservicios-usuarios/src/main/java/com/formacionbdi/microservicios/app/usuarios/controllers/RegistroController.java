package com.formacionbdi.microservicios.app.usuarios.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.formacionbdi.microservicios.app.usuarios.services.RegistroService;
import com.formacionbdi.microservicios.commons.registros.models.entity.Registro;
import com.formacionbdi.microservicios.commons.controllers.CommonController;

@RestController
public class RegistroController extends CommonController<Registro, RegistroService>{

	@GetMapping("/registros-por-deporte")
	public ResponseEntity<?> obtenerRegistrosPorDeporte(@RequestParam List<Long> ids){
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
	
		Optional<Registro> o = service.findById(id);
		
		if(o.isEmpty() || o.get().getFoto() == null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource imagen = new ByteArrayResource(o.get().getFoto());
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(imagen);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Registro registro, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Registro> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Registro registroDb = o.get();
		registroDb.setNombre(registro.getNombre());
		registroDb.setApellido(registro.getApellido());
		registroDb.setEmail(registro.getEmail());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(registroDb));
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?> filtrar(@PathVariable String term){
		return ResponseEntity.ok(service.findByNombreOrApellido(term));
	}

	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Registro registro, BindingResult result, 
			@RequestParam MultipartFile archivo) throws IOException {
		if(!archivo.isEmpty()) {
			registro.setFoto(archivo.getBytes());
		}
		return super.crear(registro, result);
	}
	
	@PutMapping("/editar-con-foto/{id}")
	public ResponseEntity<?> editarConFoto(@Valid Registro registro, BindingResult result, @PathVariable Long id,
			@RequestParam MultipartFile archivo) throws IOException{
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		
		Optional<Registro> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Registro registroDb = o.get();
		registroDb.setNombre(registro.getNombre());
		registroDb.setApellido(registro.getApellido());
		registroDb.setEmail(registro.getEmail());
		
		if(!archivo.isEmpty()) {
			registroDb.setFoto(archivo.getBytes());
		}
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(registroDb));
	}

}

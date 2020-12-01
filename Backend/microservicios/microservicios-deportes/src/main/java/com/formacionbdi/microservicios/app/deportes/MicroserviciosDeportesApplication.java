package com.formacionbdi.microservicios.app.deportes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.formacionbdi.microservicios.commons.examenes.models.entity",
	         "com.formacionbdi.microservicios.app.deportes.models.entity"})
public class MicroserviciosDeportesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosDeportesApplication.class, args);
	}

}

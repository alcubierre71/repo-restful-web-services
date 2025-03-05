package com.in28minutes.restfulwebservices.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.in28minutes.restfulwebservices.entity.SomeBean;

// Filtrado dinamico del controlador
// Cada REST API filtra y devuelve diferentes campos
@RestController
public class FilteringController {

	// Esta API filtra el field2, devolviendo field1 y field3 
	@GetMapping("/filtering") 
	public MappingJacksonValue filtering() {
		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		
		// Establecemos filtrado Jackson sobre los campos del Bean indicado
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		
		// Definimos filtro
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		
		// Construimos el proveedor de filtros
		// Le asociamos un filtro con el nombre que tenga configurado en el Bean
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
				
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
	
	// Esta API filtra el field1, devolviendo field2 y field3
	@GetMapping("/filtering-list") 
	public MappingJacksonValue filteringList() {
		List<SomeBean> lista = Arrays.asList( new SomeBean("value1", "value2", "value3"),
				new SomeBean("value4", "value5", "value6")
				);
		
		// Establecemos filtrado Jackson sobre los campos del Bean indicado
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(lista);
		
		// Definimos filtro
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		
		// Construimos el proveedor de filtros
		// Le asociamos un filtro con el nombre que tenga configurado en el Bean
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
				
		mappingJacksonValue.setFilters(filters);	
		
		return mappingJacksonValue;
	}
	
}

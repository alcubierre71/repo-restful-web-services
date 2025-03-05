package com.in28minutes.restfulwebservices.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// REST API
@RestController
public class HelloWorldController {

	private MessageSource messageSource;
	
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	// endpoint /hello-world
	//@RequestMapping(method=RequestMethod.GET, path="/hello-world")
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello World";
	}
	
	// Response es automaticamente convertido en un JSON por SpringBoot 
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		HelloWorldBean helloWorldBean = new HelloWorldBean("Hello World Bean");
		
		return helloWorldBean;
	}
	
	// Path Parameters
	// /hello-world/path-variable/{name}
	@GetMapping(path="/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		HelloWorldBean helloWorldBean = new HelloWorldBean("Hello World: " + name);
		
		return helloWorldBean;
	}
	
	@GetMapping(path="/hello-world-inter")
	public String helloWorldInter() {
		
		// Obtencion de parametros de la Localizacion del usuario
		Locale locale = LocaleContextHolder.getLocale();
		
		// Obtener mensaje parametrizado en message.properties 
		String message = messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		
		return message;
		
	}
	
}

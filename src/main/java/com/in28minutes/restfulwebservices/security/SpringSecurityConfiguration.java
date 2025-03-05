package com.in28minutes.restfulwebservices.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SpringSecurityConfiguration {

	// Filtros de seguridad de la aplicacion
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// Autenticacion de request
		http.authorizeHttpRequests( auth -> auth.anyRequest().authenticated() );
		
		// Peticiones no autenticadas
		http.httpBasic(withDefaults());

		// Deshabilitar proteccion CSRF (Cross Site Request Forgery)
		http.csrf( csrf -> csrf.disable() );
		
		
		return http.build();
		
	}
	
}

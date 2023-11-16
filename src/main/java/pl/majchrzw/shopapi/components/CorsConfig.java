package pl.majchrzw.shopapi.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		// Ustawienia dozwolonych źródeł (np. "http://localhost:3000")
		config.addAllowedOrigin("*");
		
		// Ustawienia dozwolonych metod HTTP (np. GET, POST)
		config.addAllowedMethod("*");
		
		// Ustawienia dozwolonych nagłówków
		config.addAllowedHeader("*");
		
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}

package br.com.dev.restwithspringbootudemy.configs;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.dev.restwithspringbootudemy.serialization.converter.YamlJackson2HttpMessageConverter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new YamlJackson2HttpMessageConverter());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD", "TRACE",
				"CONNECT");
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

		/*
		 * VIA EXTENSION configurer.favorParameter(false) .ignoreAcceptHeader(false)
		 * .defaultContentType(MediaType.APPLICATION_JSON) .mediaType("json",
		 * MediaType.APPLICATION_JSON) .mediaType("xml", MediaType.APPLICATION_XML);
		 */

		/*
		 * VIA QUERY PARAM configurer.favorPathExtension(false) .favorParameter(true)
		 * .parameterName("mediaType") .ignoreAcceptHeader(true)
		 * .useRegisteredExtensionsOnly(false)
		 * .defaultContentType(MediaType.APPLICATION_JSON) .mediaType("json",
		 * MediaType.APPLICATION_JSON) .mediaType("xml", MediaType.APPLICATION_XML);
		 */

		// VIA HEADER
		configurer.favorPathExtension(false).favorParameter(false).ignoreAcceptHeader(false)
				.useRegisteredExtensionsOnly(false).defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON).mediaType("xml", MediaType.APPLICATION_XML)
				.mediaType("x-yaml", MediaType.valueOf("application/x-yaml"));
	}

}

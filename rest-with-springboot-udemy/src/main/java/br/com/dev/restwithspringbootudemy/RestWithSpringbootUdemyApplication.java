package br.com.dev.restwithspringbootudemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import br.com.dev.restwithspringbootudemy.configs.FileStorageConfig;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageConfig.class })
@EnableAutoConfiguration
@ComponentScan
public class RestWithSpringbootUdemyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestWithSpringbootUdemyApplication.class, args);
	}

}

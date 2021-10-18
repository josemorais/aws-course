package br.com.dev.restwithspringbootudemy.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "file")
public class FileStorageConfig {
	
	private String uploadDir;

}

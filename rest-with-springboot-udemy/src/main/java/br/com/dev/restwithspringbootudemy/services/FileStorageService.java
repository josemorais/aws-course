package br.com.dev.restwithspringbootudemy.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import br.com.dev.restwithspringbootudemy.RestWithSpringbootUdemyApplication;
import br.com.dev.restwithspringbootudemy.configs.FileStorageConfig;
import br.com.dev.restwithspringbootudemy.exceptions.FileStorageException;
import br.com.dev.restwithspringbootudemy.exceptions.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;

	public FileStorageService(FileStorageConfig fileStorageConfig) {
		this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Cound not create the directory where the uploaded files will be stored", e);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			if (fileName.contains(".."))
				throw new FileStorageException("Sorry! Filename contains invalide path sequence " + fileName);

			Path targetLoccation = this.fileStorageLocation.resolve(fileName);
			Files.copy(file.getInputStream(), targetLoccation, StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Cound not store file " + fileName, e);
		}
	}

	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (!resource.exists())
				throw new MyFileNotFoundException("File not found " + fileName);
			return resource;
		} catch (Exception e) {
			throw new MyFileNotFoundException("File not found " + fileName, e);
		}
	}

}

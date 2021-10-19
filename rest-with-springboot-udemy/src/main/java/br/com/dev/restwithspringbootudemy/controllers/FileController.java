package br.com.dev.restwithspringbootudemy.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.dev.restwithspringbootudemy.data.vo.UploadFileResponseVO;
import br.com.dev.restwithspringbootudemy.services.FileStorageService;
import io.swagger.annotations.Api;

@Api(tags = "FileEndpoints")
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@PostMapping("/uploadFile")
	public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
		String fileName = fileStorageService.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/file/downloadFile/")
				.path(fileName).toUriString();
		return new UploadFileResponseVO(fileName, fileDownloadUri, file.getContentType(), file.getSize());
	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponseVO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> donwloadFile(@PathVariable String fileName, HttpServletRequest request) {

		Resource resource = fileStorageService.loadFileAsResource(fileName);

		String contentType = null;

		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

		} catch (Exception e) {
			logger.info("Could not determine file type!");
		}

		if (Objects.isNull(contentType))
			contentType = "application/octet-stream";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + resource.getFilename())
				.body(resource);

	}

}

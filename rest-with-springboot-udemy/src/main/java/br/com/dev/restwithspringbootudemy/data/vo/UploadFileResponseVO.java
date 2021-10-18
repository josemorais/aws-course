package br.com.dev.restwithspringbootudemy.data.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponseVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fileName;
	private String fileDownloadUri;
	private String fileType;
	private long size;
}

package com.dron.sender.config;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AppProperties {

	@Value("${file.path}")
	private String filePath;

	@Value("${tmp.import.postman.value.file.path}")
	private String tmpImportPostmanValueFilePath;

	@Value("${tmp.import.postman.request.file.path}")
	private String tmpImportPostmanRequestFilePath;

	@Value("${request.duration.time.minuts}")
	private Integer requestDuration;

	public Integer getRequestDuration() {
		return requestDuration;
	}

	public void setRequestDuration(Integer requestDuration) {
		this.requestDuration = requestDuration;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(File file) {
		this.filePath = getPath(file);
	}

	public String getTmpImportPostmanValueFilePath() {
		return tmpImportPostmanValueFilePath;
	}

	public void setTmpImportPostmanValueFilePath(
			File tmpImportPostmanValueFilePath) {
		this.tmpImportPostmanValueFilePath = tmpImportPostmanValueFilePath
				.getPath();
	}

	public String getTmpImportPostmanRequestFilePath() {
		return tmpImportPostmanRequestFilePath;
	}

	public void setTmpImportPostmanRequestFilePath(
			File tmpImportPostmanRequestFilePath) {
		this.tmpImportPostmanRequestFilePath = tmpImportPostmanRequestFilePath
				.getPath();
	}

	private String getPath(File tmpFilePath) {
		String filePath;
		if (tmpFilePath.isFile()) {
			filePath = StringUtils.substringBeforeLast(tmpFilePath.getPath(),
					File.separator);
		} else {
			filePath = tmpFilePath.getPath();
		}
		return filePath;
	}

}

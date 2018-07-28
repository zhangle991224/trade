package com.dm.trade.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="trade")
public class BootdoConfig {
	//上传路径
	private String uploadPath;

	private String othersPath;

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getOthersPath() {
		return othersPath;
	}

	public void setOthersPath(String othersPath) {
		this.othersPath = othersPath;
	}
}

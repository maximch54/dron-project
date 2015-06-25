package com.dron.sender.exim.parsers.postman.v2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data model for parse export values from POSTman V2
 * 
 * @author Koropatva A.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmanValueModel {

	private static final String DEFAULT_TYPE = "text";

	private String key;

	private String value;

	private String type;

	public PostmanValueModel() {
	}

	public PostmanValueModel(final String key, final String value) {
		this.key = key;
		this.value = value;
		this.type = DEFAULT_TYPE;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

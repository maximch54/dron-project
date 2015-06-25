package com.dron.sender.exim.parsers.postman.v2.models;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data model for parse export parameters from POSTman V2
 * 
 * @author Koropatva A.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmanValueStructureModel {

	private String id;

	private String name;

	private List<PostmanValueModel> values;

	private Timestamp timestamp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PostmanValueModel> getValues() {
		return values;
	}

	public void setValues(List<PostmanValueModel> values) {
		this.values = values;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}

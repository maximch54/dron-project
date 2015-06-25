package com.dron.sender.exim.parsers.postman.v2.models;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data model for parse export data from POSTman V2
 * 
 * @author Koropatva A.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmanModel {

	private String id;

	private String name;

	private Integer version;
	
	private Timestamp timestamp;

	private List<String> order;

	private List<RequestModel> requests;

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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getOrder() {
		return order;
	}

	public void setOrder(List<String> order) {
		this.order = order;
	}

	public List<RequestModel> getRequests() {
		return requests;
	}

	public void setRequests(List<RequestModel> requests) {
		this.requests = requests;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}

package com.dron.sender.exim.parsers.postman.v2.models;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data model for parse export data from POSTman V2
 * 
 * @author Koropatva A.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestModel {

	private String collectionId;

	private String id;

	private String name;

	private String description;

	private String url;

	private HttpMethod method;

	private String headers;

	private String data;

	private String dataMode;

	private Timestamp timestamp;

	private List<String> responses;

	private Integer version;

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDataMode() {
		return dataMode;
	}

	public void setDataMode(String dataMode) {
		this.dataMode = dataMode;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getResponses() {
		return responses;
	}

	public void setResponses(List<String> responses) {
		this.responses = responses;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}

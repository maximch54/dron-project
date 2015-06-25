package com.dron.sender.exim.parsers.postman.models;

import java.util.List;

import com.dron.sender.exim.parsers.postman.v2.models.RequestModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmanVersionModel {

	private List<RequestModel> requests;

	public List<RequestModel> getRequests() {
		return requests;
	}

	public void setRequests(List<RequestModel> requests) {
		this.requests = requests;
	}
}

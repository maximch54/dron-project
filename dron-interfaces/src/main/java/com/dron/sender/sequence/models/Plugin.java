package com.dron.sender.sequence.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import com.dron.sender.sequence.utils.ParamsUtils;

public class Plugin extends BaseNotificationModel implements Cloneable {

	public static final String PROPERTY_NAME = "Name";

	public static final String PROPERTY_HEADERS = "Headers";

	public static final String PROPERTY_SEQUENCE = "Sequence";

	public static final String PROPERTY_URL = "Url";

	public static final String PROPERTY_RESPONCE = "Responce";

	public static final String PROPERTY_POST_BODY = "PostBody";

	public static final String PROPERTY_HTTP_METHOD = "HttpMethod";

	public static final String PROPERTY_ID = "ID";

	public Plugin() {
		this(HttpMethod.GET);
	}

	public Plugin(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
		setHeaders(null);
		setId(UUID.randomUUID().toString());
		futureParams = new ArrayList<FutureParam>();
	}

	public void setHeaders(HttpHeaders headers) {
		if (headers == null) {
			headers = new HttpHeaders();
		}
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_JSON);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(mediaTypes);

		notifyListeners(this, PROPERTY_HEADERS, this.headers,
				this.headers = headers);
	}

	private Sequence sequence;

	private String id;

	private String name;

	private HttpHeaders headers;

	private HttpMethod httpMethod;

	private String url;

	private String postBody;

	private String responce;

	private boolean success;
	
	/**
	 * Structure of parameter way looks like: names of JSON object separated by
	 * dot. If you need to select someone object from the list you should to use
	 * "[numbers separated by comma]"
	 */
	private List<FutureParam> futureParams;

	public void addFutureParam(String key, String dependence) {
		sequence.getParams().add(new Param(key, null));
		this.futureParams.add(new FutureParam(key, dependence));
	}

	public HttpEntity<String> fillEntity() {
		return new HttpEntity<String>(ParamsUtils.fillDataParams(postBody,
				sequence.getParams()), headers);
	}

	public String fillUrl() {
		return ParamsUtils.fillDataParams(url, sequence.getParams());
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		notifyListeners(this, PROPERTY_HTTP_METHOD, this.httpMethod,
				this.httpMethod = httpMethod);
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public String getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		notifyListeners(this, PROPERTY_POST_BODY, this.postBody,
				this.postBody = postBody);
	}

	public String getResponce() {
		return responce;
	}

	public void setResponce(String responce) {
		notifyListeners(this, PROPERTY_RESPONCE, this.responce,
				this.responce = responce);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		notifyListeners(this, PROPERTY_URL, this.url, this.url = url);
	}

	public void setSequence(Sequence sequence) {
		notifyListeners(this, PROPERTY_SEQUENCE, this.sequence,
				this.sequence = sequence);
	}

	public Sequence getSequence() {
		return sequence;
	}

	public List<FutureParam> getFutureParams() {
		return futureParams;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		notifyListeners(this, PROPERTY_NAME, this.name, this.name = name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
		notifyListeners(this, PROPERTY_ID, this.id, this.id = id);
	}

	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@Override
	public Plugin clone() {
		Plugin plugin = new Plugin();
		plugin.setId(id);
		plugin.setName(name);
		plugin.setSuccess(success);
		plugin.setPostBody(postBody);
		plugin.setResponce(responce);
		plugin.setSequence(sequence);
		plugin.setUrl(url);
		plugin.setHeaders(headers);
		plugin.setHttpMethod(httpMethod);
		futureParams.forEach(futureParam -> plugin.getFutureParams().add(
				futureParam));
		return plugin;
	}

}
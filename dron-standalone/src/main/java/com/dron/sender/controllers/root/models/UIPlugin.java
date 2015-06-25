package com.dron.sender.controllers.root.models;

import java.io.IOException;
import java.util.UUID;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UIPlugin implements Cloneable {

	private ObjectMapper mapper = new ObjectMapper();

	private StringProperty id = new SimpleStringProperty();

	private final StringProperty url = new SimpleStringProperty();

	private final StringProperty postBody = new SimpleStringProperty();

	private final StringProperty method = new SimpleStringProperty();

	private final StringProperty name = new SimpleStringProperty();

	private final StringProperty responce = new SimpleStringProperty();

	private final BooleanProperty success = new SimpleBooleanProperty();

	private final ObservableList<UIHttpHeaders> headersList = FXCollections
			.observableArrayList();

	private final ObservableList<UIFutureParam> futureParams = FXCollections
			.observableArrayList();

	public UIPlugin() {
		prepareEmptyPlugin();
	}

	public void clear() {
		id.set(null);
		url.set("");
		postBody.set("");
		name.set("");
		responce.set("");
		success.set(true);
		headersList.clear();
		futureParams.clear();
	}

	public void prepareEmptyPlugin() {
		id.set(UUID.randomUUID().toString());
		name.set("Default plugin");
		method.set(HttpMethod.POST.name());
		headersList.add(new UIHttpHeaders());
		futureParams.add(new UIFutureParam());
	}

	public ObservableList<UIHttpHeaders> getHeadersList() {
		return headersList;
	}

	public ObservableList<UIFutureParam> getFutureParams() {
		return futureParams;
	}

	public StringProperty getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url.set(url);
	}

	public StringProperty getPostBody() {
		return postBody;
	}

	public void setPostBody(String postBody) {
		if (postBody != null) {
			try {
				Object json = mapper.readValue(postBody, Object.class);
				postBody = mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(json);
			} catch (IOException e) {
			}
		}
		this.postBody.set(postBody);
	}

	public StringProperty getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method.set(method);
	}

	public StringProperty getId() {
		return id;
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public StringProperty getName() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty getResponce() {
		return responce;
	}

	public void setResponce(String name) {
		this.responce.set(name);
	}

	public BooleanProperty isSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success.set(success);
	}

	@Override
	public UIPlugin clone() {
		UIPlugin plugin = new UIPlugin();
		plugin.setId(id.get());
		plugin.setMethod(method.get());
		plugin.setName(name.get());
		plugin.setResponce(responce.get());
		plugin.setSuccess(success.get());
		plugin.setPostBody(postBody.get());
		plugin.setUrl(url.get());
		plugin.getHeadersList().clear();
		headersList.forEach(header -> {
			plugin.getHeadersList().add(header.clone());
		});
		plugin.getFutureParams().clear();
		futureParams.forEach(futureParam -> {
			plugin.getFutureParams().add(futureParam.clone());
		});
		return plugin;
	}

	@Override
	public String toString() {
		return "UIPlugin [mapper=" + mapper + ", id=" + id + ", url=" + url
				+ ", postBody=" + postBody + ", method=" + method + ", name="
				+ name + ", responce=" + responce + ", headersList="
				+ headersList + ", futureParams=" + futureParams + "]";
	}

}

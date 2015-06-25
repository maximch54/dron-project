package com.dron.sender.controllers.base.models;

public enum ControllerEnum {
	ROOT("RootView", "rootController", "HOTPOST"),
	IMPORT_POSTMAN("ImportPostmanView", "importPostmanController", "POSTman import page");

	private String controllerName;
	private String viewName;
	private String title;

	private ControllerEnum(String viewName, String controllerName, String title) {
		this.controllerName = controllerName;
		this.viewName = viewName;
		this.title = title;
	}

	public String getControllerName() {
		return controllerName;
	}

	public String getViewName() {
		return viewName;
	}

	public String getTitle() {
		return title;
	}

}
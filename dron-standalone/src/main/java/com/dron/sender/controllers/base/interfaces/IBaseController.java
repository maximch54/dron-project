package com.dron.sender.controllers.base.interfaces;

import javafx.fxml.FXMLLoader;

import com.dron.sender.controllers.base.models.ControllerEnum;

public interface IBaseController {
	
	ControllerEnum getControllerEnum();
	
	FXMLLoader getLoader();
}

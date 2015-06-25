package com.dron.sender.controllers.base.services;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.ISceneService;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.base.models.ControllerEnum;

@Component
public class StageService implements IStageService {

	@Autowired
	private ISceneService iSceneService;

	private Stage primaryStage;

	private Stage dialogStage;

	public void initPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void showController(ControllerEnum controllerEnum) {
		primaryStage.setScene(iSceneService.findScene(controllerEnum));
		primaryStage.setTitle(controllerEnum.getTitle());
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.show();
	}

	@Override
	public void showDialog(ControllerEnum controllerEnum) {
		if (dialogStage != null && dialogStage.isShowing()) {
			return;
		}
		dialogStage = new Stage();
		dialogStage.setScene(iSceneService.findScene(controllerEnum));
		dialogStage.setTitle(controllerEnum.getTitle());
		dialogStage.initModality(Modality.APPLICATION_MODAL);
		dialogStage.initStyle(StageStyle.DECORATED);
		dialogStage.initOwner(primaryStage);
		dialogStage.toFront();
		dialogStage.showAndWait();
	}

	public void hideDialog() {
		dialogStage.hide();
		primaryStage.show();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public Stage getDialogStage() {
		return dialogStage;
	}

}

package com.dron.sender.controllers.base.interfaces;

import javafx.scene.Scene;

import com.dron.sender.controllers.base.models.ControllerEnum;

public interface ISceneService {
	Scene findScene(ControllerEnum controllerEnum);
}

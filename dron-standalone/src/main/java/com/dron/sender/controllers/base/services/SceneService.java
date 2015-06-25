package com.dron.sender.controllers.base.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.base.interfaces.ISceneService;
import com.dron.sender.controllers.base.models.ControllerEnum;

/**
 * Service works with scene. It can find scene or create new, if it in not
 * present.
 * 
 * @author admin
 *
 */
@Component
public class SceneService implements ISceneService {

	@Resource
	private ApplicationContext ctx;

	private Map<ControllerEnum, Scene> scenes;

	@PostConstruct
	private void setUp() {
		scenes = new HashMap<>();
	}

	@Override
	public Scene findScene(ControllerEnum controllerEnum) {
		Scene scene = scenes.get(controllerEnum);
		if (scene == null) {
			try {
				IBaseController baseController = (IBaseController) ctx
						.getBean(controllerEnum.getControllerName());
				scene = new Scene((Parent) baseController.getLoader().load());
				scene.getStylesheets().clear();
				scene.getStylesheets().add(SceneService.class.getClassLoader().getResource("control.css").toExternalForm());
				// Add new scene to the list
				scenes.put(controllerEnum, scene);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		return scene;
	}
}

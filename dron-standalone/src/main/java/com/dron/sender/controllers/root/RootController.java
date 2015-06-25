package com.dron.sender.controllers.root;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.base.interfaces.ISceneService;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.base.models.ControllerEnum;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.controllers.root.models.UISequence;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class RootController extends BaseRootController implements
		Initializable, IBaseController {

	@Autowired
	private IStageService iStageService;

	@Autowired
	private ISceneService iSceneService;

	@Autowired
	private ControllerStrategyContext context;

	private FXMLLoader loader;

	public RootController() {
		ClassLoader classLoader = RootController.class.getClassLoader();

		loader = new FXMLLoader(classLoader.getResource("RootView.fxml"));
		loader.setController(this);
	}

	@PostConstruct
	private void setUp() {
		Scene scene = iSceneService.findScene(getControllerEnum());
		scene.getAccelerators().put(
				new KeyCodeCombination(KeyCode.L, KeyCombination.META_DOWN),
				() -> {
					autoFillSequenceTextBox.requestFocus();
				});
		scene.getAccelerators().put(
				new KeyCodeCombination(KeyCode.F11, KeyCombination.META_DOWN,
						KeyCombination.SHIFT_DOWN),
				() -> {
					context.execute(this,
							ControllerActionStrategy.ROOT_SEND_SEQUENCE);
				});
		scene.getAccelerators().put(
				new KeyCodeCombination(KeyCode.F5, KeyCombination.META_DOWN),
				() -> {
					context.execute(this,
							ControllerActionStrategy.ROOT_SEND_PLUGIN);
				});
		scene.getAccelerators().put(
				new KeyCodeCombination(KeyCode.P, KeyCombination.META_DOWN,
						KeyCombination.SHIFT_DOWN), () -> {
					iStageService.showDialog(ControllerEnum.IMPORT_POSTMAN);
				});
		scene.getAccelerators().put(
				new KeyCodeCombination(KeyCode.DOWN, KeyCombination.META_DOWN,
						KeyCombination.SHIFT_DOWN),
				() -> {
					context.execute(this,
							ControllerActionStrategy.ROOT_MOVE_PLUGIN_DOWN);
				});
		scene.getAccelerators().put(
				new KeyCodeCombination(KeyCode.UP, KeyCombination.META_DOWN,
						KeyCombination.SHIFT_DOWN),
				() -> {
					context.execute(this,
							ControllerActionStrategy.ROOT_MOVE_PLUGIN_UP);
				});

	}

	@Override
	public FXMLLoader getLoader() {
		return loader;
	}

	@Override
	public ControllerEnum getControllerEnum() {
		return ControllerEnum.ROOT;
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resource) {
		uiSequence = new UISequence();
		context.execute(this, ControllerActionStrategy.ROOT_INITIALIZE);
	}

	@FXML
	protected void createNewSequence() {
		context.execute(this, ControllerActionStrategy.ROOT_NEW_UI_SEQUENCE);
	}

	@FXML
	protected void addNewPlugin(final ActionEvent event) {
		context.execute(this, ControllerActionStrategy.ROOT_NEW_UI_PLUGIN);
	}

	@FXML
	protected void exportSequence() {
		context.execute(this, ControllerActionStrategy.ROOT_EXPORT_SEQUENCE);
	}

	@FXML
	protected void importSequence() {
		context.execute(this,
				ControllerActionStrategy.ROOT_SHOW_IMPORT_DIALOG_SEQUENCE);
	}

	@FXML
	protected void importPostmanSequence() {
		iStageService.showDialog(ControllerEnum.IMPORT_POSTMAN);
	}

	@FXML
	protected void sendPlugin(final ActionEvent actionEvent) {
		context.execute(this, ControllerActionStrategy.ROOT_SEND_PLUGIN);
	}

	@FXML
	protected void sendSequence(final ActionEvent actionEvent) {
		context.execute(this, ControllerActionStrategy.ROOT_SEND_SEQUENCE);
	}

	@FXML
	protected void stopSendingSequence() {
		if (getSendRequestThread() != null) {
			getSendRequestThread().interrupt();
			setSendRequestThread(null);
		}
		context.execute(this, ControllerActionStrategy.ROOT_ENABLE_CONTROLS);
	}

}

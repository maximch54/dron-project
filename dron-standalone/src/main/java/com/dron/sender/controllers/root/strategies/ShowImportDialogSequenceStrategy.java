package com.dron.sender.controllers.root.strategies;

import java.io.File;

import javafx.stage.FileChooser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.config.AppProperties;
import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class ShowImportDialogSequenceStrategy extends BaseRootController
		implements IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private IStageService iStageService;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_SHOW_IMPORT_DIALOG_SEQUENCE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName(appProperties.getFilePath());
		File choosenFile = fileChooser.showOpenDialog(iStageService
				.getPrimaryStage());

		if (choosenFile != null) {
			appProperties.setFilePath(choosenFile);

			controller.setTmpImportFile(choosenFile);
			context.execute(controller,
					ControllerActionStrategy.ROOT_IMPORT_SEQUENCE);
		}
	}
}

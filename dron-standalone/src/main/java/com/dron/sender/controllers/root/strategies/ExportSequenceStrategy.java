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
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.ExportService;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Sequence;

@Component
public class ExportSequenceStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private IStageService iStageService;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_EXPORT_SEQUENCE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialFileName(appProperties.getFilePath());
			File choosenFile = fileChooser.showOpenDialog(iStageService
					.getPrimaryStage());

			if (choosenFile != null) {
				uiSequence.setSelectedPluginId(controller.getHistoryUiPlugin()
						.getUiPlugin().getId().get());

				UIPlugin uiPlugin = uiSequence.getSelectedUIPLugin();
				uiPlugin.setId(controller.getHistoryUiPlugin().getUiPlugin().getId().get());
				uiPlugin.setUrl(controller.getHistoryUiPlugin().getUiPlugin().getUrl().get());
				uiPlugin.setMethod(controller.getHistoryUiPlugin().getUiPlugin().getMethod().get());
				uiPlugin.setName(controller.getHistoryUiPlugin().getUiPlugin().getName().get());
				uiPlugin.setPostBody(controller.getHistoryUiPlugin().getUiPlugin().getPostBody().get());
				uiPlugin.setResponce(controller.getHistoryUiPlugin().getUiPlugin().getResponce().get());
				uiPlugin.setSuccess(controller.getHistoryUiPlugin().getUiPlugin().isSuccess().get());
				
				uiSequence.getUIParams().clear();
				controller.getHistoryUiPlugin().getUiParams()
						.forEach(param -> uiSequence.getUIParams().add(param.clone()));

				uiSequence.getSentPlugins().clear();

				
				Sequence sequence = new Sequence();
				TransformerFactory.reverseTransformEntity(sequence, uiSequence,
						TransformKey.ROOT_SEQUENCE);

				ExportService.getInstance().exports(sequence, choosenFile);
			}
		} catch (DronSenderException e) {
			System.out.println(e.getMessage());
		}

	}
}

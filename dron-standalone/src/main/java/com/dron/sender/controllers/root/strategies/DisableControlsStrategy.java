package com.dron.sender.controllers.root.strategies;

import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class DisableControlsStrategy extends BaseRootController implements
		IControllerStrategy {

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_DISABLE_CONTROLS;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		RootConfig.setDisableRootProperty(true);
		autoFillSequenceTextBox.disableProperty().set(true);
		tblParams.disableProperty().set(true);
		tbtnParams.disableProperty().set(true);
		accPlugins.disableProperty().set(true);
		tfNewPluginName.disableProperty().set(true);
		btnStopSendingSequence.setVisible(true);
	}
}

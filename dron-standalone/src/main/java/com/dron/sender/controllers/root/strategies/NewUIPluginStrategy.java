package com.dron.sender.controllers.root.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class NewUIPluginStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_NEW_UI_PLUGIN;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);

		UIPlugin uiPlugin = new UIPlugin();
		uiPlugin.setName(tfNewPluginName.getText());
		uiSequence.addPlugin(uiPlugin);
		tfNewPluginName.setText("");

		context.execute(controller,
				ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);
		uiSequence
				.selectedUIPLugin(uiPlugin.getId().get(), context, controller);
	}

}

package com.dron.sender.controllers.root.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.pattern.services.transformers.TransformerFactory;

@Component
public class PrepareSequenceStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	private RootController controller;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_PREPARE_SEQUENCE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		controller = (RootController) iBaseController;
		setUp(controller);

		uiSequence.clear();

		TransformerFactory.transformEntity(getTmpImportSequence(), uiSequence,
				TransformKey.ROOT_SEQUENCE);

		context.execute(controller,
				ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);

		uiSequence.selectedUIPLugin(uiSequence.getOrder().get(0), context,
				controller);
	}

}

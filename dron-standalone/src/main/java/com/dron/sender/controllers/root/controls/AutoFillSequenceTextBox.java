package com.dron.sender.controllers.root.controls;

import java.io.File;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import com.dron.sender.config.AppProperties;
import com.dron.sender.controllers.base.controls.AutoFillTextBoxBase;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

public class AutoFillSequenceTextBox extends AutoFillTextBoxBase {

	private ApplicationContext ctx;

	private RootController controller;

	public void setUp(ApplicationContext ctx, RootController controller) {
		this.ctx = ctx;
		this.controller = controller;
	}

	@Override
	public ObservableList<String> searchItems(final String value) {
		ObservableList<String> itemList;
		if (StringUtils.isNotBlank(value)) {
			// Items is filled use start with ignore case criteria and with
			// limit count
			itemList = FXCollections
					.observableArrayList(items
							.stream()
							.filter(m -> StringUtils.startsWithIgnoreCase(m,
									value))
							.filter(m -> StringUtils.endsWith(m,
									AutoFillTextBoxBase.FILE_END_JSON))
							.limit(getDefaultLimitCount())
							.collect(Collectors.toList()));
		} else {
			itemList = FXCollections.observableArrayList(items);
		}

		return itemList;
	}

	@Override
	public void onSelectAction(String fileName) {
		ControllerStrategyContext context = ctx
				.getBean(ControllerStrategyContext.class);
		AppProperties appProperties = ctx.getBean(AppProperties.class);

		File choosenFile = new File(appProperties.getFilePath()
				+ File.separator + fileName);

		controller.setTmpImportFile(choosenFile);
		context.execute(controller,
				ControllerActionStrategy.ROOT_IMPORT_SEQUENCE);
		
		//Set focus on the send sequence button
		controller.getBtnSendSequence().requestFocus();
	}
}

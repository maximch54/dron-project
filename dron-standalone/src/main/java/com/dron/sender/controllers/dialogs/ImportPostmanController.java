package com.dron.sender.controllers.dialogs;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.config.AppProperties;
import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.base.interfaces.ISceneService;
import com.dron.sender.controllers.base.interfaces.IStageService;
import com.dron.sender.controllers.base.models.ControllerEnum;
import com.dron.sender.controllers.dialogs.models.BaseImportPostmanController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.parsers.ImportFactory;
import com.dron.sender.exim.parsers.ParserType;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Sequence;

@Component
public class ImportPostmanController extends BaseImportPostmanController
		implements Initializable, IBaseController {

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private IStageService iStageService;

	@Autowired
	private ISceneService iSceneService;

	@Autowired
	private ControllerStrategyContext context;

	@Autowired
	private RootController rootController;

	private FXMLLoader loader;

	public ImportPostmanController() {
		ClassLoader classLoader = ImportPostmanController.class.getClassLoader();

		loader = new FXMLLoader(classLoader.getResource("ImportPostmanView.fxml"));
		loader.setController(this);
	}

	@PostConstruct
	private void setUp() {

	}

	@Override
	public FXMLLoader getLoader() {
		return loader;
	}

	@Override
	public ControllerEnum getControllerEnum() {
		return ControllerEnum.IMPORT_POSTMAN;
	}

	@Override
	public void initialize(final URL url, final ResourceBundle resource) {
	}

	@FXML
	public void chooseFileWithValues() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName(appProperties
				.getTmpImportPostmanValueFilePath());
		File choosenFile = fileChooser.showOpenDialog(iStageService
				.getDialogStage());

		if (choosenFile != null) {
			appProperties.setTmpImportPostmanValueFilePath(choosenFile);
			tfPostmanValues.setText(appProperties
					.getTmpImportPostmanValueFilePath());
			setTmpImportPostmanValueFile(choosenFile);
		}
	}

	@FXML
	public void chooseFileWithRequests() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName(appProperties
				.getTmpImportPostmanRequestFilePath());
		File choosenFile = fileChooser.showOpenDialog(iStageService
				.getDialogStage());

		if (choosenFile != null) {
			appProperties.setTmpImportPostmanRequestFilePath(choosenFile);
			tfPostmanRequests.setText(appProperties
					.getTmpImportPostmanRequestFilePath());
			setTmpImportPostmanRequestFile(choosenFile);
		}
	}

	@FXML
	public void cancelImportPostmanDialog() {
		iStageService.hideDialog();
	}

	@FXML
	public void importPostmanData() {
		Sequence sequence = new Sequence();
		try {
			if (tmpImportPostmanRequestFile != null) {
				ImportFactory.getInstance().importSequence(
						tmpImportPostmanRequestFile, sequence,
						ParserType.POSTMAN_REQUESTS);
			}
			if (tmpImportPostmanValueFile != null) {
				ImportFactory.getInstance().importSequence(
						tmpImportPostmanValueFile, sequence,
						ParserType.POSTMAN_VALUES);
			}

			TransformerFactory.transformEntity(sequence,
					rootController.getUiSequence(), TransformKey.ROOT_SEQUENCE);

			rootController.setTmpImportSequence(sequence);

			context.execute(rootController,
					ControllerActionStrategy.ROOT_PREPARE_SEQUENCE);

			cancelImportPostmanDialog();
		} catch (DronSenderException e) {
			System.out.println(e.getMessage());
		}
	}
}

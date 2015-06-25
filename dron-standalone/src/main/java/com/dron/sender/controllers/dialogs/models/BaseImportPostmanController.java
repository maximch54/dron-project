package com.dron.sender.controllers.dialogs.models;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class BaseImportPostmanController extends Parent {

	protected File tmpImportPostmanValueFile;

	protected File tmpImportPostmanRequestFile;

	@FXML
	protected TextField tfPostmanValues;

	@FXML
	protected TextField tfPostmanRequests;

	@FXML
	protected Button btnCancel;

	public File getTmpImportPostmanValueFile() {
		return tmpImportPostmanValueFile;
	}

	public void setTmpImportPostmanValueFile(File tmpImportPostmanValueFile) {
		this.tmpImportPostmanValueFile = tmpImportPostmanValueFile;
	}

	public File getTmpImportPostmanRequestFile() {
		return tmpImportPostmanRequestFile;
	}

	public void setTmpImportPostmanRequestFile(File tmpImportPostmanRequestFile) {
		this.tmpImportPostmanRequestFile = tmpImportPostmanRequestFile;
	}

	public TextField getTfPostmanValues() {
		return tfPostmanValues;
	}

	public TextField getTfPostmanRequests() {
		return tfPostmanRequests;
	}

}

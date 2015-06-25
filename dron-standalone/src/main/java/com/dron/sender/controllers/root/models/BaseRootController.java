package com.dron.sender.controllers.root.models;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

import com.dron.sender.controllers.root.controls.AutoFillSequenceTextBox;
import com.dron.sender.sequence.models.Sequence;

public abstract class BaseRootController extends Parent {

	protected static final double DEFAULT_ACCORDION_WIDTH = 300.0;
	protected static final double DEFAULT_ACCORDION_HEIGHT = 100.0;

	@FXML
	protected TextField tfUrl;

	@FXML
	protected TextField tfNewPluginName;

	@FXML
	protected TextArea txaResponce;

	@FXML
	protected TextArea txaPostBody;

	@FXML
	protected TextArea txaConsole;

	@FXML
	protected ToggleButton tbtnHeaders;

	@FXML
	protected ToggleButton tbtnParams;

	@FXML
	protected Button btnSendActivePlugin;

	@FXML
	protected Button btnStopSendingSequence;

	@FXML
	protected Button btnSendSequence;

	@FXML
	protected Button btnAddNewPlugin;

	@FXML
	protected ChoiceBox<String> cbMethods;

	@FXML
	protected Accordion accPlugins;

	@FXML
	protected TableView<UIHttpHeaders> tblHeaders;

	@FXML
	protected TableView<UIParam> tblParams;

	@FXML
	protected AutoFillSequenceTextBox autoFillSequenceTextBox;

	@FXML
	protected ListView<HistoryUiPlugin> lvHistory;

	@FXML
	protected SplitPane spHeaders;
	
	@FXML
	protected SplitPane spParams;
	
	protected UISequence uiSequence;

	private HistoryUiPlugin historyUiPlugin;

	// The sequence is used for share sequence between IMPORT_SEQUENCE and
	// PREPARE_SEQUENCE strategies
	private Sequence tmpImportSequence;

	// The File is used for share file between SHOW_IMPORT_DIALOG_SEQUENCE and
	// IMPORT_SEQUENCE strategies
	private File tmpImportFile;

	// Thread that is using for send sequence requests
	private Thread sendRequestThread;

	public void setUp(BaseRootController modelRootController) {
		this.tfUrl = modelRootController.getTfUrl();
		this.tfNewPluginName = modelRootController.getTfNewPluginName();
		this.txaResponce = modelRootController.getTxaResponce();
		this.txaPostBody = modelRootController.getTxaPostBody();
		this.txaConsole = modelRootController.getTxaConsole();
		this.tbtnHeaders = modelRootController.getTbtnHeaders();
		this.tbtnParams = modelRootController.getTbtnParams();
		this.cbMethods = modelRootController.getCbMethods();
		this.accPlugins = modelRootController.getAccPlugins();
		this.tblHeaders = modelRootController.getTblHeaders();
		this.tblParams = modelRootController.getTblParams();
		this.uiSequence = modelRootController.getUiSequence();
		this.historyUiPlugin = modelRootController.getHistoryUiPlugin();
		this.btnSendActivePlugin = modelRootController.getBtnSendActivePlugin();
		this.btnStopSendingSequence = modelRootController
				.getBtnStopSendingSequence();
		this.btnSendSequence = modelRootController.getBtnSendSequence();
		this.btnAddNewPlugin = modelRootController.getBtnAddNewPlugin();
		this.autoFillSequenceTextBox = modelRootController
				.getAutoFillSequenceTextBox();
		this.tmpImportSequence = modelRootController.getTmpImportSequence();
		this.tmpImportFile = modelRootController.getTmpImportFile();
		this.sendRequestThread = modelRootController.getSendRequestThread();
		this.lvHistory = modelRootController.getLvHistory();
		this.spHeaders = modelRootController.getSpHeaders();
		this.spParams = modelRootController.getSpParams();
	}

	public TextField getTfUrl() {
		return tfUrl;
	}

	public TextField getTfNewPluginName() {
		return tfNewPluginName;
	}

	public TextArea getTxaResponce() {
		return txaResponce;
	}

	public TextArea getTxaConsole() {
		return txaConsole;
	}

	public TableView<UIHttpHeaders> getTblHeaders() {
		return tblHeaders;
	}

	public TableView<UIParam> getTblParams() {
		return tblParams;
	}

	public ToggleButton getTbtnHeaders() {
		return tbtnHeaders;
	}

	public ToggleButton getTbtnParams() {
		return tbtnParams;
	}

	public TextArea getTxaPostBody() {
		return txaPostBody;
	}

	public ChoiceBox<String> getCbMethods() {
		return cbMethods;
	}

	public Accordion getAccPlugins() {
		return accPlugins;
	}

	public UISequence getUiSequence() {
		return uiSequence;
	}

	public Button getBtnSendActivePlugin() {
		return btnSendActivePlugin;
	}

	public Button getBtnStopSendingSequence() {
		return btnStopSendingSequence;
	}

	public Button getBtnSendSequence() {
		return btnSendSequence;
	}

	public Button getBtnAddNewPlugin() {
		return btnAddNewPlugin;
	}

	public AutoFillSequenceTextBox getAutoFillSequenceTextBox() {
		return autoFillSequenceTextBox;
	}

	public Sequence getTmpImportSequence() {
		return tmpImportSequence;
	}

	public File getTmpImportFile() {
		return tmpImportFile;
	}

	public void setTmpImportSequence(Sequence tmpImportSequence) {
		this.tmpImportSequence = tmpImportSequence;
	}

	public void setTmpImportFile(File tmpImportFile) {
		this.tmpImportFile = tmpImportFile;
	}

	public Thread getSendRequestThread() {
		return sendRequestThread;
	}

	public void setSendRequestThread(Thread sendRequestThread) {
		this.sendRequestThread = sendRequestThread;
	}

	public ListView<HistoryUiPlugin> getLvHistory() {
		return lvHistory;
	}

	public void setLvHistory(ListView<HistoryUiPlugin> lvHistory) {
		this.lvHistory = lvHistory;
	}

	public HistoryUiPlugin getHistoryUiPlugin() {
		return historyUiPlugin;
	}

	public void setHistoryUiPlugin(HistoryUiPlugin historyUiPlugin) {
		this.historyUiPlugin = historyUiPlugin;
	}

	public SplitPane getSpHeaders() {
		return spHeaders;
	}

	public void setSpHeaders(SplitPane spHeaders) {
		this.spHeaders = spHeaders;
	}

	public SplitPane getSpParams() {
		return spParams;
	}

	public void setSpParams(SplitPane spParams) {
		this.spParams = spParams;
	}
}

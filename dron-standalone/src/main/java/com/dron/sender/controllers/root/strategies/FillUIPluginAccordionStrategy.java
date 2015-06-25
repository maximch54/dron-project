package com.dron.sender.controllers.root.strategies;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.FutureParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.controllers.root.models.HistoryUiPlugin;
import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class FillUIPluginAccordionStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION;
	}

	@Override
	public void execute(final IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		List<TitledPane> titledPanes = new ArrayList<TitledPane>();

		if (uiSequence.getUiPlugins().isEmpty()) {
			uiSequence.getUiPlugins().add(new UIPlugin());
		}

		uiSequence.getOrder().forEach(order -> {
			UIPlugin uiPlugin = uiSequence.findPlugin(order);
			titledPanes.add(createPluginTitlePane(uiPlugin, controller));
		});

		accPlugins.getPanes().clear();
		accPlugins.getPanes().addAll(titledPanes);
		accPlugins.setExpandedPane(accPlugins.getPanes().get(0));

		accPlugins.expandedPaneProperty().addListener(
				(ObservableValue<? extends TitledPane> observable,
						TitledPane oldValue, TitledPane newValue) -> {
					if (oldValue != null) {
						RootConfig.setDisableRootProperty(true);
					}
					if (newValue != null) {
						int index = accPlugins.getChildrenUnmodifiable()
								.indexOf(newValue);

						uiSequence.selectedUIPLugin(
								uiSequence.getOrder().get(index), context,
								controller);
						RootConfig.setDisableRootProperty(false);
					}
				});

	}

	private TitledPane createPluginTitlePane(UIPlugin uiPlugin,
			RootController controller) {

		AnchorPane anchorPane = createAnchorPane(uiPlugin, controller);

		TitledPane pluginTitle = new TitledPane(uiPlugin.getName().get(),
				anchorPane);
		setAnchor(pluginTitle);
		pluginTitle.setAnimated(false);
		pluginTitle.textProperty().bindBidirectional(uiPlugin.getName());
		return pluginTitle;
	}

	private AnchorPane createAnchorPane(final UIPlugin uiPlugin,
			final RootController controller) {
		TableView<UIFutureParam> tblFutureParams = new TableView<>();
		tblFutureParams = new FutureParamTableView().initializeWithKeyList(
				uiPlugin.getFutureParams(), uiSequence.getKeys(),
				tblFutureParams);

		int index = uiSequence.findSelectedIndex(uiPlugin.getId().get());
		Button btnMoveDown = new Button("");
		btnMoveDown.getStyleClass().add("btn-move-down");
		btnMoveDown.setPrefWidth(25.0);
		btnMoveDown.setMnemonicParsing(false);
		btnMoveDown.setOnAction(listener -> {
			setHistoryUiPlugin(new HistoryUiPlugin(uiPlugin, uiSequence
					.getUIParams()));
			context.execute(controller,
					ControllerActionStrategy.ROOT_MOVE_PLUGIN_DOWN);
		});
		btnMoveDown.setVisible(index != uiSequence.getOrder().size() - 1);

		Button btnMoveUp = new Button("");
		btnMoveUp.getStyleClass().add("btn-move-up");
		btnMoveUp.setPrefWidth(25.0);
		btnMoveUp.setMnemonicParsing(false);
		btnMoveUp.setOnAction(listener -> {
			setHistoryUiPlugin(new HistoryUiPlugin(uiPlugin, uiSequence
					.getUIParams()));
			context.execute(controller,
					ControllerActionStrategy.ROOT_MOVE_PLUGIN_UP);
		});
		btnMoveUp.setVisible(index != 0);

		Button btnRemove = new Button("");
		VBox.setMargin(btnRemove, new Insets(0.0, 0.0, 25.0, 0.0));
		btnRemove.getStyleClass().add("btn-remove");
		btnRemove.setPrefWidth(25.0);
		btnRemove.setMnemonicParsing(false);
		btnRemove.setOnAction(listener -> {
			int expantedIndex = getExpantedUIPluginIndex();
			uiSequence.removePlugin(expantedIndex);

			context.execute(controller,
					ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);
			uiSequence.selectedUIPLugin(context, controller);
		});

		VBox vOrientation = new VBox();
		vOrientation.getChildren().addAll(btnRemove, btnMoveUp, btnMoveDown);

		HBox hRename = new HBox();

		HBox hRenamePanel = new HBox();
		hRenamePanel.setVisible(false);
		hRenamePanel.setManaged(false);

		Button btnRename = new Button("Rename");
		btnRename.managedProperty().bind(hRenamePanel.visibleProperty().not());
		btnRename.setOnAction(listener -> {
			hRenamePanel.setVisible(true);
			hRenamePanel.setManaged(true);
		});

		TextField tfPluginName = new TextField();
		tfPluginName.setText(uiPlugin.getName().get());

		Button btnSave = new Button("Save");
		btnSave.setOnAction(listener -> {
			uiPlugin.getName().set(tfPluginName.getText());
			hRenamePanel.setVisible(false);
			hRenamePanel.setManaged(false);
		});
		Button btnCancel = new Button("Cancel");
		btnCancel.setOnAction(listener -> {
			hRenamePanel.setVisible(false);
			hRenamePanel.setManaged(false);
		});
		hRenamePanel.getChildren().addAll(tfPluginName, btnSave, btnCancel);

		hRename.getChildren().addAll(btnRename, hRenamePanel);
		setAnchor(hRename);

		HBox hBox = new HBox();
		hBox.getChildren().addAll(tblFutureParams, vOrientation);
		setAnchor(hBox);

		VBox vBox = new VBox(hBox, hRename);
		setAnchor(vBox);

		AnchorPane anchorPane = new AnchorPane();
		anchorPane.getChildren().addAll(vBox);

		return anchorPane;
	}

	private void setAnchor(Node node) {
		AnchorPane.setBottomAnchor(node, 0.0);
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);
		AnchorPane.setTopAnchor(node, 0.0);
	}

	private int getExpantedUIPluginIndex() {
		return accPlugins.getChildrenUnmodifiable().indexOf(
				accPlugins.getExpandedPane());
	}

}

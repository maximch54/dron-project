package com.dron.sender.controllers.base.controls;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.DefaultStringConverter;

import com.dron.sender.controllers.base.interfaces.IModelTableView;
import com.dron.sender.controllers.base.interfaces.IOnKeyEditCommit;

public abstract class BasePropertyTableView<T extends IModelTableView> {

	protected abstract int getMinWidth();

	protected abstract String getKeyName();

	protected abstract String getValueName();

	protected abstract T getEmptyInstance();

	private IOnKeyEditCommit<T> iOnKeyEditCommit;

	public TableView<T> initializeWithKeyList(
			final ObservableList<T> observableList,
			final ObservableList<String> keys, final TableView<T> tableView) {

		return initialize(observableList, keys, null, tableView);
	}

	public TableView<T> initialize(final ObservableList<T> observableList,
			final TableView<T> tableView, IOnKeyEditCommit<T> iOnKeyEditCommit) {
		this.iOnKeyEditCommit = iOnKeyEditCommit;
		return initialize(observableList, null, null, tableView);
	}

	@SuppressWarnings("unchecked")
	private TableView<T> initialize(final ObservableList<T> observableList,
			final ObservableList<String> keys,
			final ObservableList<String> values, final TableView<T> tableView) {
		final TableColumn<T, String> headerCol = initKeyColumn(observableList,
				keys, tableView);

		final TableColumn<T, String> valueCol = initValueColumn(observableList,
				tableView);
		tableView.getColumns().setAll(headerCol, valueCol);
		tableView.setItems(observableList);
		tableView.setEditable(true);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableView.managedProperty().bind(tableView.visibleProperty());

		return tableView;
	}

	private TableColumn<T, String> initKeyColumn(
			final ObservableList<T> observableList,
			final ObservableList<String> keys, final TableView<T> tableView) {
		final TableColumn<T, String> headerCol = new TableColumn<>(getKeyName());
		headerCol.setCellValueFactory(new PropertyValueFactory<>(getKeyName()));
		headerCol.setMinWidth(getMinWidth());

		// If keys equals null, then editable cell should be TextField
		if (keys == null) {
			headerCol
					.setCellFactory(factory -> new TextFieldTableCell<T, String>(
							new DefaultStringConverter()));
		} else {
			// If keys is not null, then we should show ComboBox for user in the
			// editable form
			headerCol.setCellFactory(ComboBoxTableCell.forTableColumn(keys));
			if (iOnKeyEditCommit != null) {
				iOnKeyEditCommit.onKeyEditCommit(observableList);
			}
		}
		headerCol.setOnEditCommit(header -> {
			header.getRowValue().updateModelKey(header.getNewValue());

			cleanList(observableList);
			observableList.add(getEmptyInstance());
			
			if (iOnKeyEditCommit != null) {
				iOnKeyEditCommit.onKeyEditCommit(observableList);
			}
		});
		return headerCol;
	}

	private TableColumn<T, String> initValueColumn(
			final ObservableList<T> observableList, final TableView<T> tableView) {
		final TableColumn<T, String> valueCol = new TableColumn<>(
				getValueName());
		valueCol.setCellValueFactory(new PropertyValueFactory<>(getValueName()));

		valueCol.setMinWidth(getMinWidth());

		valueCol.setCellFactory(factory -> new TextFieldTableCell<T, String>(
				new DefaultStringConverter()));

		valueCol.setOnEditCommit(value -> {
			value.getRowValue().updateModelValue(value.getNewValue());

			cleanList(observableList);

			observableList.add(getEmptyInstance());
		});
		return valueCol;
	}

	private void cleanList(final ObservableList<T> observableList) {
		List<T> notEmptyList = new ArrayList<>();
		observableList.forEach(item -> {
			if (!item.isEmpty()) {
				notEmptyList.add(item);
			}
		});
		observableList.clear();
		observableList.addAll(notEmptyList);
	}

}

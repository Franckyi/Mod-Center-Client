package com.franckyi.mpb.view.region;

import java.util.List;

import com.franckyi.mpb.core.ModFileCell;
import com.franckyi.mpb.core.ModFileCell.ModFileCellDLButton;
import com.franckyi.mpb.core.json.JsonMod;
import com.franckyi.mpb.core.json.JsonModFile;
import com.franckyi.mpb.view.MPBFonts;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.Pane;

public class SelectModTable extends Pane {

	private ObservableList<ModFileCell> files = FXCollections.observableArrayList();

	@SuppressWarnings("unchecked")
	public SelectModTable(JsonMod mod) {
		for (List<JsonModFile> versions : mod.versions.values())
			for (JsonModFile file : versions)
				files.add(new ModFileCell(file, mod.title));

		JFXTreeTableColumn<ModFileCell, String> versionColumn = new JFXTreeTableColumn<>("Version");
		versionColumn.setPrefWidth(100);
		versionColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ModFileCell, String> param) -> {
			if (versionColumn.validateValue(param))
				return param.getValue().getValue().version;
			else
				return versionColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<ModFileCell, String> typeColumn = new JFXTreeTableColumn<>("Type");
		typeColumn.setPrefWidth(100);
		typeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ModFileCell, String> param) -> {
			if (typeColumn.validateValue(param))
				return param.getValue().getValue().type;
			else
				return typeColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<ModFileCell, String> nameColumn = new JFXTreeTableColumn<>("Name");
		nameColumn.setPrefWidth(350);
		nameColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<ModFileCell, String> param) -> {
			if (nameColumn.validateValue(param))
				return param.getValue().getValue().name;
			else
				return nameColumn.getComputedValue(param);
		});

		JFXTreeTableColumn<ModFileCell, ModFileCellDLButton> downloadColumn = new JFXTreeTableColumn<>("Download");
		downloadColumn.setPrefWidth(200);
		downloadColumn
				.setCellValueFactory((TreeTableColumn.CellDataFeatures<ModFileCell, ModFileCellDLButton> param) -> {
					if (downloadColumn.validateValue(param))
						return param.getValue().getValue().download;
					else
						return downloadColumn.getComputedValue(param);
				});

		final TreeItem<ModFileCell> root = new RecursiveTreeItem<ModFileCell>(files, RecursiveTreeObject::getChildren);

		JFXTreeTableView<ModFileCell> treeView = new JFXTreeTableView<ModFileCell>(root);
		treeView.setStyle(
				"-fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 16px; -fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; -fx-background-insets: 0, 1, 2; -fx-background-radius: 5, 4, 3;");
		treeView.setShowRoot(false);
		treeView.setEditable(false);
		treeView.getColumns().setAll(versionColumn, typeColumn, nameColumn, downloadColumn);

		Label search = new Label("Search :");
		search.setFont(MPBFonts.NORMAL);

		JFXTextField filterField = new JFXTextField();
		filterField.textProperty().addListener((o, oldVal, newVal) -> {
			treeView.setPredicate(file -> file.getValue().version.get().contains(newVal)
					|| file.getValue().type.get().contains(newVal) || file.getValue().name.get().contains(newVal));
		});

		Label size = new Label();
		size.setFont(MPBFonts.NORMAL);
		size.textProperty().bind(Bindings.createStringBinding(() -> treeView.getCurrentItemsCount() + " files found.",
				treeView.currentItemsCountProperty()));

		search.relocate(10, 18);
		filterField.relocate(80, 14);
		treeView.setPrefSize(750, 750);
		treeView.relocate(0, 50);
		size.relocate(10, 818);

		this.setPrefSize(750, 850);
		this.getChildren().addAll(treeView, search, filterField, size);
	}

}

package com.github.franckyi.mpb.view.region;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.franckyi.modcenter.api.ProjectFile;
import com.github.franckyi.mpb.MPBApplication;
import com.github.franckyi.mpb.view.nodes.DownloadButton;
import com.github.franckyi.mpb.view.nodes.NormalLabel;
import com.github.franckyi.mpb.view.region.SelectModTable.ProjectFileRow;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SelectModTable extends TableView<ProjectFileRow> {

	private TableColumn<ProjectFileRow, Label> name = new TableColumn<>("Name");
	private TableColumn<ProjectFileRow, Label> version = new TableColumn<>("Version");
	private TableColumn<ProjectFileRow, Label> type = new TableColumn<>("Type");
	private TableColumn<ProjectFileRow, Label> size = new TableColumn<>("Size");
	private TableColumn<ProjectFileRow, Label> uploaded = new TableColumn<>("Date");
	private TableColumn<ProjectFileRow, Label> downloads = new TableColumn<>("Downloads");
	private TableColumn<ProjectFileRow, DownloadButton> download = new TableColumn<>("Download");

	@SuppressWarnings("unchecked")
	public SelectModTable(List<ProjectFile> files) {
		ObservableList<ProjectFileRow> data = FXCollections.observableArrayList();
		for (ProjectFile file : files)
			data.add(new ProjectFileRow(file));
		this.setEditable(false);
		this.setPrefWidth(917);
		this.setPrefHeight(800);
		this.getColumns().addAll(name, version, type, size, uploaded, downloads, download);
		name.setCellValueFactory(new PropertyValueFactory<ProjectFileRow, Label>("name"));
		name.setPrefWidth(300);
		version.setCellValueFactory(new PropertyValueFactory<ProjectFileRow, Label>("version"));
		version.setPrefWidth(70);
		type.setCellValueFactory(new PropertyValueFactory<ProjectFileRow, Label>("type"));
		type.setPrefWidth(90);
		size.setCellValueFactory(new PropertyValueFactory<ProjectFileRow, Label>("size"));
		size.setPrefWidth(110);
		uploaded.setCellValueFactory(new PropertyValueFactory<ProjectFileRow, Label>("uploaded"));
		uploaded.setPrefWidth(100);
		downloads.setCellValueFactory(new PropertyValueFactory<ProjectFileRow, Label>("downloads"));
		downloads.setPrefWidth(100);
		download.setCellValueFactory(new PropertyValueFactory<ProjectFileRow, DownloadButton>("download"));
		download.setPrefWidth(130);
		this.setItems(data);
		MPBApplication.print("Showing file list, waiting for user to choose...");
	}

	public static class ProjectFileRow {
		private final SimpleObjectProperty<Label> name;
		private final SimpleObjectProperty<Label> version;
		private final SimpleObjectProperty<Label> type;
		private final SimpleObjectProperty<Label> size;
		private final SimpleObjectProperty<Label> uploaded;
		private final SimpleObjectProperty<Label> downloads;
		private final SimpleObjectProperty<DownloadButton> download;

		public ProjectFileRow(ProjectFile file) {
			this.name = new SimpleObjectProperty<>(new NormalLabel(file.getFileName()));
			this.version = new SimpleObjectProperty<>(new NormalLabel(file.getVersion()));
			this.type = new SimpleObjectProperty<>(new NormalLabel(file.getType()));
			this.size = new SimpleObjectProperty<>(new NormalLabel(file.getSize()));
			this.uploaded = new SimpleObjectProperty<>(new NormalLabel(file.getUploaded().toLocalDate().toString()));
			this.downloads = new SimpleObjectProperty<>(
					new NormalLabel(NumberFormat.getNumberInstance(Locale.US).format(file.getDownloads()) + ""));
			this.download = new SimpleObjectProperty<>(new DownloadButton(file));
		}

		public Label getName() {
			return name.get();
		}

		public void setName(Label name) {
			this.name.set(name);
		}

		public Label getVersion() {
			return version.get();
		}

		public void setVersion(Label version) {
			this.version.set(version);
		}

		public Label getType() {
			return type.get();
		}

		public void setType(Label type) {
			this.type.set(type);
		}

		public Label getSize() {
			return size.get();
		}

		public void setSize(Label size) {
			this.size.set(size);
		}

		public Label getUploaded() {
			return uploaded.get();
		}

		public void setUploaded(Label uploaded) {
			this.uploaded.set(uploaded);
		}

		public Label getDownloads() {
			return downloads.get();
		}

		public void setDownloads(Label downloads) {
			this.downloads.set(downloads);
		}

		public DownloadButton getDownload() {
			return download.get();
		}

		public void setDownload(DownloadButton download) {
			this.download.set(download);
		}

	}

}

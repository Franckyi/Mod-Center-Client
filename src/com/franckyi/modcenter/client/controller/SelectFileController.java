package com.franckyi.modcenter.client.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.beans.Project;
import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.api.beans.ProjectFileFilter;
import com.franckyi.modcenter.api.beans.enums.EnumFileType;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.core.tasks.SelectFileTask;
import com.franckyi.modcenter.client.view.MCCColors;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;
import com.franckyi.modcenter.client.view.region.FileVisual;
import com.franckyi.modcenter.client.view.region.LoadingPane;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class SelectFileController {

	public String currentMcVersion;
	public boolean searching;

	private Project project;
	private boolean latest;

	@FXML
	private VBox root;

	@FXML
	private JFXComboBox<String> mcVersion;

	@FXML
	public JFXCheckBox chkRelease, chkBeta, chkAlpha;

	@FXML
	private VBox spContent;

	public VBox init(Project project, List<ProjectFile> files, boolean latest, ProjectFileFilter filter) {
		this.project = project;
		this.latest = latest;
		mcVersion.getItems().add("");
		try {
			mcVersion.getItems().addAll(ModCenterAPI.getVersions(project));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		mcVersion.setValue(filter.getVersion());
		currentMcVersion = filter.getVersion();
		spContent.getChildren().clear();
		for (ProjectFile file : files)
			spContent.getChildren().addAll(new Separator(), new FileVisual(file));
		if (!spContent.getChildren().isEmpty())
			spContent.getChildren().remove(0);
		else
			spContent.getChildren().add(new Label("No files found."));
		chkRelease.setTextFill(MCCColors.GREEN);
		chkRelease.setSelected(filter.getTypes().contains(EnumFileType.RELEASE));
		chkBeta.setTextFill(MCCColors.BLUE);
		chkBeta.setSelected(filter.getTypes().contains(EnumFileType.BETA));
		chkAlpha.setTextFill(MCCColors.RED);
		chkAlpha.setSelected(filter.getTypes().contains(EnumFileType.ALPHA));
		return root;
	}

	@FXML
	void mcVersionChanged(ActionEvent event) {
		if (!mcVersion.getValue().equals(currentMcVersion)) {
			currentMcVersion = mcVersion.getValue();
			searching = true;
			updateFileList();
		}
	}

	@FXML
	void chkTypeChanged(ActionEvent event) {
		updateFileList();
	}

	private void updateFileList() {
		List<EnumFileType> types = new ArrayList<>();
		if (chkRelease.isSelected())
			types.add(EnumFileType.RELEASE);
		if (chkBeta.isSelected())
			types.add(EnumFileType.BETA);
		if (chkAlpha.isSelected())
			types.add(EnumFileType.ALPHA);
		ProjectFileFilter filter = new ProjectFileFilter(currentMcVersion, types);
		spContent.getChildren().clear();
		spContent.getChildren().add(new LoadingPane());
		Thread th = new Thread(new SelectFileTask(project, filter, latest));
		th.setName("SelectFileTask");
		th.start();
	}

	public static SelectFileController get(FXMLFile file) {
		return (SelectFileController) ModCenterClient.INSTANCE.parents.get(file).getUserData();
	}

}

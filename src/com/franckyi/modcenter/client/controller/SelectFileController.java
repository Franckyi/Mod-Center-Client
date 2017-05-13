package com.franckyi.modcenter.client.controller;

import java.util.List;

import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.api.beans.enums.EnumCategory;
import com.franckyi.modcenter.api.beans.enums.EnumSortFilter;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.view.MCCColors;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;
import com.franckyi.modcenter.client.view.region.FileVisual;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SelectFileController {
	
	public String currentMcVersion;
	public EnumSortFilter currentSortFilter;
	public boolean currentSortOrder = false;
	public String currentSearchText;
	
	public boolean searching;

	@FXML
	private VBox root;
	
	@FXML
	private JFXComboBox<EnumSortFilter> sortFilter;

	@FXML
	private ImageView sortOrder;

	@FXML
	private JFXComboBox<String> mcVersion;

	@FXML
	public JFXTextField search;

	@FXML
	public JFXComboBox<EnumCategory> categories;
	
	@FXML
	public JFXCheckBox chkRelease, chkBeta, chkAlpha;

	@FXML
	private VBox spContent;

	public VBox init(List<ProjectFile> files) {
		for (ProjectFile file : files)
			spContent.getChildren().addAll(new Separator(), new FileVisual(file));
		if (!spContent.getChildren().isEmpty())
			spContent.getChildren().remove(0);
		chkRelease.setTextFill(MCCColors.GREEN);
		chkBeta.setTextFill(MCCColors.BLUE);
		chkAlpha.setTextFill(MCCColors.RED);
		return root;
	}
	
	@FXML
	void mcVersionChanged(ActionEvent event) {
		if (!mcVersion.getValue().equals(currentMcVersion)) {
			currentMcVersion = mcVersion.getValue();
			//
		}
	}

	@FXML
	void sortFilterChanged(ActionEvent event) {
		if (!sortFilter.getValue().equals(currentSortFilter)) {
			currentSortFilter = sortFilter.getValue();
			//
		}
	}

	@FXML
	void sortOrderChanged(MouseEvent event) {
		if (!searching) {
			if (currentSortOrder) {
				currentSortOrder = false;
				sortOrder.setImage(new Image(getClass().getResourceAsStream("../view/img/arrow-down.png")));
			} else {
				currentSortOrder = true;
				sortOrder.setImage(new Image(getClass().getResourceAsStream("../view/img/arrow-up.png")));
			}
			//
		}
	}

	@FXML
	void searchFilterChanged(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			currentSearchText = search.getText();
			//
		}
	}
	
	@FXML
	void chkTypeChanged(ActionEvent event) {
		
	}

	public static SelectFileController get(FXMLFile file) {
		return (SelectFileController) ModCenterClient.INSTANCE.parents.get(file).getUserData();
	}

}

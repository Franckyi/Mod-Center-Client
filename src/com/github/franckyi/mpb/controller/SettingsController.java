package com.github.franckyi.mpb.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.github.franckyi.mpb.MPBApplication;
import com.github.franckyi.mpb.MPBConfig;
import com.github.franckyi.mpb.MPBConfig.EnumConfig;
import com.github.franckyi.mpb.view.MPBFonts;
import com.github.franckyi.mpb.view.fxml.FXMLFile;
import com.github.franckyi.mpb.view.nodes.NormalButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SettingsController implements Initializable {

	@FXML
	private JFXToggleButton displayModsThumbnail;

	@FXML
	private JFXTextField projectsPerPage;

	@FXML
	private HBox buttonsPane;

	@FXML
	public ScrollPane settingsScrollPane;

	private NormalButton cancel, reset, save;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		settingsScrollPane.setStyle(
				"-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; -fx-background-insets: 0, 1, 2; -fx-background-radius: 5, 4, 3;");
		cancel = new NormalButton("Cancel", Color.web("#E49788"));
		cancel.setPrefWidth(150);
		cancel.setFont(MPBFonts.BIG_20);
		cancel.setTextFill(Color.WHITE);
		cancel.setOnAction(e -> {
			loadValues();
		});
		reset = new NormalButton("Defaults", Color.web("#7FA5C4"));
		reset.setPrefWidth(150);
		reset.setFont(MPBFonts.BIG_20);
		reset.setTextFill(Color.WHITE);
		reset.setOnAction(e -> {
			MPBConfig.defaults();
			loadValues();
		});
		save = new NormalButton("Save", Color.web("#8CAF62"));
		save.setPrefWidth(150);
		save.setFont(MPBFonts.BIG_20);
		save.setTextFill(Color.WHITE);
		save.setOnAction(e -> {
			saveValues();
		});
		buttonsPane.getChildren().addAll(cancel, reset, save);
		loadValues();
	}

	private void loadValues() {
		displayModsThumbnail.setSelected(MPBConfig.getBoolean(EnumConfig.displayModsThumbnail));
		projectsPerPage.setText(MPBConfig.getString(EnumConfig.projectsPerPage));
		// For each parameter
	}

	private void saveValues() {
		MPBConfig.setProperty(EnumConfig.displayModsThumbnail, displayModsThumbnail.isSelected());
		MPBConfig.setProperty(EnumConfig.projectsPerPage, projectsPerPage.getText());
		// For each parameter
		MPBConfig.saveConfig();
		((ModBrowserController) MPBApplication.INSTANCE.parents.get(FXMLFile.MOD_BROWSER).getUserData())
				.updateModBrowser();
	}

}

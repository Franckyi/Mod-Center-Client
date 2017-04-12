package com.franckyi.mpb.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.franckyi.mpb.MPBConfig;
import com.franckyi.mpb.MPBConfig.EnumConfig;
import com.franckyi.mpb.view.nodes.NormalButton;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SettingsController implements Initializable {

	@FXML
	private JFXToggleButton displayModsThumbnail;

	@FXML
	private HBox buttonsPane;

	private NormalButton cancel, reset, save;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cancel = new NormalButton("Cancel", Color.web("#E49788"));
		cancel.setPrefWidth(150);
		cancel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		cancel.setTextFill(Color.WHITE);
		cancel.setOnAction(e -> {
			loadValues();
		});
		reset = new NormalButton("Defaults", Color.web("#7FA5C4"));
		reset.setPrefWidth(150);
		reset.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		reset.setTextFill(Color.WHITE);
		reset.setOnAction(e -> {
			MPBConfig.defaults();
			loadValues();
		});
		save = new NormalButton("Save", Color.web("#8CAF62"));
		save.setPrefWidth(150);
		save.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
		save.setTextFill(Color.WHITE);
		save.setOnAction(e -> {
			saveValues();
		});
		buttonsPane.getChildren().addAll(cancel, reset, save);
		loadValues();
	}

	private void loadValues() {
		displayModsThumbnail.setSelected(MPBConfig.getBoolean(EnumConfig.displayModsThumbnail));
		// For each parameter
	}

	private void saveValues() {
		MPBConfig.setProperty(EnumConfig.displayModsThumbnail, displayModsThumbnail.isSelected());
		// For each parameter
		MPBConfig.saveConfig();
	}

}

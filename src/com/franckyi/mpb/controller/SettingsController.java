package com.franckyi.mpb.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.franckyi.mpb.MPBApplication;
import com.franckyi.mpb.MPBConfig;
import com.franckyi.mpb.MPBConfig.EnumConfig;
import com.franckyi.mpb.view.MPBFonts;
import com.franckyi.mpb.view.fxml.FXMLFile;
import com.franckyi.mpb.view.nodes.NormalButton;
import com.jfoenix.controls.JFXToggleButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

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
	
	@FXML
	void clearWebCache(ActionEvent e){
		MPBApplication.INSTANCE.cache.clear();
		((ModBrowserController) MPBApplication.INSTANCE.parents.get(FXMLFile.MOD_BROWSER).getUserData()).updateModBrowser();
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

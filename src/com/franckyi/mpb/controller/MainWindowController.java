package com.franckyi.mpb.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.franckyi.mpb.MPBApplication;
import com.franckyi.mpb.view.fxml.FXMLFile;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MainWindowController implements Initializable {

	@FXML
	private VBox menu;
	
	@FXML
	private ImageView logo;
	
	@FXML
	private Pane body;
	
	@FXML
	private Label myMods, myModpacks, modBrowser, settings;
	
	private List<Label> topMenu, bottomMenu, allMenu;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topMenu = Arrays.asList(myMods, myModpacks, modBrowser);
		bottomMenu = Arrays.asList(settings);
		allMenu = new ArrayList<Label>(topMenu);
		allMenu.addAll(bottomMenu);
		showModBrowser(null);
		logo.setImage(new Image(getClass().getResourceAsStream("../view/img/logo.png")));
	}

	@FXML
	void showMyMods(MouseEvent event) {
		show(myMods);
	}

	@FXML
	void showMyModpacks(MouseEvent event) {
		show(myModpacks);
	}

	@FXML
	void showModBrowser(MouseEvent event) {
		show(modBrowser);
		body.getChildren().add(MPBApplication.INSTANCE.parents.get(FXMLFile.MOD_BROWSER));
	}
	
	@FXML
	void showSettings(MouseEvent event) {
		show(settings);
		body.getChildren().add(MPBApplication.INSTANCE.parents.get(FXMLFile.SETTINGS));
	}
	
	private void show(Label l) {
		body.getChildren().clear();
		select(l);
		for(Label label : allMenu)
			if(!label.equals(l))
				unselect(label);
	}
	
	private void select(Label label) {
		label.setTextFill(Color.WHITE);
		if(topMenu.contains(label))
			label.setBackground(new Background(new BackgroundFill(Color.web("#8CAF62"), null, null)));
		else
			label.setBackground(new Background(new BackgroundFill(Color.web("#7FA5C4"), null, null)));
	}
	
	private void unselect(Label label) {
		label.setTextFill(Color.BLACK);
		label.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}

}

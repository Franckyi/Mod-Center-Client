package com.franckyi.modcenter.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.view.MCCColors;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;

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
	private ImageView logo, banner;

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
		banner.setImage(new Image(getClass().getResourceAsStream("../view/img/banner.png")));
	}

	@FXML
	void showMyMods(MouseEvent event) {
		show(myMods);
		setView(FXMLFile.MY_MODS);
	}

	@FXML
	void showMyModpacks(MouseEvent event) {
		show(myModpacks);
		body.getChildren().clear();
	}

	@FXML
	void showModBrowser(MouseEvent event) {
		show(modBrowser);
		setView(FXMLFile.MOD_BROWSER);
	}

	@FXML
	void showSettings(MouseEvent event) {
		show(settings);
		setView(FXMLFile.SETTINGS);
	}

	private void show(Label l) {
		select(l);
		for (Label label : allMenu)
			if (!label.equals(l))
				unselect(label);
	}

	public void setView(FXMLFile file) {
		body.getChildren().clear();
		body.getChildren().add(ModCenterClient.INSTANCE.parents.get(file));
	}

	private void select(Label label) {
		label.setTextFill(Color.WHITE);
		if (topMenu.contains(label))
			label.setBackground(new Background(new BackgroundFill(MCCColors.GREEN, null, null)));
		else if (bottomMenu.contains(label))
			label.setBackground(new Background(new BackgroundFill(MCCColors.BLUE, null, null)));
		else
			label.setBackground(new Background(new BackgroundFill(MCCColors.RED, null, null)));
	}

	private void unselect(Label label) {
		label.setTextFill(Color.BLACK);
		label.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}

	public static MainWindowController get() {
		return (MainWindowController) ModCenterClient.INSTANCE.parents.get(FXMLFile.MAIN).getUserData();
	}

}

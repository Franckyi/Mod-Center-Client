package com.franckyi.modcenter.client.controller;

import java.util.List;

import com.franckyi.modcenter.api.ProjectFile;
import com.franckyi.modcenter.client.view.MCCFonts;
import com.franckyi.modcenter.client.view.region.FileVisual;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

public class SelectFileController {

	@FXML
	private Label modName;

	@FXML
	private VBox spContent;

	public void init(String name, List<ProjectFile> files) {
		modName.setText(name);
		modName.setFont(MCCFonts.BIG_24);
		for (ProjectFile file : files)
			spContent.getChildren().addAll(new Separator(), new FileVisual(file));
		if (!spContent.getChildren().isEmpty())
			spContent.getChildren().remove(0);
	}

}

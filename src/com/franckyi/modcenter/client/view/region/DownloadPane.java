package com.franckyi.modcenter.client.view.region;

import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.client.core.tasks.DownloadModTask;
import com.franckyi.modcenter.client.view.MCCFonts;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DownloadPane extends Pane {
	
	private DownloadModTask task;
	
	private JFXProgressBar progressBar;
	private Label fileName;
	private JFXCheckBox finished;
	
	public DownloadPane(ProjectFile file) {
		setPrefHeight(50);
		setPrefWidth(USE_COMPUTED_SIZE);
	
		task = new DownloadModTask(file);
		task.setOnSucceeded(e -> {
			finished.setSelected(true);
		});
		
		progressBar = new JFXProgressBar();
		progressBar.progressProperty().bind(task.progressProperty());
		progressBar.relocate(25, 25);
		
		fileName = new Label(file.getFileName());
		fileName.setFont(MCCFonts.NORMAL_18);
		fileName.relocate(250, 34);
		
		finished = new JFXCheckBox();
		finished.setCheckedColor(Color.GREEN);
		finished.setDisable(true);
		finished.relocate(800, 30);
	}

}

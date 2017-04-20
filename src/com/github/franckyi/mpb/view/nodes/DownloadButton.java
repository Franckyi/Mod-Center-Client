package com.github.franckyi.mpb.view.nodes;

import java.io.IOException;

import com.franckyi.modcenter.api.MCVersion;
import com.franckyi.modcenter.api.Project;
import com.franckyi.modcenter.api.ProjectFile;
import com.github.franckyi.mpb.core.tasks.SelectModTask;
import com.github.franckyi.mpb.view.MPBFonts;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class DownloadButton extends HBox {

	private JFXButton download;
	private JFXButton options;

	public DownloadButton(Project project, MCVersion version) {
		download = new JFXButton("Download");
		download.setFont(MPBFonts.BIG_20);
		download.setTextFill(Color.WHITE);
		download.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 10 0 0 10;");
		download.setBackground(new Background(
				new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(10, 0, 0, 10, false), Insets.EMPTY)));
		download.setPrefSize(150, 50);
		download.setOnAction(e -> {
			Thread task = new Thread(new SelectModTask(project, version, true));
			task.setName("SelectModTask");
			task.start();
		});

		options = new JFXButton("...");
		options.setFont(MPBFonts.BIG_20);
		options.setTextFill(Color.WHITE);
		options.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 0 10 10 0;");
		options.setBackground(new Background(
				new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(0, 10, 10, 0, false), Insets.EMPTY)));
		options.setPrefSize(60, 50);
		options.setOnAction(e -> {
			Thread task = new Thread(new SelectModTask(project, version, false));
			task.setName("SelectModTask");
			task.start();
		});
		this.getChildren().addAll(download, options);
	}

	public DownloadButton(ProjectFile file) {
		download = new JFXButton("Download");
		download.setFont(MPBFonts.NORMAL_14);
		download.setTextFill(Color.WHITE);
		download.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 10;");
		download.setBackground(
				new Background(new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(10), Insets.EMPTY)));
		download.setPrefSize(100, 25);
		download.setOnAction(e -> {
			try {
				SelectModTask.startDownload(SelectModTask.getUrl(file), file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		this.getChildren().add(download);
	}

}

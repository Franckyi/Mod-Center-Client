package com.franckyi.modcenter.client.view.nodes;

import com.franckyi.modcenter.api.beans.Project;
import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.client.core.tasks.DownloadModTask;
import com.franckyi.modcenter.client.core.tasks.SelectFileTask;
import com.franckyi.modcenter.client.view.MCCColors;
import com.franckyi.modcenter.client.view.MCCFonts;
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

	public DownloadButton(Project project, String version) {
		download = new JFXButton("Download");
		download.setFont(MCCFonts.BIG_20);
		download.setTextFill(Color.WHITE);
		download.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 10 0 0 10;");
		download.setBackground(new Background(
				new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(10, 0, 0, 10, false), Insets.EMPTY)));
		download.setPrefSize(150, 50);
		download.setOnAction(e -> {
			new Thread(new SelectFileTask(project, version, true)).start();
		});

		options = new JFXButton("...");
		options.setFont(MCCFonts.BIG_20);
		options.setTextFill(Color.WHITE);
		options.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 0 10 10 0;");
		options.setBackground(new Background(
				new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(0, 10, 10, 0, false), Insets.EMPTY)));
		options.setPrefSize(60, 50);
		options.setOnAction(e -> {
			new Thread(new SelectFileTask(project, version, false)).start();
		});
		this.getChildren().addAll(download, options);
	}

	public DownloadButton(ProjectFile file) {
		download = new JFXButton("Download");
		download.setFont(MCCFonts.BIG_20);
		download.setTextFill(Color.WHITE);
		download.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 10;");
		download.setBackground(new Background(new BackgroundFill(MCCColors.getColor(file.getType()), new CornerRadii(10), Insets.EMPTY)));
		download.setPrefSize(175, 25);
		download.setOnAction(e -> {
			new Thread(new DownloadModTask(file)).start();
		});
		this.getChildren().add(download);
	}

}

package com.franckyi.modcenter.client.view.nodes;

import com.franckyi.modcenter.api.Project;
import com.franckyi.modcenter.api.ProjectFile;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.core.tasks.SelectFileTask;
import com.franckyi.modcenter.client.view.MCCFonts;
import com.franckyi.modcenter.client.view.region.LoadingPane;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.Scene;
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
			Thread task = new Thread(new SelectFileTask(project, version, true));
			task.setName("SelectModTask");
			task.start();
		});

		options = new JFXButton("...");
		options.setFont(MCCFonts.BIG_20);
		options.setTextFill(Color.WHITE);
		options.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 0 10 10 0;");
		options.setBackground(new Background(
				new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(0, 10, 10, 0, false), Insets.EMPTY)));
		options.setPrefSize(60, 50);
		options.setOnAction(e -> {
			Thread task = new Thread(new SelectFileTask(project, version, false));
			task.setName("SelectModTask");
			task.start();
		});
		this.getChildren().addAll(download, options);
	}

	public DownloadButton(ProjectFile file, Color color) {
		download = new JFXButton("Download");
		download.setFont(MCCFonts.BIG_20);
		download.setTextFill(Color.WHITE);
		download.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 10;");
		download.setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)));
		download.setPrefSize(175, 25);
		download.setOnAction(e -> {
			ModCenterClient.INSTANCE.secondaryStage.setScene(new Scene(new LoadingPane()));
			new Thread() {
				@Override
				public void run() {
					try {
						SelectFileTask.startDownload(file);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}.start();
		});
		this.getChildren().add(download);
	}

}

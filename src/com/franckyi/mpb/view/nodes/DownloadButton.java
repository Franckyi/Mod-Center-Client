package com.franckyi.mpb.view.nodes;

import com.franckyi.mpb.core.curse.MCVersion;
import com.franckyi.mpb.core.curse.ModLogical;
import com.franckyi.mpb.core.tasks.SelectModTask;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DownloadButton extends HBox {

	private static final Font BIG = Font.font("Segoe UI", FontWeight.BOLD, 24);

	private JFXButton download;
	private JFXButton options;

	public DownloadButton(ModLogical mod, MCVersion version) {
		download = new JFXButton("Download");
		download.setFont(BIG);
		download.setTextFill(Color.WHITE);
		download.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 10 0 0 10;");
		download.setBackground(new Background(
				new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(10, 0, 0, 10, false), Insets.EMPTY)));
		download.setPrefSize(150, 50);
		download.setOnAction(e -> {
			Thread task = new Thread(new SelectModTask(mod.getProjectUrl(), version, true));
			task.setName("SelectModTask");
			task.start();
		});

		options = new JFXButton("...");
		options.setFont(BIG);
		options.setTextFill(Color.WHITE);
		options.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 0 10 10 0;");
		options.setBackground(new Background(
				new BackgroundFill(Color.web("#8CAF62"), new CornerRadii(0, 10, 10, 0, false), Insets.EMPTY)));
		options.setPrefSize(60, 50);
		options.setOnAction(e -> {
			Thread task = new Thread(new SelectModTask(mod.getProjectUrl(), version, false));
			task.setName("SelectModTask");
			task.start();
		});
		this.getChildren().addAll(download, options);
	}

}

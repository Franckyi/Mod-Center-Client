package com.franckyi.modcenter.client.view.region;

import com.franckyi.modcenter.client.view.MPBFonts;
import com.jfoenix.controls.JFXProgressBar;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class LoadingPane extends VBox {

	private Label label;
	private JFXProgressBar progressBar;

	public LoadingPane() {
		this.setPrefSize(1048, 100);
		this.setSpacing(20);
		this.setAlignment(Pos.CENTER);
		label = new Label("Loading...");
		label.setFont(MPBFonts.BIG_24);
		progressBar = new JFXProgressBar();
		this.getChildren().addAll(label, progressBar);
	}

	public LoadingPane(String text) {
		this();
		label.setText(text);
	}

	public LoadingPane(String text, ObservableValue<Number> value) {
		this(text);
		progressBar.progressProperty().bind(value);
	}

}

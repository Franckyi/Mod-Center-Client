package com.franckyi.mpb.view.nodes;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class NormalButton extends JFXButton {

	public NormalButton(String text, Color color) {
		this.setText(text);
		this.setStyle("-fx-border-color: rgba(0,0,0,0.25); -fx-border-radius: 10;");
		this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), Insets.EMPTY)));
	}

}

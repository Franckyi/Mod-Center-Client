package com.franckyi.modcenter.client.view.nodes;

import com.franckyi.modcenter.client.view.MPBFonts;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class NormalLabel extends Label {

	public NormalLabel(String fileName) {
		this.setFont(MPBFonts.NORMAL_14);
		this.setPadding(new Insets(5));
		this.setText(fileName);
	}

}

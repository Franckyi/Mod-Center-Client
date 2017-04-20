package com.github.franckyi.mpb.view.nodes;

import com.github.franckyi.mpb.view.MPBFonts;

import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class NormalLabel extends Label {

	public NormalLabel(String fileName) {
		this.setFont(MPBFonts.NORMAL_14);
		this.setPadding(new Insets(5));
		this.setText(fileName);
	}

}

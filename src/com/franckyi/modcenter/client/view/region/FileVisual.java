package com.franckyi.modcenter.client.view.region;

import java.text.NumberFormat;
import java.util.Locale;

import com.franckyi.modcenter.api.ProjectFile;
import com.franckyi.modcenter.client.view.MCCColors;
import com.franckyi.modcenter.client.view.MCCFonts;
import com.franckyi.modcenter.client.view.nodes.DownloadButton;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class FileVisual extends Pane {

	private Color color;
	private Label name, size, date, type, mcVersion, dlCount;
	private DownloadButton download;

	public FileVisual(ProjectFile file) {
		this.setWidth(1047);
		this.setHeight(100);
		color = MCCColors.getColor(file.getType());
		name = new Label(file.getFileName());
		size = new Label(file.getSize());
		date = new Label("Uploaded " + file.getUploaded().toLocalDate().toString());
		type = new Label(file.getType().getDisplayText());
		mcVersion = new Label("for MC " + file.getVersion());
		dlCount = new Label(NumberFormat.getNumberInstance(Locale.US).format(file.getDownloads()) + " downloads");
		download = new DownloadButton(file, color);
		placeAndBuild();
	}

	private void placeAndBuild() {

		name.setFont(MCCFonts.BIG_20);
		name.relocate(25, 15);

		size.setFont(MCCFonts.NORMAL_14);
		size.relocate(100, 45);

		date.setFont(MCCFonts.NORMAL_14);
		date.relocate(100, 70);

		type.setFont(MCCFonts.NORMAL_18);
		type.setTextFill(color);
		type.relocate(500, 45);

		mcVersion.setFont(MCCFonts.NORMAL_18);
		mcVersion.relocate(500, 70);

		dlCount.setFont(MCCFonts.NORMAL_14);
		dlCount.setPrefWidth(175);
		dlCount.setAlignment(Pos.CENTER);
		dlCount.relocate(800, 65);

		download.relocate(800, 20);

		this.getChildren().addAll(name, size, date, type, mcVersion, dlCount, download);
	}

}

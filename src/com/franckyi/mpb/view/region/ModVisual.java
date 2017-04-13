package com.franckyi.mpb.view.region;

import com.franckyi.mpb.MPBConfig;
import com.franckyi.mpb.MPBConfig.EnumConfig;
import com.franckyi.mpb.MPBUtils;
import com.franckyi.mpb.core.curse.MCVersion;
import com.franckyi.mpb.core.curse.ModLogical;
import com.franckyi.mpb.core.event.ViewModEvent;
import com.franckyi.mpb.view.MPBFonts;
import com.franckyi.mpb.view.nodes.DownloadButton;
import com.franckyi.mpb.view.nodes.NormalButton;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ModVisual extends Pane {

	private ModLogical modLogical;
	private ImageView image;
	private Label name;
	private Label monthlyDl;
	private Label totalDl;
	private Label updated;
	private Label created;
	private NormalButton view;
	private DownloadButton download;

	public ModVisual(ModLogical mod, MCVersion version) {
		this.setWidth(950);
		this.setHeight(150);
		this.modLogical = mod;
		if (MPBConfig.getBoolean(EnumConfig.displayModsThumbnail))
			this.image = new ImageView(new Image(mod.getImage()));
		else {
			this.image = new ImageView(new Image(getClass().getResourceAsStream("../img/logo.png")));
			this.image.setVisible(false);
		}
		this.name = new Label(MPBUtils.unescapeHTML(mod.getName()));
		this.monthlyDl = new Label(mod.getMonthlyDl());
		this.totalDl = new Label(mod.getTotalDl());
		this.updated = new Label(mod.getUpdated());
		this.created = new Label(mod.getCreated());
		this.view = new NormalButton("View", Color.web("#7FA5C4"));
		this.download = new DownloadButton(mod, version);
		placeAndBuild();
	}

	private void placeAndBuild() {
		image.setPreserveRatio(true);
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.relocate(25, 25 + (100 - image.getImage().getHeight()) / 2);

		name.setFont(MPBFonts.BIG_24);
		name.relocate(150, 25);

		monthlyDl.setFont(MPBFonts.NORMAL);
		monthlyDl.relocate(150, 75);

		totalDl.setFont(MPBFonts.NORMAL);
		totalDl.relocate(400, 75);

		updated.setFont(MPBFonts.NORMAL);
		updated.relocate(150, 100);

		created.setFont(MPBFonts.NORMAL);
		created.relocate(400, 100);

		view.setFont(MPBFonts.BIG_24);
		view.setTextFill(Color.WHITE);
		view.relocate(650, 24);
		view.setPrefWidth(210);
		view.setOnAction(new ViewModEvent(modLogical.getProjectUrl()));

		download.relocate(650, 76);

		this.getChildren().addAll(image, name, monthlyDl, totalDl, updated, created, view, download);
	}

}

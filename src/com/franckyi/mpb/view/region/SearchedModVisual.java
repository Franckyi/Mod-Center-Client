package com.franckyi.mpb.view.region;

import com.franckyi.mpb.MPBConfig;
import com.franckyi.mpb.MPBConfig.EnumConfig;
import com.franckyi.mpb.MPBUtils;
import com.franckyi.mpb.core.curse.SearchedModLogical;
import com.franckyi.mpb.core.event.ViewModEvent;
import com.franckyi.mpb.view.MPBFonts;
import com.franckyi.mpb.view.nodes.DownloadButton;
import com.franckyi.mpb.view.nodes.NormalButton;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SearchedModVisual extends Pane {

	private SearchedModLogical modLogical;
	private ImageView image;
	private Label name;
	private Label description;
	private Label author;
	private Label updated;
	private NormalButton view;
	private DownloadButton download;

	public SearchedModVisual(SearchedModLogical mod) {
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
		this.description = new Label(MPBUtils.unescapeHTML(mod.getDescription()));
		this.author = new Label(MPBUtils.unescapeHTML(mod.getAuthor()));
		this.updated = new Label(mod.getUpdated());
		this.view = new NormalButton("View", Color.web("#7FA5C4"));
		this.download = new DownloadButton(mod);
		placeAndBuild();
	}

	private void placeAndBuild() {
		image.setPreserveRatio(true);
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.relocate(25, 25 + (100 - image.getImage().getHeight()) / 2);

		name.setFont(MPBFonts.BIG_24);
		name.relocate(150, 25);

		author.setFont(MPBFonts.NORMAL);
		author.relocate(150, 75);

		description.setFont(MPBFonts.NORMAL);
		description.relocate(400, 75);

		updated.setFont(MPBFonts.NORMAL);
		updated.relocate(150, 100);

		view.setFont(MPBFonts.BIG_24);
		view.setTextFill(Color.WHITE);
		view.relocate(650, 24);
		view.setPrefWidth(210);
		view.setOnAction(new ViewModEvent(modLogical.getProjectUrl()));

		download.relocate(650, 76);

		this.getChildren().addAll(image, name, author, description, updated, view, download);
	}

}

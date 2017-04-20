package com.github.franckyi.mpb.view.region;

import java.text.NumberFormat;
import java.util.Locale;

import com.franckyi.modcenter.api.MCVersion;
import com.franckyi.modcenter.api.Project;
import com.github.franckyi.mpb.MPBConfig;
import com.github.franckyi.mpb.MPBUtils;
import com.github.franckyi.mpb.MPBConfig.EnumConfig;
import com.github.franckyi.mpb.core.event.ViewModEvent;
import com.github.franckyi.mpb.view.MPBFonts;
import com.github.franckyi.mpb.view.nodes.DownloadButton;
import com.github.franckyi.mpb.view.nodes.NormalButton;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ProjectVisual extends Pane {

	private Project project;
	private ImageView image;
	private Label name;
	private Label author;
	private Label totalDl;
	private Label updated;
	private Label description;
	private NormalButton view;
	private DownloadButton download;

	public ProjectVisual(Project project, MCVersion version) {
		basicConstructor(project);
		this.download = new DownloadButton(project, version);
		placeAndBuild();
	}

	private void basicConstructor(Project project) {
		this.setPrefWidth(1030);
		this.setPrefHeight(175);
		this.project = project;
		if (project.getThumbnail() != "" && MPBConfig.getBoolean(EnumConfig.displayModsThumbnail))
			this.image = new ImageView(new Image(project.getThumbnail()));
		else {
			this.image = new ImageView(new Image(getClass().getResourceAsStream("../img/logo.png")));
			if (!MPBConfig.getBoolean(EnumConfig.displayModsThumbnail))
				this.image.setVisible(false);
		}
		this.name = new Label(MPBUtils.unescapeHTML(project.getName()));
		this.author = new Label("by " + project.getAuthor());
		this.totalDl = new Label(
				NumberFormat.getNumberInstance(Locale.US).format(project.getTotalDl()) + " total downloads");
		this.updated = new Label("Updated " + project.getUpdated().toLocalDate().toString());
		this.description = new Label(project.getDescription());
		this.view = new NormalButton("View", Color.web("#7FA5C4"));
	}

	private void placeAndBuild() {
		image.setPreserveRatio(true);
		image.setFitHeight(100);
		image.setFitWidth(100);
		image.relocate(25,
				((image.getImage().getHeight() < 100) ? (20 + (100 - image.getImage().getHeight()) / 2) : 35));

		name.setFont(MPBFonts.BIG_24);
		name.relocate(150, 25);

		author.setFont(MPBFonts.NORMAL_14);
		author.relocate(150, 60);

		totalDl.setFont(MPBFonts.NORMAL_14);
		totalDl.setPrefWidth(205);
		totalDl.setAlignment(Pos.CENTER);
		totalDl.relocate(775, 135);

		updated.setFont(MPBFonts.NORMAL_14);
		updated.relocate(150, 80);

		description.setFont(MPBFonts.NORMAL_18);
		description.setWrapText(true);
		description.setMaxWidth(600);
		description.setMaxHeight(70);
		description.relocate(150, 105);

		view.setFont(MPBFonts.BIG_24);
		view.setTextFill(Color.WHITE);
		view.relocate(775, 24);
		view.setPrefWidth(210);
		view.setOnAction(new ViewModEvent(project.getProjectUrl()));

		download.relocate(775, 76);

		this.getChildren().addAll(image, name, author, totalDl, updated, description, view, download);
	}

}

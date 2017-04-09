package com.franckyi.mpb.core;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.franckyi.mpb.MPBApplication;
import com.franckyi.mpb.core.json.JsonModFile;
import com.franckyi.mpb.core.tasks.SelectModTask;
import com.franckyi.mpb.view.nodes.NormalButton;
import com.franckyi.mpb.view.region.LoadingPane;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class ModFileCell extends RecursiveTreeObject<ModFileCell> {
	
	public StringProperty version;
	public StringProperty type;
	public StringProperty name;
	public ObjectProperty<ModFileCellDLButton> download;
	
	public ModFileCell(JsonModFile json, String title) {
		this.version = new SimpleStringProperty(json.version) ;
        this.type = new SimpleStringProperty(json.type);
        this.name = new SimpleStringProperty(json.name);
        this.download = new SimpleObjectProperty<ModFileCellDLButton>(new ModFileCellDLButton(json.url, json, title));
	}
	
	public class ModFileCellDLButton extends NormalButton{

		public ModFileCellDLButton(String url, JsonModFile modFile, String modTitle) {
			super("Download", Color.WHITE);
			this.setOnAction(event -> {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						MPBApplication.INSTANCE.secondaryStage.setScene(new Scene(new LoadingPane()));
						MPBApplication.INSTANCE.secondaryStage.setTitle("Selecting");
						MPBApplication.INSTANCE.secondaryStage.show();
					}
				});
				new Thread() {
					@Override
					public void run() {
						try {
							Document doc = Jsoup.connect(url).get();
							String dlUrl = doc.select(".download-link").get(0).attr("data-href")
									.replaceAll("http://addons.curse.cursecdn.com/", "https://addons-origin.cursecdn.com/");
							String fileName = dlUrl.split("/")[dlUrl.split("/").length - 1];
							SelectModTask.startDownload(dlUrl, fileName, modFile, modTitle);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			});
		}
		
		
		
	}

}

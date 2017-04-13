package com.franckyi.mpb.core.tasks;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.franckyi.mpb.MPBApplication;
import com.franckyi.mpb.core.curse.MCVersion;
import com.franckyi.mpb.core.data.DataFiles;
import com.franckyi.mpb.core.data.cache.ModDownloadCache;
import com.franckyi.mpb.core.json.JsonMod;
import com.franckyi.mpb.core.json.JsonModFile;
import com.franckyi.mpb.view.region.LoadingPane;
import com.franckyi.mpb.view.region.SelectModTable;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class SelectModTask extends Task<Void> {

	public static final String curseWidgetUrl = "https://widget.mcf.li/mc-mods/minecraft/";

	private String projectUrl;
	private MCVersion version;
	private boolean latest;

	public SelectModTask(String projectUrl, MCVersion version, boolean latest) {
		this.projectUrl = projectUrl;
		this.version = version;
		this.latest = latest;
	}

	@Override
	protected Void call() throws Exception {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MPBApplication.INSTANCE.secondaryStage.setScene(new Scene(new LoadingPane()));
				MPBApplication.INSTANCE.secondaryStage.setTitle("Loading");
				MPBApplication.INSTANCE.secondaryStage.show();
			}
		});
		MPBApplication.print("Selecting mod file for project " + projectUrl + " (auto=" + latest + ")");
		Gson gson = new Gson();
		URL url = new URL(curseWidgetUrl + projectUrl + ".json");
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.connect();
		JsonParser jp = new JsonParser();
		JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonMod mod = gson.fromJson(root, JsonMod.class);
		if (latest) {
			JsonModFile modFile;
			if (version.equals(MCVersion.ALL))
				modFile = mod.download;
			else {
				List<JsonModFile> files = mod.versions.get(version.displayText);
				files.sort(new Comparator<JsonModFile>() {
					@Override
					public int compare(JsonModFile o1, JsonModFile o2) {
						return (o1.id < o2.id) ? 1 : -1;
					}
				});
				modFile = files.get(0);
			}
			Document doc = Jsoup.connect(modFile.url).get();
			String dlUrl = doc.select(".download-link").get(0).attr("data-href")
					.replaceAll("http://addons.curse.cursecdn.com/", "https://addons-origin.cursecdn.com/");
			String fileName = dlUrl.split("/")[dlUrl.split("/").length - 1];
			startDownload(dlUrl, fileName, modFile, mod.title);
		} else {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MPBApplication.INSTANCE.secondaryStage.setScene(new Scene(new SelectModTable(mod)));
					MPBApplication.INSTANCE.secondaryStage.setTitle("Selecting");
					MPBApplication.INSTANCE.secondaryStage.show();
				}
			});
			MPBApplication.print("Showing file list, waiting for user to choose...");
		}
		return null;
	}

	public static void startDownload(String dlUrl, String fileName, JsonModFile modFile, String modTitle) throws MalformedURLException {
		DownloadModTask task = new DownloadModTask(new URL(dlUrl), new File(DataFiles.MOD_CACHE_FOLDER.getPath() + "/" + fileName));
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MPBApplication.INSTANCE.secondaryStage.setScene(new Scene(new LoadingPane("Downloading " + fileName, task.progressProperty())));
				MPBApplication.INSTANCE.secondaryStage.setTitle("Downloading");
				MPBApplication.INSTANCE.secondaryStage.show();
			}
		});
		task.setOnSucceeded(event -> {
			try {
				MPBApplication.print("Download ended : " + modFile.name + " - Now adding file to cache");
				ModDownloadCache.addFileToCache(modTitle, modFile);
				MPBApplication.INSTANCE.secondaryStage.hide();
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
		});
		Thread th = new Thread(task);
		th.setName("DownloadModTask");
		th.start();
	}

}

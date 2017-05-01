package com.franckyi.modcenter.client.core.tasks;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;

import com.franckyi.modcenter.api.CurseURLFormatter;
import com.franckyi.modcenter.api.MCVersion;
import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.Project;
import com.franckyi.modcenter.api.ProjectFile;
import com.franckyi.modcenter.client.MPBApplication;
import com.franckyi.modcenter.client.core.data.DataFiles;
import com.franckyi.modcenter.client.core.data.cache.ModDownloadCache;
import com.franckyi.modcenter.client.view.region.LoadingPane;
import com.franckyi.modcenter.client.view.region.SelectModTable;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class SelectModTask extends Task<Void> {

	private Project project;
	private MCVersion version;
	private boolean latest;

	public SelectModTask(Project project, MCVersion version, boolean latest) {
		this.project = project;
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
		MPBApplication.print("Selecting mod file for project " + project.getProjectUrl() + " (auto=" + latest + ")");
		if (latest) {
			ProjectFile file = ModCenterAPI.getFilesFromProject(project, version).get(0);
			startDownload(getUrl(file), file);
		} else {
			List<ProjectFile> files = ModCenterAPI.getFilesFromProject(project);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					MPBApplication.INSTANCE.secondaryStage.setScene(new Scene(new SelectModTable(files)));
					MPBApplication.INSTANCE.secondaryStage.setTitle("Selecting");
					MPBApplication.INSTANCE.secondaryStage.show();
				}
			});
		}
		return null;
	}

	public static String getUrl(ProjectFile file) throws IOException {
		return Jsoup.connect(Jsoup.connect(CurseURLFormatter.format(file.getFileUrl())).execute().header("location"))
				.execute().header("location");
	}

	public static void startDownload(String dlUrl, ProjectFile file) throws MalformedURLException {
		String dlFileName = dlUrl.split("/")[dlUrl.split("/").length - 1];
		DownloadModTask task = new DownloadModTask(new URL(dlUrl),
				new File(DataFiles.MOD_CACHE_FOLDER.getPath() + "/" + dlFileName));
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				MPBApplication.INSTANCE.secondaryStage.setScene(
						new Scene(new LoadingPane("Downloading " + file.getFileName(), task.progressProperty())));
				MPBApplication.INSTANCE.secondaryStage.setTitle("Downloading");
				MPBApplication.INSTANCE.secondaryStage.show();
			}
		});
		task.setOnSucceeded(event -> {
			try {
				MPBApplication.print("Download ended : " + dlFileName + " - Now adding file to cache");
				ModDownloadCache.addFileToCache(file);
				MPBApplication.INSTANCE.secondaryStage.hide();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread th = new Thread(task);
		th.setName("DownloadModTask");
		th.start();
	}

}

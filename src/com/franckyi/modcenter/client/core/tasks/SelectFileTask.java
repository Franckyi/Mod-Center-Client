package com.franckyi.modcenter.client.core.tasks;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.franckyi.modcenter.api.CurseURLFormatter;
import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.Project;
import com.franckyi.modcenter.api.ProjectFile;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.controller.ModBrowserController;
import com.franckyi.modcenter.client.controller.SelectFileController;
import com.franckyi.modcenter.client.core.data.DataFiles;
import com.franckyi.modcenter.client.core.data.cache.ProjectDownloadCache;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;
import com.franckyi.modcenter.client.view.region.LoadingPane;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class SelectFileTask extends Task<Void> {

	private static Project project;
	private String version;
	private boolean latest;

	public SelectFileTask(Project project, String version, boolean latest) {
		SelectFileTask.project = project;
		this.version = version;
		this.latest = latest;
	}

	@Override
	protected Void call() throws Exception {
		ModCenterClient.print("Selecting mod file for project " + project.getProjectUrl() + " (auto=" + latest + ")");
		if (latest) {
			ProjectFile file = ModCenterAPI.getFilesFromProject(project, version).get(0);
			startDownload(file);
		} else {
			List<ProjectFile> files = ModCenterAPI.getFilesFromProject(project, version);
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					FXMLFile file = new FXMLFile(FXMLFile.SELECT_FILE, project);
					try {
						ModCenterClient.INSTANCE.loadFXML(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					ModBrowserController.get().addTab(SelectFileController.get(file).init(files),
							project.getName());
				}
			});
		}
		return null;
	}

	private static String getUrl(ProjectFile file) throws IOException {
		Response response = Jsoup.connect(CurseURLFormatter.format(file.getFileUrl())).ignoreContentType(true)
				.execute();
		return (response.url().toString());
	}

	public static void startDownload(ProjectFile file) throws IOException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ModCenterClient.INSTANCE.secondaryStage.setScene(new Scene(new LoadingPane()));
				ModCenterClient.INSTANCE.secondaryStage.setTitle("Downloading");
				ModCenterClient.INSTANCE.secondaryStage.show();
			}
		});
		String dlUrl = getUrl(file);
		String dlFileName = dlUrl.split("/")[dlUrl.split("/").length - 1];
		DownloadModTask task = new DownloadModTask(new URL(dlUrl),
				new File(DataFiles.MOD_CACHE_FOLDER.getPath() + "/" + dlFileName));
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ModCenterClient.INSTANCE.secondaryStage.setScene(
						new Scene(new LoadingPane("Downloading " + file.getFileName(), task.progressProperty())));
			}
		});
		task.setOnSucceeded(event -> {
			try {
				ModCenterClient.print("Download ended : " + dlFileName + " - Now adding file to cache");
				ProjectDownloadCache.addFileToCache(file, project);
				ModCenterClient.INSTANCE.secondaryStage.hide();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread th = new Thread(task);
		th.setName("DownloadModTask");
		th.start();
	}

}

package com.franckyi.modcenter.client.core.tasks;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.beans.Project;
import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.api.misc.CurseParser;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.controller.ModBrowserController;
import com.franckyi.modcenter.client.core.data.DataFiles;
import com.franckyi.modcenter.client.core.data.cache.ProjectDownloadCache;
import com.franckyi.modcenter.client.view.region.LoadingPane;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;

public class DownloadModTask extends Task<Void> {

	private ProjectFile file;
	private Project project;
	private URL source;
	private File destination;
	private boolean hasParent;

	public DownloadModTask(ProjectFile file) {
		this.file = file;
	}

	@Override
	protected Void call() throws Exception {
		Thread.currentThread().setName(getClass().getSimpleName());
		this.project = ModCenterAPI.getProjectFromFile(file);
		Platform.runLater(new DownloadStarter());
		String dlUrl = CurseParser.getFinalUrl(file);
		String dlFileName = dlUrl.split("/")[dlUrl.split("/").length - 1];
		this.source = new URL(dlUrl);
		this.destination = new File(DataFiles.MOD_CACHE_FOLDER.getPath() + "/" + dlFileName);
		Platform.runLater(new InitializeProgress());

		ModCenterClient.print("Download started : " + destination.getName());
		this.updateProgress(0, 1);
		HttpURLConnection httpConnection = (HttpURLConnection) (source.openConnection());
		long completeFileSize = httpConnection.getContentLength();
		BufferedInputStream bis = new BufferedInputStream(httpConnection.getInputStream());
		FileOutputStream fis = new FileOutputStream(destination);
		byte[] buffer = new byte[1024];
		long dl = 0;
		int count = 0;
		while ((count = bis.read(buffer, 0, 1024)) != -1) {
			fis.write(buffer, 0, count);
			dl += count;
			this.updateProgress(dl, completeFileSize);
		}
		fis.close();
		bis.close();
		return null;
	}

	@Override
	protected void succeeded() {
		try {
			ModCenterClient.print("Download ended : " + destination.getName() + " - Now adding file to cache");
			ProjectDownloadCache.addFileToCache(file, project);
			if (ModBrowserController.get().tabPane.getTabs().size() != 1)
				ModBrowserController.get().tabPane.getTabs()
						.remove(ModBrowserController.get().tabPane.getSelectionModel().getSelectedIndex());
			ModBrowserController.get().tabPane.setDisable(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class DownloadStarter implements Runnable {

		@Override
		public void run() {
			ModBrowserController.get().tabPane.setDisable(true);
			// create tab
		}

	}

	private class InitializeProgress implements Runnable {

		@Override
		public void run() {
			// bind progress
		}

	}

}

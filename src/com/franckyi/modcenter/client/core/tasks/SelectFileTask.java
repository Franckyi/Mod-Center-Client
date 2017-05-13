package com.franckyi.modcenter.client.core.tasks;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.beans.Project;
import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.api.beans.ProjectFileFilter;
import com.franckyi.modcenter.api.beans.enums.EnumFileType;
import com.franckyi.modcenter.api.misc.CurseParser;
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

	private Project project;
	private boolean latest;
	private FXMLFile file;
	private ProjectFileFilter filter = ProjectFileFilter.DEFAULT;

	private SelectFileTask(Project project, boolean latest) {
		this.project = project;
		this.latest = latest;
		this.file = new FXMLFile(FXMLFile.SELECT_FILE, project);
	}

	public SelectFileTask(Project project, String version, boolean latest) {
		this(project, latest);
		this.filter = new ProjectFileFilter(version);
	}

	public SelectFileTask(Project project, ProjectFileFilter filter, boolean latest) {
		this(project, latest);
		this.filter = filter;
	}

	@Override
	protected Void call() throws Exception {
		ModCenterClient.print("Selecting mod file for project " + project.getProjectUrl() + " (auto=" + latest + ")");
		List<ProjectFile> allFiles = ModCenterAPI.getFilesFromProject(project, filter);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					ModCenterClient.INSTANCE.loadFXML(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		List<ProjectFile> files = new ArrayList<>();
		if (latest) {
			List<EnumFileType> types = new ArrayList<>();
			for (ProjectFile pfile : allFiles)
				if (!types.contains(pfile.getType())) {
					files.add(pfile);
					types.add(pfile.getType());
				}
			if (check(files, types))
				return null;
		} else
			files.addAll(allFiles);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ModBrowserController.get().addTab(SelectFileController.get(file).init(project, files, latest, filter),
						project);
			}
		});
		return null;
	}

	private boolean check(List<ProjectFile> files, List<EnumFileType> types) throws IOException, SQLException {
		if (files.size() == 1) {
			startDownload(files.get(0));
			return true;
		}
		List<ProjectFile> files2 = new ArrayList<>(files);
		files2.sort(new Comparator<ProjectFile>() {
			@Override
			public int compare(ProjectFile o1, ProjectFile o2) {
				return (o1.getType().getLevel() < o2.getType().getLevel()) ? -1 : 1;
			}
		});
		if (files.get(0) == files.get(1)) {
			startDownload(files.get(0));
			return true;
		}
		return false;
	}

	public static void startDownload(ProjectFile file) throws IOException, SQLException {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ModBrowserController.get().tabPane.setDisable(true);
				ModCenterClient.INSTANCE.secondaryStage.setScene(new Scene(new LoadingPane()));
				ModCenterClient.INSTANCE.secondaryStage.setTitle("Downloading");
				ModCenterClient.INSTANCE.secondaryStage.show();
			}
		});
		String dlUrl = CurseParser.getFinalUrl(file);
		Project project = ModCenterAPI.getProjectFromFile(file);
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
				ModBrowserController.get().tabPane.getTabs()
						.remove(ModBrowserController.get().tabPane.getSelectionModel().getSelectedIndex());
				ModBrowserController.get().tabPane.setDisable(false);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		Thread th = new Thread(task);
		th.setName("DownloadModTask");
		th.start();
	}

}

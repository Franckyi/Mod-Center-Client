package com.franckyi.modcenter.client.core.tasks;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.beans.Project;
import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.api.beans.ProjectFileFilter;
import com.franckyi.modcenter.api.beans.enums.EnumFileType;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.controller.ModBrowserController;
import com.franckyi.modcenter.client.controller.SelectFileController;
import com.franckyi.modcenter.client.view.fxml.FXMLFile;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;

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
		Thread.currentThread().setName(getClass().getSimpleName());
		ModCenterClient.print("Selecting files for project '" + project.getName() + "'");
		List<ProjectFile> allFiles = ModCenterAPI.getFilesFromProject(project, filter);
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
		for (Tab tab : ModBrowserController.get().tabPane.getTabs())
			if (tab.getUserData() != null && tab.getUserData().equals(project.getProjectId())) {
				ModCenterClient.print("Updating...");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						ModBrowserController.get().replaceTab(tab, files);
					}
				});
				return null;
			}
		ModCenterClient.print("Showing...");
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					ModCenterClient.INSTANCE.loadFXML(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				ModBrowserController.get().addTab(SelectFileController.get(file).init(project, files, latest, filter),
						project);
			}
		});

		return null;
	}

	private boolean check(List<ProjectFile> files, List<EnumFileType> types) throws IOException, SQLException {
		if (files.size() == 1) {
			new Thread(new DownloadModTask(files.get(0))).start();
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
			new Thread(new DownloadModTask(files.get(0))).start();
			return true;
		}
		return false;
	}

}

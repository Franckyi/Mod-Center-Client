package com.franckyi.modcenter.client.core.tasks;

import java.util.List;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.Project;
import com.franckyi.modcenter.api.SortFilter;
import com.franckyi.modcenter.client.MPBApplication;
import com.franckyi.modcenter.client.MPBConfig;
import com.franckyi.modcenter.client.MPBConfig.EnumConfig;
import com.franckyi.modcenter.client.controller.ModBrowserController;
import com.franckyi.modcenter.client.view.region.ProjectVisual;

import javafx.concurrent.Task;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ModBrowserListTask extends Task<Pane> {

	private ModBrowserController controller;
	private SortFilter sortFilter;

	public ModBrowserListTask(ModBrowserController controller) {
		this.controller = controller;
		this.sortFilter = new SortFilter(controller.currentSortFilter, controller.currentSortOrder);
	}

	@Override
	protected Pane call() throws Exception {
		VBox vbox = new VBox();
		try {
			MPBApplication.print("Getting informations (page=" + controller.page + ")");
			controller.maxPage = ModCenterAPI.getProjectsPages(MPBConfig.getInteger(EnumConfig.projectsPerPage));
			List<Project> projectList = ModCenterAPI.getProjects(controller.page,
					MPBConfig.getInteger(EnumConfig.projectsPerPage), sortFilter);
			for (Project project : projectList) {
				ProjectVisual projectV = new ProjectVisual(project, controller.currentMcVersion);
				vbox.getChildren().add(projectV);
				vbox.getChildren().add(new Separator());
			}
			vbox.getChildren().remove(vbox.getChildren().size() - 1);
			MPBApplication.print("Informations displayed");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vbox;
	}

	@Override
	protected void succeeded() {
		controller.modBrowserScrollPane.setContent(getValue());
		controller.lock(false);
		controller.updateButtons();
	}

}

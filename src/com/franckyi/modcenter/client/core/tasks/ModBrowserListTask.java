package com.franckyi.modcenter.client.core.tasks;

import java.util.List;

import com.franckyi.modcenter.api.ModCenterAPI;
import com.franckyi.modcenter.api.Project;
import com.franckyi.modcenter.api.SortedProjectFilter;
import com.franckyi.modcenter.client.MCCConfig;
import com.franckyi.modcenter.client.MCCConfig.EnumConfig;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.controller.ModBrowserController;
import com.franckyi.modcenter.client.view.region.ProjectVisual;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ModBrowserListTask extends Task<Pane> {

	private ModBrowserController controller;
	private SortedProjectFilter filter;

	public ModBrowserListTask(ModBrowserController controller) {
		this.controller = controller;
		this.filter = new SortedProjectFilter(controller.currentSearchText, controller.currentMcVersion,
				controller.currentCategory, controller.currentSortFilter, controller.currentSortOrder);
	}

	@Override
	protected Pane call() throws Exception {
		VBox vbox = new VBox();
		try {
			controller.maxPage = ModCenterAPI.getProjectsPageNumber(MCCConfig.getInteger(EnumConfig.projectsPerPage),
					filter);
			ModCenterClient.print("Getting informations (page=" + controller.page + ";max=" + controller.maxPage + ")");
			List<Project> projectList = ModCenterAPI.getProjects(controller.page,
					MCCConfig.getInteger(EnumConfig.projectsPerPage), filter);
			for (Project project : projectList) {
				ProjectVisual projectV = new ProjectVisual(project, controller.currentMcVersion);
				vbox.getChildren().add(projectV);
				vbox.getChildren().add(new Separator());
			}
			if (vbox.getChildren().isEmpty())
				vbox.getChildren().add(new Label("No projects found."));
			else
				vbox.getChildren().remove(vbox.getChildren().size() - 1);
			ModCenterClient.print("Informations displayed");
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

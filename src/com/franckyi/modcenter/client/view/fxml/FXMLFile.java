package com.franckyi.modcenter.client.view.fxml;

import java.util.Arrays;
import java.util.List;

import com.franckyi.modcenter.api.Project;

public class FXMLFile {

	public static final FXMLFile MY_MODS = new FXMLFile("view/fxml/MyModsPane.fxml");
	public static final FXMLFile MOD_BROWSER = new FXMLFile("view/fxml/ModBrowserPane.fxml");
	public static final FXMLFile SETTINGS = new FXMLFile("view/fxml/SettingsPane.fxml");
	public static final FXMLFile MAIN = new FXMLFile("view/fxml/MainWindow.fxml");

	public static final String SELECT_FILE = "view/fxml/SelectFileTab.fxml";

	public String url;
	public Project project;

	public FXMLFile(String url) {
		this.url = url;
	}

	public FXMLFile(String url, Project project) {
		this.url = url;
		this.project = project;
	}

	public static List<FXMLFile> values() {
		return Arrays.asList(MY_MODS, MOD_BROWSER, SETTINGS, MAIN);
	}

}

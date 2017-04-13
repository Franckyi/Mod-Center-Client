package com.franckyi.mpb.view.fxml;

public enum FXMLFile {

	MOD_BROWSER("view/fxml/ModBrowserPane.fxml"), SETTINGS("view/fxml/SettingsPane.fxml"), MAIN(
			"view/fxml/MainWindow.fxml");

	public String url;

	FXMLFile(String url) {
		this.url = url;
	}

}

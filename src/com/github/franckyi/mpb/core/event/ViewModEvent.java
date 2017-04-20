package com.github.franckyi.mpb.core.event;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.franckyi.modcenter.api.CurseURLFormatter;
import com.github.franckyi.mpb.MPBApplication;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ViewModEvent implements EventHandler<ActionEvent> {

	private String projectUrl;

	public ViewModEvent(String projectUrl) {
		this.projectUrl = projectUrl;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			MPBApplication.print("Opening webpage : " + projectUrl);
			new ViewModThread(new URL(CurseURLFormatter.format(projectUrl)).toURI()).start();
		} catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private class ViewModThread extends Thread {

		private URI uri;

		private ViewModThread(URI uri) {
			this.uri = uri;
		}

		@Override
		public void run() {
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}

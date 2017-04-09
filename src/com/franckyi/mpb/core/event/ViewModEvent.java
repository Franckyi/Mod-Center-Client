package com.franckyi.mpb.core.event;

import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import com.franckyi.mpb.core.curse.CurseURLFormatter;

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
			openWebpage(new URL(CurseURLFormatter.baseUrl + "/" + projectUrl).toURI());
		} catch (URISyntaxException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void openWebpage(URI uri) {
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

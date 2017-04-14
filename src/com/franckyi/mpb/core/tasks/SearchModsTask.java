package com.franckyi.mpb.core.tasks;

import com.franckyi.mpb.MPBApplication;

import javafx.concurrent.Task;

public class SearchModsTask extends Task<Void> {
	
	private String text;
	
	public SearchModsTask(String text) {
		this.text = text;
	}

	@Override
	protected Void call() throws Exception {
		MPBApplication.print("Searching for : " + text);
		return null;
	}

}

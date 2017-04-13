package com.franckyi.mpb.core.curse;

import java.net.MalformedURLException;
import java.net.URL;

public class CurseURLFormatter {

	public static final String baseUrl = "https://mods.curse.com/mc-mods/minecraft";
	public static final String versionParameter = "filter-project-game-version";
	public static final String sortParameter = "filter-project-sort";
	public static final String pageParameter = "page";

	public static URL format(MCVersion version, SortFilter sort, int page) {
		try {
			return new URL(baseUrl + "?" + versionParameter + "=" + version.urlText + "&" + sortParameter + "="
					+ sort.value + "&" + pageParameter + "=" + page);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}

package com.franckyi.mpb.core.curse;

import java.net.MalformedURLException;
import java.net.URL;

public class CurseURLFormatter {

	public static final String curseUrl = "https://mods.curse.com/";
	public static final String browserUrl = "mc-mods/minecraft";
	public static final String versionParameter = "filter-project-game-version";
	public static final String sortParameter = "filter-project-sort";
	public static final String cursePageParameter = "page";

	public static final String curseforgeUrl = "https://minecraft.curseforge.com/";
	public static final String searchUrl = "search";
	public static final String curseforgePageParameter = "projects-page";
	public static final String searchParameter = "search";

	public static URL format(MCVersion version, SortFilter sort, int page) {
		try {
			return new URL(curseUrl + browserUrl + "?" + versionParameter + "=" + version.urlText + "&" + sortParameter
					+ "=" + sort.value + "&" + cursePageParameter + "=" + page);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static URL formatSearch(String query, int page) {
		try {
			return new URL(curseforgeUrl + searchUrl + "?" + curseforgePageParameter + "=" + page + "&"
					+ searchParameter + "=" + query);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}

}

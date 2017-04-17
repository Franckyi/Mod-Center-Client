package com.franckyi.mpb.core.tasks;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.franckyi.mpb.MPBApplication;
import com.franckyi.mpb.controller.ModBrowserController;
import com.franckyi.mpb.core.curse.CurseURLFormatter;
import com.franckyi.mpb.core.curse.SearchedModLogical;
import com.franckyi.mpb.core.data.cache.ModBrowserCache;
import com.franckyi.mpb.core.data.cache.ModBrowserCacheKey;
import com.franckyi.mpb.view.region.SearchedModVisual;

import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SearchModsTask extends Task<Pane> {

	private URL url;
	private ModBrowserController controller;
	private ModBrowserCache cache = MPBApplication.INSTANCE.cache;

	public SearchModsTask(String query, int page, ModBrowserController controller) {
		this.url = CurseURLFormatter.formatSearch(query, page);
		this.controller = controller;
	}

	@Override
	protected Pane call() throws Exception {
		try {
			List<Object> visualModList = null;
			VBox vbox = new VBox();
			ModBrowserCacheKey cacheKey = new ModBrowserCacheKey(controller.page, controller.currentMcVersion,
					controller.currentSortFilter, controller.currentSearchText);
			if (cache.containsKey(cacheKey)) {
				MPBApplication.print("Reloading data from cache");
				visualModList = cache.get(cacheKey);
				for (Object modV : visualModList) {
					vbox.getChildren().add((SearchedModVisual) modV);
				}
			} else {
				MPBApplication.print("Collecting data at " + url.toString());
				List<SearchedModLogical> modList = new ArrayList<SearchedModLogical>();
				Document doc = Jsoup.connect(url.toString()).get();
				Element table = doc.select(".listing-project").get(0);
				for (Element tr : table.select(".results")) {
					modList.add(new SearchedModLogical(
							tr.select(".results-name").get(0).select(".results-highlight").get(0).html(),
							tr.select(".results-summary").get(0).html(),
							tr.select(".results-image").get(0).select("img").get(0).attr("src"),
							tr.select(".results-date").get(0).select("abbr").get(0).html(),
							tr.select(".results-owner").get(0).select("a").get(0).html(),
							tr.select(".results-name").get(0).select("a").get(0).attr("href")));
				}
				try {
					String maxPage = doc.select(".scrolled-content").get(0).select("a").get(0).html();
					controller.maxPage = Integer.parseInt(maxPage.substring(10, 12)) / 25 + 1;
				} catch (Exception e) {
					e.printStackTrace();
				}
				MPBApplication.print("Data collected. Rendering...");
				try {
					visualModList = new ArrayList<Object>();
					for (SearchedModLogical mod : modList) {
						SearchedModVisual modV = new SearchedModVisual(mod);
						vbox.getChildren().add(modV);
						visualModList.add(modV);
					}
					MPBApplication.print("Rendering complete. Adding page to cache");
					cache.put(cacheKey, visualModList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return vbox;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

}

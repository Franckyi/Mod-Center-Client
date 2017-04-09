package com.franckyi.mpb.core.tasks;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.franckyi.mpb.MPBApplication;
import com.franckyi.mpb.controller.ModBrowserController;
import com.franckyi.mpb.core.curse.CurseURLFormatter;
import com.franckyi.mpb.core.curse.MCVersion;
import com.franckyi.mpb.core.curse.ModLogical;
import com.franckyi.mpb.core.curse.SortFilter;
import com.franckyi.mpb.core.data.cache.ModBrowserCache;
import com.franckyi.mpb.core.data.cache.ModBrowserCacheKey;
import com.franckyi.mpb.view.region.ModVisual;

import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ModBrowserListTask extends Task<Pane> {

	private URL url;
	private ModBrowserController controller;
	private ModBrowserCache cache = MPBApplication.INSTANCE.cache;

	public ModBrowserListTask(MCVersion version, SortFilter sort, int page, ModBrowserController modBrowserController) {
		this.url = CurseURLFormatter.format(version, sort, page);
		this.controller = modBrowserController;
	}

	@Override
	protected Pane call() throws Exception {
		List<ModVisual> visualModList = null;
		VBox vbox = new VBox();
		ModBrowserCacheKey cacheKey = new ModBrowserCacheKey(controller.page, controller.currentMcVersion,
				controller.currentSortFilter);
		if (cache.containsKey(cacheKey)) {
			MPBApplication.print("Reloading data from cache");
			visualModList = cache.get(cacheKey);
			for (ModVisual modV : visualModList) {
				vbox.getChildren().add(modV);
			}
		} else {
			MPBApplication.print("Collecting data at " + url.toString());
			List<ModLogical> modList = new ArrayList<ModLogical>();
			Document doc = Jsoup.connect(url.toString()).get();
			Element list = doc.select(".listing-project").get(1);
			for (Element el : list.select(".group")) {
				modList.add(new ModLogical(el.select(".screenshot").get(0).select("img").attr("src"),
						el.select(".title").get(0).select("a").get(0).html(),
						el.select(".title").get(0).select("a").get(0).attr("href").split("/")[3],
						el.select(".average-downloads").get(0).html(), el.select(".download-total").get(0).html(),
						el.select(".updated").get(0).html(), el.select(".updated").get(1).html()));
			}
			try {
				Elements els = doc.getElementById("addons-browse").select(".b-pagination-item");
				if (els == null || els.size() == 0)
					controller.maxPage = 1;
				else
					controller.maxPage = Integer.parseInt(els
							.get(els.size() - 2).select("a, span")
							.get(0).html());
			} catch (Exception e) {
				e.printStackTrace();
			}
			MPBApplication.print("Data collected. Rendering...");
			visualModList = new ArrayList<ModVisual>();
			for (ModLogical mod : modList) {
				ModVisual modV = new ModVisual(mod, controller.currentMcVersion);
				vbox.getChildren().add(modV);
				visualModList.add(modV);
			}
			MPBApplication.print("Rendering complete. Adding page to cache");
			cache.put(cacheKey, visualModList);
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

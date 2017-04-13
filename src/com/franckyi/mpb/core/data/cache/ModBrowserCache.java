package com.franckyi.mpb.core.data.cache;

import java.util.HashMap;
import java.util.List;

import com.franckyi.mpb.view.region.ModVisual;

public class ModBrowserCache extends HashMap<ModBrowserCacheKey, List<ModVisual>> {

	private static final long serialVersionUID = -6629115848934460874L;

	@Override
	public List<ModVisual> get(Object key) {
		for (ModBrowserCacheKey key2 : keySet())
			if (key2.equals(key)) {
				return super.get(key2);
			}
		return null;
	}

	@Override
	public boolean containsKey(Object key) {
		for (ModBrowserCacheKey key2 : keySet())
			if (key2.equals(key))
				return true;
		return false;
	}

}

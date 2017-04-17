package com.franckyi.mpb.core.data.cache;

import java.util.HashMap;
import java.util.List;

public class ModBrowserCache extends HashMap<ModBrowserCacheKey, List<Object>> {

	private static final long serialVersionUID = -6629115848934460874L;

	@Override
	public List<Object> get(Object key) {
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

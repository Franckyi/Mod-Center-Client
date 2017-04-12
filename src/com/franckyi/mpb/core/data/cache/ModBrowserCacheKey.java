package com.franckyi.mpb.core.data.cache;

import com.franckyi.mpb.core.curse.MCVersion;
import com.franckyi.mpb.core.curse.SortFilter;

public class ModBrowserCacheKey {
	
	private int page;
	private MCVersion mcVersion;
	private SortFilter sortFilter;
	
	public ModBrowserCacheKey(int page, MCVersion mcVersion, SortFilter sortFilter) {
		this.page = page;
		this.mcVersion = mcVersion;
		this.sortFilter = sortFilter;
	}

	public int getPage() {
		return page;
	}

	public MCVersion getMcVersion() {
		return mcVersion;
	}

	public SortFilter getSortFilter() {
		return sortFilter;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModBrowserCacheKey other = (ModBrowserCacheKey) obj;
		if (mcVersion != other.mcVersion)
			return false;
		if (page != other.page)
			return false;
		if (sortFilter != other.sortFilter)
			return false;
		return true;
	}
	
	

}

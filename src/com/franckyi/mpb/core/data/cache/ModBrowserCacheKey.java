package com.franckyi.mpb.core.data.cache;

import com.franckyi.mpb.core.curse.MCVersion;
import com.franckyi.mpb.core.curse.SortFilter;

public class ModBrowserCacheKey {

	private int page;
	private MCVersion mcVersion;
	private SortFilter sortFilter;
	private String search;

	public ModBrowserCacheKey(int page, MCVersion mcVersion, SortFilter sortFilter, String search) {
		this.page = page;
		this.mcVersion = mcVersion;
		this.sortFilter = sortFilter;
		this.search = search;
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
	
	public String getSearchFilter() {
		return search;
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
		if (search == other.search)
			return true;
		if (mcVersion != other.mcVersion)
			return false;
		if (page != other.page)
			return false;
		if (sortFilter != other.sortFilter)
			return false;
		return true;
	}

}

package com.franckyi.mpb.core.json;

import com.google.gson.annotations.SerializedName;

public class JsonConfig {
	
	@SerializedName("displayModsThumbnail")
	public boolean displayModsThumbnail;

	public JsonConfig setDisplayModsThumbnail(boolean displayModsThumbnail) {
		this.displayModsThumbnail = displayModsThumbnail;
		return this;
	}
	

}

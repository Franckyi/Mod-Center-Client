package com.franckyi.mpb.core.data.cache;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.franckyi.mpb.MPBApplication;
import com.franckyi.mpb.core.data.DataFiles;
import com.franckyi.mpb.core.json.JsonModFile;
import com.franckyi.mpb.core.json.JsonModcache;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class ModDownloadCache {

	public static void addFileToCache(String title, JsonModFile mod) throws JsonSyntaxException, JsonIOException, IOException {
		Gson gson = new Gson();
		File fileg = DataFiles.MOD_CACHE_FILE;
		JsonModcache modsg;
		if(fileg.createNewFile())
			modsg = new JsonModcache();
		else 
			modsg = gson.fromJson(new FileReader(fileg), JsonModcache.class);
		if(!modsg.containsKey(mod.version))
			modsg.put(mod.version, new HashMap<>());
		modsg.get(mod.version).put(title, mod);
		FileWriter fwg = new FileWriter(fileg);
		gson.toJson(modsg, fwg);
		fwg.close();
		MPBApplication.print("Added mod to cache");
	}

}

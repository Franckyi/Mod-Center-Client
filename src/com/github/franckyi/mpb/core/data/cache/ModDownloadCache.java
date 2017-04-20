package com.github.franckyi.mpb.core.data.cache;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.franckyi.modcenter.api.ProjectFile;
import com.github.franckyi.mpb.MPBApplication;
import com.github.franckyi.mpb.core.data.DataFiles;
import com.github.franckyi.mpb.core.json.JsonModcache;
import com.google.gson.Gson;

public class ModDownloadCache {

	public static void addFileToCache(ProjectFile file) throws IOException {
		Gson gson = new Gson();
		File fileg = DataFiles.MOD_CACHE_FILE;
		JsonModcache modsg;
		if (fileg.createNewFile())
			modsg = new JsonModcache();
		else
			modsg = gson.fromJson(new FileReader(fileg), JsonModcache.class);
		if (!modsg.containsKey(file.getVersion()))
			modsg.put(file.getVersion(), new HashMap<>());
		modsg.get(file.getVersion()).put(file.getProjectId(), file);
		FileWriter fwg = new FileWriter(fileg);
		gson.toJson(modsg, fwg);
		fwg.close();
		MPBApplication.print("Added mod to cache");
	}

}

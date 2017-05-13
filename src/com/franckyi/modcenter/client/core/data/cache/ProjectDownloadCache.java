package com.franckyi.modcenter.client.core.data.cache;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.franckyi.modcenter.api.beans.Project;
import com.franckyi.modcenter.api.beans.ProjectFile;
import com.franckyi.modcenter.client.ModCenterClient;
import com.franckyi.modcenter.client.core.data.DataFiles;
import com.franckyi.modcenter.client.core.json.JsonModcache;
import com.franckyi.modcenter.client.core.json.JsonProject;
import com.franckyi.modcenter.client.core.json.JsonProjectFile;
import com.franckyi.modcenter.client.core.json.MCCJson;
import com.google.gson.Gson;

public class ProjectDownloadCache {

	public static void addFileToCache(ProjectFile file, Project project) throws IOException {
		Gson gson = MCCJson.getGson();
		File fileg = DataFiles.MOD_CACHE_FILE;
		JsonModcache modsg;
		if (fileg.createNewFile())
			modsg = new JsonModcache();
		else
			modsg = gson.fromJson(new FileReader(fileg), JsonModcache.class);
		if (!modsg.containsKey(file.getProjectId()))
			modsg.put(file.getProjectId(), new JsonProject(project));
		else
			modsg.get(file.getProjectId()).update(project);
		modsg.get(file.getProjectId()).getFiles().put(file.getVersion(), new JsonProjectFile(file));
		FileWriter fwg = new FileWriter(fileg);
		gson.toJson(modsg, fwg);
		fwg.close();
		ModCenterClient.print("Added file to cache");
	}

}

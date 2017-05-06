package com.franckyi.modcenter.client.core.json;

import com.franckyi.modcenter.api.ProjectFile;

@SuppressWarnings("unused")
public class JsonProjectFile {

	private int id;
	private String name;
	private String type;
	private String size;
	private String uploaded;
	private String url;

	public JsonProjectFile(ProjectFile file) {
		id = file.getFileId();
		name = file.getFileName();
		type = file.getType().getDbKey();
		size = file.getSize();
		uploaded = file.getUploaded().toLocalDate().toString();
		url = file.getFileUrl();
	}

}

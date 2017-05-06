package com.franckyi.modcenter.client.core.json;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.franckyi.modcenter.api.EnumCategory;
import com.franckyi.modcenter.api.Project;

public class JsonProject {

	private transient int id;
	private String name;
	private String author;
	private int downloads;
	private Date updated;
	private String description;
	private String url;
	private String thumbnail;
	private List<EnumCategory> categories;
	private Map<String, JsonProjectFile> files = new HashMap<>();

	public JsonProject(Project project) {
		update(project);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAuthor() {
		return author;
	}

	public int getDownloads() {
		return downloads;
	}

	public Date getUpdated() {
		return updated;
	}

	public String getDescription() {
		return description;
	}

	public String getUrl() {
		return url;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public List<EnumCategory> getCategories() {
		return categories;
	}

	public Map<String, JsonProjectFile> getFiles() {
		return files;
	}

	public void update(Project project) {
		id = project.getProjectId();
		name = project.getName();
		author = project.getAuthor();
		downloads = project.getTotalDl();
		updated = project.getUpdated();
		description = project.getDescription();
		url = project.getProjectUrl();
		thumbnail = project.getThumbnail();
		categories = project.getCategories();
	}

}

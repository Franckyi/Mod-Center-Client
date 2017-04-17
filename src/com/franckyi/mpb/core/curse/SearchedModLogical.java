package com.franckyi.mpb.core.curse;

public class SearchedModLogical {

	private String name;
	private String description;
	private String image;
	private String updated;
	private String author;
	private String projectUrl;

	public SearchedModLogical(String name, String description, String image, String updated, String author,
			String projectUrl) {
		this.name = name;
		this.description = description;
		this.image = image;
		this.updated = updated;
		this.author = author;
		this.projectUrl = projectUrl;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getImage() {
		return image;
	}

	public String getUpdated() {
		return updated;
	}

	public String getAuthor() {
		return author;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

}

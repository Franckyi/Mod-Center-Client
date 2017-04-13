package com.franckyi.mpb.core.curse;

public class ModLogical {

	private String image;
	private String name;
	private String monthlyDl;
	private String totalDl;
	private String updated;
	private String created;
	private String projectUrl;

	public ModLogical(String image, String name, String projectUrl, String monthlyDl, String totalDl, String updated,
			String created) {
		this.image = image;
		this.name = name;
		this.projectUrl = projectUrl;
		this.monthlyDl = monthlyDl;
		this.totalDl = totalDl;
		this.updated = updated;
		this.created = created;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public String getMonthlyDl() {
		return monthlyDl;
	}

	public String getTotalDl() {
		return totalDl;
	}

	public String getUpdated() {
		return updated;
	}

	public String getCreated() {
		return created;
	}

	public String getProjectUrl() {
		return projectUrl;
	}

}

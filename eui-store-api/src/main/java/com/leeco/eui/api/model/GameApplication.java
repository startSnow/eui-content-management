package com.leeco.eui.api.model;

public class GameApplication {
	private Long id;
	
	private String packageName;
	
	private String title;
	
	private String iconUrl;
	
	public GameApplication() {}
	
	public GameApplication(Long id, String packageName, String title, String iconUrl) {
		this.id = id;
		this.packageName = packageName;
		this.title = title;
		this.iconUrl = iconUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
}

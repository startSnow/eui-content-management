package com.leeco.eui.api.model;

public class UserProfile {
	private String userName;
	private String locale;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	@Override
	public String toString() {
		return "UserProfile [userName=" + userName + ", locale=" + locale + "]";
	}

}

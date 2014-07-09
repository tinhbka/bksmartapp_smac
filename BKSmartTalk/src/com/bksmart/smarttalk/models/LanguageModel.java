package com.bksmart.smarttalk.models;

public class LanguageModel {
	private String Lang;
	private boolean selected;
	public String getLang() {
		return Lang;
	}
	public void setLang(String lang) {
		Lang = lang;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public LanguageModel(String lang, boolean selected) {
		super();
		Lang = lang;
		this.selected = selected;
	}
	
}

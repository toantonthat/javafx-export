package com.export.model;

public class FileModel {
	private long id;
	private String ma;
	private String name;
	private String url;
	private String type;
	private String extension;
	private String description;

	public FileModel(long id, String ma, String name, String url, String type, String description) {
		super();
		this.id = id;
		this.ma = ma;
		this.name = name;
		this.url = url;
		this.type = type;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

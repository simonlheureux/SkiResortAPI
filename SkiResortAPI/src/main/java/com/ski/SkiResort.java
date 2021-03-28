package com.ski;

public class SkiResort {

	private long idResort = -1;
	private String name;
	private String description;

	public SkiResort(long id, String name, String description) {
		this.idResort = id;
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return idResort;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public void setId(long id) {
		this.idResort = id;
	}
}

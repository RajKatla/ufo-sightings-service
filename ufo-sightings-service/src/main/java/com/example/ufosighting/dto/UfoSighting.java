package com.example.ufosighting.dto;

public class UfoSighting {
	private String dateSeen;
	private String dateReported;
	private String placeSeen;
	private String shape;
	private String duration;
	private String description;
	
	public String getDateSeen() {
		return dateSeen;
	}

	public String getDateReported() {
		return dateReported;
	}

	public String getPlaceSeen() {
		return placeSeen;
	}

	public String getShape() {
		return shape;
	}

	public String getDuration() {
		return duration;
	}

	public String getDescription() {
		return description;
	}

	// We have to change it to builder which will construct UfoSighting with sensible method names like withDateSeen(), withDateReported().. so on
	// and this will make sure that the caller is passing right values to the right properties.
	
	public UfoSighting(String dateSeen, String dateReported, String placeSeen, String shape, String duration, String description) {
		this.dateSeen = dateSeen;
		this.dateReported = dateReported;
		this.placeSeen = placeSeen;
		this.shape = shape;
		this.duration = duration;
		this.description = description;
	}
}

package com.travlytics.model;

public class PlacePercentage {
	private String placeName;
	private Double perentage;
	/**
	 * @param placeName
	 * @param perentage
	 */
	public PlacePercentage(String placeName, Double perentage) {
		this.placeName = placeName;
		this.perentage = perentage;
	}
	/**
	 * @return the placeName
	 */
	public String getPlaceName() {
		return placeName;
	}
	/**
	 * @param placeName the placeName to set
	 */
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	/**
	 * @return the perentage
	 */
	public Double getPerentage() {
		return perentage;
	}
	/**
	 * @param perentage the perentage to set
	 */
	public void setPerentage(Double perentage) {
		this.perentage = perentage;
	}
}

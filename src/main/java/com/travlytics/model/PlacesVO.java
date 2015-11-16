package com.travlytics.model;

/**
 * @author kunal
 *
 */
public class PlacesVO {
	private String continent;
	private String country;
	private String state;
	private String code;
	private boolean isAutonomous;
	/**
	 * @return the continent
	 */
	public String getContinent() {
		return continent;
	}
	/**
	 * @param continent the continent to set
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the isAutonomous
	 */
	public boolean isAutonomous() {
		return isAutonomous;
	}
	/**
	 * @param isAutonomous the isAutonomous to set
	 */
	public void setAutonomous(boolean isAutonomous) {
		this.isAutonomous = isAutonomous;
	}
	/**
	 * @param continent
	 * @param country
	 * @param state
	 * @param code
	 * @param isAutonomous
	 */
	public PlacesVO(String continent, String country, String state, String code, boolean isAutonomous) {
		this.continent = continent;
		this.country = country;
		this.state = state;
		this.code = code;
		this.isAutonomous = isAutonomous;
	}
	
}

package com.travlytics.service;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.travlytics.dao.PlacesDAO;
import com.travlytics.model.PlacePercentage;
import com.travlytics.model.PlacesVO;

/**
 * @author kghogale
 *
 */
public class PlacesService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6519239476989624116L;
	private PlacesDAO dao;
	
	public void setPlacesDAO(PlacesDAO dao) {
		this.dao = dao;
	}
    
	/**
	 * @param username
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public List<PlacesVO> getPlacesByType(String username, String type) throws SQLException {
		return dao.getPlacesByType(username, type);
	}
	
	/**
	 * @param username
	 * @param place
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public Integer savePlaceForUserEvent(String username, String place, String type) throws SQLException {
		return dao.savePlaceForUserEvent(username, place, type);
	}
	
	/**
	 * @param username
	 * @param measure
	 * @param type
	 * @return
	 * @throws SQLException
	 */
	public List<PlacePercentage> getPercentageOfCountryByAreaPopulation(String username, String measure, String type) throws SQLException {
		return dao.getPercentageOfCountryByAreaPopulation(username, measure, type);
	}
}
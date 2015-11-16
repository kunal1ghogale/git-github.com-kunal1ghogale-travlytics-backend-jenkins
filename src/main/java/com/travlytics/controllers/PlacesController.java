package com.travlytics.controllers;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travlytics.model.PlacePercentage;
import com.travlytics.model.PlacesVO;
import com.travlytics.service.PlacesService;
import com.travlytics.utils.ErrorSuccessConstants;



/**
 * @author kghogale
 *
 */
@Controller
@RequestMapping("/places")
public class PlacesController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6691269822766872649L;
	
	private PlacesService service;
	
	public void setPlacesService(PlacesService service) {
		this.service = service;
	}

	/**
	 * @param username
	 * @param type
	 * @param event
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping(value = "/getplaces", method = RequestMethod.POST)
	@ResponseBody
	public List<PlacesVO> getPlacesByTypeForUser(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "event", required = false, defaultValue = "travel") String event) throws SQLException {
		if (type == null || type.equalsIgnoreCase("")) {
			return new ArrayList<PlacesVO>();
		}
		if (event.equalsIgnoreCase("travel")) {
			return service.getPlacesByType(username, type);
		}
		return null;
	}

	@RequestMapping(value = "/saveplaces", method = RequestMethod.POST)
	@ResponseBody
	public Integer setPlacesByTypeForUser(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "event", required = false, defaultValue = "travel") String event,
			@RequestParam(value = "place", required = true) String place) throws SQLException {
		if (event.equalsIgnoreCase("travel")) {
			return service.savePlaceForUserEvent(username, place, type);
		}
		return ErrorSuccessConstants.INVALID_EVENT;
	}
	
	@RequestMapping(value = "/getpercentageforuser", method = RequestMethod.POST)
	@ResponseBody
	public List<PlacePercentage> getPercentageOfCountryByAreaPopulation(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "measure", required = false) String measure) throws SQLException {
		return service.getPercentageOfCountryByAreaPopulation(username, measure, type);
	}

}

package com.travlytics.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travlytics.model.PlacePercentage;
import com.travlytics.model.PlacesVO;
import com.travlytics.utils.ErrorSuccessConstants;

/**
 * @author kghogale
 * 
 */
public class PlacesDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8057518985140592749L;

	private DataSource dataSource;
	
	private static final Logger LOG = LoggerFactory.getLogger(PlacesDAO.class);
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @param username
	 * @return places user has been to else list of places of specified type
	 * @throws SQLException 
	 */
	public List<PlacesVO> getPlacesByType(String username, String type) throws SQLException {
		StringBuilder sql = new StringBuilder();
		if (username == null || username.equalsIgnoreCase("")) {
			if (type.equalsIgnoreCase("continent")) {
				sql.append("Select name as continentname from travlytics.public.continents");
			} else if (type.equalsIgnoreCase("country")) {
				sql.append("Select country.name as countryname, continent.name as continentname, country.code as code, country.autonomous as autonomous from ");
				sql.append(" travlytics.public.countries country join travlytics.public.continents continent on country.continentid = continent.id");
			} else if (type.equalsIgnoreCase("state")) {
				sql.append("Select state.name as statename, country.name as countryname, continent.name as continentname from ");
				sql.append(" travlytics.public.states state join travlytics.public.countries country on state.countryid = country.id ");
				sql.append(" join travlytics.public.continents continent on country.continentid = continent.id");
			}
		} else {
			if (type.equalsIgnoreCase("continent")) {
				sql.append("Select continent.name as continentname from travlytics.public.travelevents te join travlytics.public.accounts ac ");
				sql.append(" on te.accountid = ac.id join travlytics.public.continents continent on te.placeid = continent.id ");
				sql.append(" where ac.email = ? and te.type = 'continent'");
			} else if (type.equalsIgnoreCase("country")) {
				sql.append("Select country.name as countryname, continent.name as continentname, country.code as code, country.autonomous as autonomous from travlytics.public.travelevents te join travlytics.public.accounts ac ");
				sql.append(" on te.accountid = ac.id join travlytics.public.countries country on te.placeid = country.id ");
				sql.append(" join travlytics.public.continents continent on country.continentid = continent.id ");
				sql.append(" where ac.email = ? and te.type = 'country'");
			} else if (type.equalsIgnoreCase("state")) {
				sql.append("Select state.name as statename, country.name as countryname, continent.name as continentname"
						+ " from travlytics.public.travelevents te join travlytics.public.accounts ac ");
				sql.append(" on te.accountid = ac.id join travlytics.public.states state on te.placeid = state.id ");
				sql.append(" join travlytics.public.countries country on state.countryid = country.id ");
				sql.append(" join travlytics.public.continents continent on country.continentid = continent.id ");
				sql.append(" where ac.email = ? and te.type = 'state'");
			}
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		List<PlacesVO> list = new ArrayList<PlacesVO>();
		try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(sql.toString());
            if (username != null && !username.equalsIgnoreCase("")) {
            	preparedStatement.setString(1, username);
            }
            ResultSet rs = preparedStatement.executeQuery();
    		while(rs.next()) {
    			String continent = "";
    			String country = "";
    			String state = "";
    			String code = "";
    			boolean isAutonomous = true;
    			if (type.equalsIgnoreCase("continent")) {
    				continent = rs.getString("continentname");
    			} else if (type.equalsIgnoreCase("country")) {
    				continent = rs.getString("continentname");
    				country = rs.getString("countryname");
    				code = rs.getString("code");
    				isAutonomous = rs.getBoolean("autonomous");
    			} else if (type.equalsIgnoreCase("state")) {
    				continent = rs.getString("continentname");
    				country = rs.getString("countryname");
    				state = rs.getString("statename");
    			}
    			PlacesVO place = new PlacesVO(continent, country, state, code, isAutonomous);
    			list.add(place);
    		}
    		return list;
        } catch (Exception e) {
        	e.printStackTrace();
            LOG.info("Error occured while getting data:" + e.getMessage());
    		return list;
        } finally {
            if (preparedStatement != null) {
            	preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
	}
	
	/**
	 * @param username
	 * @param place
	 * @param type
	 * @return
	 * @throws SQLException 
	 */
	public Integer savePlaceForUserEvent(String username, String place, String type) throws SQLException {
		String tableName = "";
		if (type.equalsIgnoreCase("continent")) {
			tableName = "continents";
		} else if (type.equalsIgnoreCase("country")) {
			tableName = "countries";
		} else if (type.equalsIgnoreCase("state")) {
			tableName = "states";
		}
		Integer accountid = null;
		Integer placeid = null;
		String sql = "INSERT into travlytics.public.travelevents (accountid, placeid, type) values (?, ?, ?)";
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("Select id from travlytics.public.accounts where email = ? and active = true");
           	preparedStatement.setString(1, StringEscapeUtils.escapeJava(username));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	accountid = rs.getInt("id");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            LOG.info("Error occured while getting data:" + e.getMessage());
    		return ErrorSuccessConstants.DB_ERROR;
        } finally {
            if (preparedStatement != null) {
            	preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
		try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement("Select id from travlytics.public." + tableName + " where name = ?");
           	preparedStatement.setString(1, StringEscapeUtils.escapeJava(place));
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	placeid = rs.getInt("id");
            }
        } catch (Exception e) {
        	e.printStackTrace();
            LOG.info("Error occured while getting data:" + e.getMessage());
    		return ErrorSuccessConstants.DB_ERROR;
        } finally {
            if (preparedStatement != null) {
            	preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
		if (accountid != null && placeid != null) {
			try {
	            conn = dataSource.getConnection();
	            preparedStatement = conn.prepareStatement(sql.toString());
	           	preparedStatement.setInt(1, accountid);
	           	preparedStatement.setInt(2, placeid);
	           	preparedStatement.setString(3, type);
	            return preparedStatement.executeUpdate();
	        } catch (Exception e) {
	        	e.printStackTrace();
	            LOG.info("Error occured while getting data:" + e.getMessage());
	    		return ErrorSuccessConstants.DB_ERROR;
	        } finally {
	            if (preparedStatement != null) {
	            	preparedStatement.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        }
		} else {
			LOG.info("Account or place is invalid");
			return ErrorSuccessConstants.INVALID_EVENT;
		}
	}
	
	/**
	 * @param username
	 * @return useraccount object from username
	 * @throws SQLException 
	 */
	public List<PlacePercentage> getPercentageOfCountryByAreaPopulation(String username, String measure, String type) throws SQLException {
		String sql = "";
		if (type.equalsIgnoreCase("country")) {
			sql = "Select (Select sum(" + measure + ") from travlytics.public.travelevents te join travlytics.public.accounts a ON te.accountid = a.id"
					+ " join travlytics.public.countries c on te.placeid = c.id where a.email = ? and te.type = 'country') /"
					+ "(Select sum(" + measure + ") from travlytics.public.countries) * 100.0 as percentage, 'world' as place";
		} else if (type.equalsIgnoreCase("state")) {
			sql = "Select sum(s." + measure + ")/c." + measure + " * 100.0 as percentage, c.name as place from travlytics.public.travelevents te join accounts a ON te.accountid = a.id "
					+ " join states s on te.placeid = s.id join countries c on s.countryid = c.id "
					+ "where a.email = ? and te.type = 'state' group by c.name, s.countryid, c." + measure + ";";	
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		List<PlacePercentage> list = new ArrayList<PlacePercentage>();
        try {
			conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setString(1, username);
    		ResultSet rs = preparedStatement.executeQuery();
    		while(rs.next()) {
    			list.add(new PlacePercentage(rs.getString("place"), rs.getDouble("percentage")));
    		}
    		return list;
        } catch (Exception e) {
            LOG.info("Error occured while getting data:" + e.getMessage());
    		return list;
        } finally {
            if (preparedStatement != null) {
            	preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
	}
}

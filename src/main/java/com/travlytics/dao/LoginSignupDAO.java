package com.travlytics.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travlytics.model.UserAccount;
import com.travlytics.utils.Constants;
import com.travlytics.utils.ErrorSuccessConstants;

/**
 * @author kghogale
 * 
 */
public class LoginSignupDAO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8057518985140592749L;

	private DataSource dataSource;
	
	private static final Logger LOG = LoggerFactory.getLogger(LoginSignupDAO.class);
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	/**
	 * @param user
	 * @return if signup was successful
	 * @throws SQLException
	 */
	public Integer storeSignedupUser(UserAccount user) throws SQLException {
		String sql = "INSERT INTO travlytics.public.accounts (name, email, password, facebookToken, role, active, updatedby) VALUES (?, ?, ?, ?, ?, ?, ?)";
		UserAccount temp = getUserAccountByUsername(user.getUsername());
		if (temp != null) {
			return ErrorSuccessConstants.USER_ALREADY_EXISTS;
		}
		Connection conn = null;
		PreparedStatement preparedStatement = null;
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setString(1, user.getName());
    		preparedStatement.setString(2, user.getUsername());
    		preparedStatement.setString(3, user.getPassword());
    		preparedStatement.setString(4, user.getFacebookToken());
    		preparedStatement.setString(5, Constants.DEFAULT_ROLE);
    		preparedStatement.setBoolean(6, Constants.ACTIVE);
    		preparedStatement.setString(7, Constants.DEFAULT_USER);
    		Integer val = preparedStatement.executeUpdate();
    		if (val == 1) {
    			return ErrorSuccessConstants.SIGNUP_SUCCESS;
    		} else {
    			return ErrorSuccessConstants.DB_ERROR;
    		}
        } catch (Exception e) {
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
		
	}
	
	/**
	 * @param username
	 * @return useraccount object from username
	 * @throws SQLException 
	 */
	public UserAccount getUserAccountByUsername(String username) throws SQLException {
		String sql = "Select name, email, password, facebookToken from travlytics.public.accounts where email = ? and active = true";
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		UserAccount user = null;
		try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(sql);
    		preparedStatement.setString(1, username);
    		ResultSet rs = preparedStatement.executeQuery();
    		while(rs.next()) {
    			user = new UserAccount(rs.getString("email") ,
    					rs.getString("password"), rs.getString("name"), rs.getString("facebookToken"));
    		}
    		return user;
        } catch (Exception e) {
            LOG.info("Error occured while getting data:" + e.getMessage());
    		return user;
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

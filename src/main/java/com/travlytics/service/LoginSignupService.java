package com.travlytics.service;

import java.io.Serializable;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.travlytics.dao.LoginSignupDAO;
import com.travlytics.model.UserAccount;
import com.travlytics.utils.ErrorSuccessConstants;

/**
 * @author kghogale
 *
 */
public class LoginSignupService implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6519239476989624116L;
	private LoginSignupDAO dao;
	
	public void setLoginSignupDAO(LoginSignupDAO dao) {
		this.dao = dao;
	}
    
	/**
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public Integer signUpUser(UserAccount user) throws SQLException {
		if (!(user.getPassword() == null) && !user.getPassword().equalsIgnoreCase("")) {
			String password = user.getPassword();
			password = BCrypt.hashpw(password, BCrypt.gensalt());
			user.setPassword(password);
		}
		if (!(user.getFacebookToken() == null) && !user.getFacebookToken().equalsIgnoreCase("")) {
			String facebookToken = user.getFacebookToken();
			facebookToken = BCrypt.hashpw(facebookToken, BCrypt.gensalt());
			user.setFacebookToken(facebookToken);
		}
		return dao.storeSignedupUser(user);
	}
	
	public Integer loginUserFromEmail(String username, String password, String facebookToken) throws SQLException {
		UserAccount user = dao.getUserAccountByUsername(username);
		if (user == null) {
			return ErrorSuccessConstants.USER_DOES_NOT_EXIST;
		}
		boolean validLogin = false;
		if (password != null && !password.equalsIgnoreCase("")) {
			validLogin = BCrypt.checkpw(password, user.getPassword());
		}
		if (facebookToken != null && !facebookToken.equalsIgnoreCase("")) {
			validLogin = BCrypt.checkpw(facebookToken, user.getFacebookToken());
		}
		if (validLogin) {
			return ErrorSuccessConstants.USER_LOGGED_IN;
		} else {
			return ErrorSuccessConstants.INVALID_LOGIN_CREDENTIALS;
		}
	}
}
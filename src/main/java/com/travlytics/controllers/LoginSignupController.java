package com.travlytics.controllers;

import java.io.Serializable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travlytics.exception.TravlyticsException;
import com.travlytics.model.UserAccount;
import com.travlytics.service.LoginSignupService;
import com.travlytics.utils.ErrorSuccessConstants;



/**
 * @author kghogale
 *
 */
@Controller
@RequestMapping("/accounts")
public class LoginSignupController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6691269822766872649L;
	
	private LoginSignupService service;
	
	public void setLoginSignupService(LoginSignupService service) {
		this.service = service;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@ResponseBody
	public Integer signUpUser(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "facebookToken", required = false) String facebookToken) {
		try {
			if (username == null || username.equalsIgnoreCase("")) {
				return ErrorSuccessConstants.INVALID_USERNAME;
			}
			if ((password == null || password.equalsIgnoreCase(""))
					&& (facebookToken == null || facebookToken.equalsIgnoreCase(""))) {
				if (password == null || password.equalsIgnoreCase("")) {
					return ErrorSuccessConstants.INVALID_LOGIN_CREDENTIALS;
				} else {
					return ErrorSuccessConstants.INVALID_FACEBOOK_TOKEN;
				}
			}
			if (name == null) {
				name = "";
			}
			return service.signUpUser(new UserAccount(username, password, name, facebookToken));
		} catch (Exception e) {
			e.printStackTrace();
			throw new TravlyticsException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Integer signUpUser(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "facebookToken", required = false) String facebookToken) {
		try {
			if (username == null || username.equalsIgnoreCase("")) {
				return ErrorSuccessConstants.INVALID_USERNAME;
			}
			if ((password == null || password.equalsIgnoreCase("")) &&
					(facebookToken == null || facebookToken.equalsIgnoreCase(""))) {
				if (password == null || password.equalsIgnoreCase("")) {
					return ErrorSuccessConstants.INVALID_LOGIN_CREDENTIALS;
				} else {
					return ErrorSuccessConstants.INVALID_FACEBOOK_TOKEN;
				}
			}
			return service.loginUserFromEmail(username, password, facebookToken);
		} catch (Exception e) {
			e.printStackTrace();
			throw new TravlyticsException(e.getMessage());
		}
	}

}

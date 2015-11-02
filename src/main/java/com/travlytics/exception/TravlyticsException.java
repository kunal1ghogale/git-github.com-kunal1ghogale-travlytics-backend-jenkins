package com.travlytics.exception;

/**
 * @author kghogale
 *
 */
public class TravlyticsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1607319832387751250L;

	public TravlyticsException(){
        super();
    }

    public TravlyticsException(String message){
        super(message);
    }
}

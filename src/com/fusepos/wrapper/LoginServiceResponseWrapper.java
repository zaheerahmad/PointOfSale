/**
 * 
 */
package com.fusepos.wrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class LoginServiceResponseWrapper
{
	private int				code;
	private String			status;
	private String			message;
	private LoginWrapper	response;

	/**
	 * @param code
	 * @param status
	 * @param message
	 * @param response
	 */
	public LoginServiceResponseWrapper( int code, String status, String message, LoginWrapper response )
	{

		super();
		this.code = code;
		this.status = status;
		this.message = message;
		this.response = response;
	}

	/**
	 * @return the code
	 */
	public int getCode()
	{

		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode( int code )
	{

		this.code = code;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{

		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus( String status )
	{

		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{

		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage( String message )
	{

		this.message = message;
	}

	/**
	 * @return the response
	 */
	public LoginWrapper getResponse()
	{

		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse( LoginWrapper response )
	{

		this.response = response;
	}

}

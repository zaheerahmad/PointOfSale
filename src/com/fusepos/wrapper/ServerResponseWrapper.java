package com.fusepos.wrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class ServerResponseWrapper
{
	int		code;
	String	message;
	String	status;

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
	 * @param code
	 * @param message
	 */
	public ServerResponseWrapper( int code, String message )
	{

		super();
		this.code = code;
		this.message = message;
	}

	/**
	 * @param code
	 * @param message
	 * @param status
	 */
	public ServerResponseWrapper( int code, String message, String status )
	{

		super();
		this.code = code;
		this.message = message;
		this.status = status;
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

}

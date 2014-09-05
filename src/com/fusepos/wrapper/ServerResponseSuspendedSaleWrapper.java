package com.fusepos.wrapper;

import java.util.List;

public class ServerResponseSuspendedSaleWrapper
{
	private int									code;
	private String								status;
	private String								message;
	private List<ResponseSuspendedSaleWrapper>	response;

	/**
	 * @param code
	 * @param status
	 * @param message
	 * @param response
	 */
	public ServerResponseSuspendedSaleWrapper( int code, String status, String message, List<ResponseSuspendedSaleWrapper> response )
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
	public List<ResponseSuspendedSaleWrapper> getResponse()
	{

		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse( List<ResponseSuspendedSaleWrapper> response )
	{

		this.response = response;
	}

}

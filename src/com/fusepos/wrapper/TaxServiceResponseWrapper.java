package com.fusepos.wrapper;


public class TaxServiceResponseWrapper
{

	private Integer		code;
	private String		status;
	private String		message;
	private TaxWrapper	response;

	public Integer getCode()
	{

		return code;
	}

	public void setCode( Integer code )
	{

		this.code = code;
	}

	public String getStatus()
	{

		return status;
	}

	public void setStatus( String status )
	{

		this.status = status;
	}

	public String getMessage()
	{

		return message;
	}

	public void setMessage( String message )
	{

		this.message = message;
	}

	public TaxWrapper getResponse()
	{

		return response;
	}

	public void setResponse( TaxWrapper response )
	{

		this.response = response;
	}

}

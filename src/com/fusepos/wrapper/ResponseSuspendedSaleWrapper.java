package com.fusepos.wrapper;

public class ResponseSuspendedSaleWrapper
{
	private String	clientId;
	private String	status;
	private String	serverId;

	public ResponseSuspendedSaleWrapper( String clientId, String status, String serverId )
	{

		super();
		this.clientId = clientId;
		this.status = status;
		this.serverId = serverId;
	}

	public String getClientId()
	{

		return clientId;
	}

	public void setClientId( String clientId )
	{

		this.clientId = clientId;
	}

	public String getStatus()
	{

		return status;
	}

	public void setStatus( String status )
	{

		this.status = status;
	}

	public String getServerId()
	{

		return serverId;
	}

	public void setServerId( String serverId )
	{

		this.serverId = serverId;
	}

}

package com.fusepos.datalayer;

public class SuspendProductBO
{
	private int		id;
	private String	suspendProductJson;
	private String	suspendProductDate;

	/**
	 * @param id
	 * @param suspendProductJson
	 * @param suspendProductDate
	 */
	public SuspendProductBO( int id, String suspendProductJson, String suspendProductDate )
	{

		super();
		this.id = id;
		this.suspendProductDate = suspendProductDate;
		this.suspendProductJson = suspendProductJson;
	}

	public int getId()
	{

		return id;
	}

	public void setId( int id )
	{

		this.id = id;
	}

	public String getSuspendProductJson()
	{

		return suspendProductJson;
	}

	public void setSuspendProductJson( String suspendProductJson )
	{

		this.suspendProductJson = suspendProductJson;
	}

	public String getSuspendProductDate()
	{

		return suspendProductDate;
	}

	public void setSuspendProductDate( String suspendProductDate )
	{

		this.suspendProductDate = suspendProductDate;
	}
}

package com.fusepos.datalayer;

public class SuspendedSalesBO
{
	private int		id;
	private String	suspendedSalesJson;
	private String	suspendedSalesDate;

	/**
	 * @param id
	 * @param suspendedSalesJson
	 * @param suspendedSalesDate
	 */
	public SuspendedSalesBO( int id, String suspendedSalesJson, String suspendedSalesDate )
	{

		super();
		this.id = id;
		this.suspendedSalesDate = suspendedSalesDate;
		this.suspendedSalesJson = suspendedSalesJson;
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

		return suspendedSalesJson;
	}

	public void setSuspendProductJson( String suspendProductJson )
	{

		this.suspendedSalesJson = suspendProductJson;
	}

	public String getSuspendProductDate()
	{

		return suspendedSalesDate;
	}

	public void setSuspendProductDate( String suspendProductDate )
	{

		this.suspendedSalesDate = suspendProductDate;
	}
}

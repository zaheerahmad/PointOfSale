package com.fusepos.datalayer;

public class TaxBO
{
	private int		taxId;
	private String	name;
	private String	rate;
	private String	type;

	/**
	 * @param taxId
	 * @param name
	 * @param rate
	 * @param type
	 */

	public TaxBO( int taxId, String name, String rate, String type )
	{

		super();
		this.taxId = taxId;
		this.name = name;
		this.rate = rate;
		this.type = type;
	}

	public int getTaxId()
	{

		return taxId;
	}

	public void setTaxId( int taxId )
	{

		this.taxId = taxId;
	}

	public String getName()
	{

		return name;
	}

	public void setName( String name )
	{

		this.name = name;
	}

	public String getRate()
	{

		return rate;
	}

	public void setRate( String rate )
	{

		this.rate = rate;
	}

	public String getType()
	{

		return type;
	}

	public void setType( String type )
	{

		this.type = type;
	}

}

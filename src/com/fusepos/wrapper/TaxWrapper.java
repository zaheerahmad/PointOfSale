package com.fusepos.wrapper;


public class TaxWrapper
{

	private String	id;
	private String	name;
	private String	rate;
	private String	type;

	public TaxWrapper( String id, String name, String rate, String type )
	{

		super();
		this.id = id;
		this.name = name;
		this.rate = rate;
		this.type = type;
	}

	public String getId()
	{

		return id;
	}

	public void setId( String id )
	{

		this.id = id;
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

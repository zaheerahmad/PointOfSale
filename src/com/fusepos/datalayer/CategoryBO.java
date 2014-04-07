/**
 * 
 */
package com.fusepos.datalayer;

/**
 * @author Zaheer Ahmad
 * 
 */
public class CategoryBO
{
	private int		id;
	private String	code;
	private String	name;

	/**
	 * @param id
	 * @param code
	 * @param name
	 */
	public CategoryBO( int id, String code, String name )
	{

		super();
		this.id = id;
		this.code = code;
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId( int id )
	{

		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{

		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode( String code )
	{

		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{

		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName( String name )
	{

		this.name = name;
	}

}

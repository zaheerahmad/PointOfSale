package com.fusepos.wrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class CategoryWrapper
{

	private String	id;
	private String	name;
	private String	code;

	/**
	 * @param id
	 * @param name
	 * @param code
	 */
	public CategoryWrapper( String id, String name, String code )
	{

		super();
		this.id = id;
		this.name = name;
		this.code = code;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId( String id )
	{

		this.id = id;
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

}

/**
 * 
 */
package com.fusepos.wrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class LoginWrapper
{

	private String	email;
	private String	id;
	private String	username;
	private String	password;
	private String	firstName;
	private String	lastName;
	private String	company;
	private String	phone;

	/**
	 * @param email
	 * @param id
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param company
	 * @param phone
	 */
	public LoginWrapper( String email, String id, String username, String password, String firstName, String lastName, String company, String phone )
	{

		super();
		this.email = email;
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.company = company;
		this.phone = phone;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{

		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail( String email )
	{

		this.email = email;
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
	 * @return the username
	 */
	public String getUsername()
	{

		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername( String username )
	{

		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{

		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword( String password )
	{

		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{

		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName( String firstName )
	{

		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{

		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName( String lastName )
	{

		this.lastName = lastName;
	}

	/**
	 * @return the company
	 */
	public String getCompany()
	{

		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany( String company )
	{

		this.company = company;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{

		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone( String phone )
	{

		this.phone = phone;
	}
}

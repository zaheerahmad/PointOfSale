/**
 * 
 */
package com.fusepos.datalayer;

/**
 * @author Zaheer Ahmad
 * 
 */
public class LoginBO
{
	private int		loginId;
	private String	username;
	private String	password;
	private String	email;
	private String	fname;
	private String	lname;
	private String	company;
	private String	phone;

	/**
	 * @param loginId
	 * @param username
	 * @param password
	 * @param email
	 * @param fname
	 * @param lname
	 * @param company
	 * @param phone
	 */
	public LoginBO( int loginId, String username, String password, String email, String fname, String lname, String company, String phone )
	{

		super();
		this.loginId = loginId;
		this.username = username;
		this.password = password;
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.company = company;
		this.phone = phone;
	}

	/**
	 * @return the loginId
	 */
	public int getLoginId()
	{

		return loginId;
	}

	/**
	 * @param loginId
	 *            the loginId to set
	 */
	public void setLoginId( int loginId )
	{

		this.loginId = loginId;
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
	 * @return the fname
	 */
	public String getFname()
	{

		return fname;
	}

	/**
	 * @param fname
	 *            the fname to set
	 */
	public void setFname( String fname )
	{

		this.fname = fname;
	}

	/**
	 * @return the lname
	 */
	public String getLname()
	{

		return lname;
	}

	/**
	 * @param lname
	 *            the lname to set
	 */
	public void setLname( String lname )
	{

		this.lname = lname;
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

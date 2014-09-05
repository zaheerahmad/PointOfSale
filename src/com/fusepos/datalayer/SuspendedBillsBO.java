package com.fusepos.datalayer;

import java.math.BigDecimal;

public class SuspendedBillsBO
{
	private int			id;
	private String		date;
	private int			customerId;
	private int			count;
	private float		tax1;
	private float		tax2;
	private BigDecimal	discount;
	private BigDecimal	invTotal;
	private float		total;
	private String		status;
	private String		serverId;

	/**
	 * @param id
	 * @param date
	 * @param customerId
	 * @param count
	 * @param tax1
	 * @param tax2
	 * @param discount
	 * @param invTotal
	 * @param total
	 * @param status
	 * @param serverId
	 */
	public SuspendedBillsBO( int id, String date, int customerId, int count, float tax1, float tax2, BigDecimal discount, BigDecimal invTotal, float total, String status, String serverId )
	{

		super();
		this.id = id;
		this.date = date;
		this.customerId = customerId;
		this.count = count;
		this.tax1 = tax1;
		this.tax2 = tax2;
		this.discount = discount;
		this.invTotal = invTotal;
		this.total = total;
		this.status = status;
		this.serverId = serverId;
	}

	public int getId()
	{

		return id;
	}

	public void setId( int id )
	{

		this.id = id;
	}

	public String getDate()
	{

		return date;
	}

	public void setDate( String date )
	{

		this.date = date;
	}

	public int getCustomerId()
	{

		return customerId;
	}

	public void setCustomerId( int customerId )
	{

		this.customerId = customerId;
	}

	public int getCount()
	{

		return count;
	}

	public void setCount( int count )
	{

		this.count = count;
	}

	public float getTax1()
	{

		return tax1;
	}

	public void setTax1( float tax1 )
	{

		this.tax1 = tax1;
	}

	public float getTax2()
	{

		return tax2;
	}

	public void setTax2( float tax2 )
	{

		this.tax2 = tax2;
	}

	public BigDecimal getDiscount()
	{

		return discount;
	}

	public void setDiscount( BigDecimal discount )
	{

		this.discount = discount;
	}

	public BigDecimal getInvTotal()
	{

		return invTotal;
	}

	public void setInvTotal( BigDecimal invTotal )
	{

		this.invTotal = invTotal;
	}

	public float getTotal()
	{

		return total;
	}

	public void setTotal( float total )
	{

		this.total = total;
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

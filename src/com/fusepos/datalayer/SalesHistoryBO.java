package com.fusepos.datalayer;

import java.math.BigDecimal;

public class SalesHistoryBO
{
	private int			id;
	private int			saleId;
	private String		referenceNo;
	private int			warehouseId;
	private int			billerId;
	private String		billerName;
	private int			customerId;
	private String		customerName;
	private String		date;
	private String		note;
	private String		internalNote;
	private BigDecimal	invTotal;
	private BigDecimal	totalTax;
	private BigDecimal	total;
	private int			invoiceType;
	private String		inType;
	private BigDecimal	totalTax2;
	private int			taxRate2Id;
	private BigDecimal	invDiscount;
	private int			discountId;
	private String		user;
	private String		updatedBy;
	private String		paidBy;
	private int			count;
	private BigDecimal	shipping;
	private int			pos;
	private BigDecimal	paid;
	private String		ccNo;
	private String		ccHolder;
	private String		checqueNo;
	private String		eventTime;
	private String		action;
	private String		status;

	public SalesHistoryBO( int id, int saleId, String referenceNo, int warehouseId, int billerId, String billerName, int customerId, String customerName, String date, String note, String internalNote, BigDecimal invTotal, BigDecimal totalTax, BigDecimal total, int invoiceType, String inType, BigDecimal totalTax2, int taxRate2Id, BigDecimal invDiscount, int discountId, String user, String updatedBy, String paidBy, int count, BigDecimal shipping, int pos, BigDecimal paid, String ccNo, String ccHolder, String checqueNo, String eventTime, String action, String status )
	{

		super();
		this.id = id;
		this.saleId = saleId;
		this.referenceNo = referenceNo;
		this.warehouseId = warehouseId;
		this.billerId = billerId;
		this.billerName = billerName;
		this.customerId = customerId;
		this.customerName = customerName;
		this.date = date;
		this.note = note;
		this.internalNote = internalNote;
		this.invTotal = invTotal;
		this.totalTax = totalTax;
		this.total = total;
		this.invoiceType = invoiceType;
		this.inType = inType;
		this.totalTax2 = totalTax2;
		this.taxRate2Id = taxRate2Id;
		this.invDiscount = invDiscount;
		this.discountId = discountId;
		this.user = user;
		this.updatedBy = updatedBy;
		this.paidBy = paidBy;
		this.count = count;
		this.shipping = shipping;
		this.pos = pos;
		this.paid = paid;
		this.ccNo = ccNo;
		this.ccHolder = ccHolder;
		this.checqueNo = checqueNo;
		this.eventTime = eventTime;
		this.action = action;
		this.status = status;
	}

	public int getId()
	{

		return id;
	}

	public void setId( int id )
	{

		this.id = id;
	}

	public int getSaleId()
	{

		return saleId;
	}

	public void setSaleId( int saleId )
	{

		this.saleId = saleId;
	}

	public String getReferenceNo()
	{

		return referenceNo;
	}

	public void setReferenceNo( String referenceNo )
	{

		this.referenceNo = referenceNo;
	}

	public int getWarehouseId()
	{

		return warehouseId;
	}

	public void setWarehouseId( int warehouseId )
	{

		this.warehouseId = warehouseId;
	}

	public int getBillerId()
	{

		return billerId;
	}

	public void setBillerId( int billerId )
	{

		this.billerId = billerId;
	}

	public String getBillerName()
	{

		return billerName;
	}

	public void setBillerName( String billerName )
	{

		this.billerName = billerName;
	}

	public int getCustomerId()
	{

		return customerId;
	}

	public void setCustomerId( int customerId )
	{

		this.customerId = customerId;
	}

	public String getCustomerName()
	{

		return customerName;
	}

	public void setCustomerName( String customerName )
	{

		this.customerName = customerName;
	}

	public String getDate()
	{

		return date;
	}

	public void setDate( String date )
	{

		this.date = date;
	}

	public String getNote()
	{

		return note;
	}

	public void setNote( String note )
	{

		this.note = note;
	}

	public String getInternalNote()
	{

		return internalNote;
	}

	public void setInternalNote( String internalNote )
	{

		this.internalNote = internalNote;
	}

	public BigDecimal getInvTotal()
	{

		return invTotal;
	}

	public void setInvTotal( BigDecimal invTotal )
	{

		this.invTotal = invTotal;
	}

	public BigDecimal getTotalTax()
	{

		return totalTax;
	}

	public void setTotalTax( BigDecimal totalTax )
	{

		this.totalTax = totalTax;
	}

	public BigDecimal getTotal()
	{

		return total;
	}

	public void setTotal( BigDecimal total )
	{

		this.total = total;
	}

	public int getInvoiceType()
	{

		return invoiceType;
	}

	public void setInvoiceType( int invoiceType )
	{

		this.invoiceType = invoiceType;
	}

	public String getInType()
	{

		return inType;
	}

	public void setInType( String inType )
	{

		this.inType = inType;
	}

	public BigDecimal getTotalTax2()
	{

		return totalTax2;
	}

	public void setTotalTax2( BigDecimal totalTax2 )
	{

		this.totalTax2 = totalTax2;
	}

	public int getTaxRate2Id()
	{

		return taxRate2Id;
	}

	public void setTaxRate2Id( int taxRate2Id )
	{

		this.taxRate2Id = taxRate2Id;
	}

	public BigDecimal getInvDiscount()
	{

		return invDiscount;
	}

	public void setInvDiscount( BigDecimal invDiscount )
	{

		this.invDiscount = invDiscount;
	}

	public int getDiscountId()
	{

		return discountId;
	}

	public void setDiscountId( int discountId )
	{

		this.discountId = discountId;
	}

	public String getUser()
	{

		return user;
	}

	public void setUser( String user )
	{

		this.user = user;
	}

	public String getUpdatedBy()
	{

		return updatedBy;
	}

	public void setUpdatedBy( String updatedBy )
	{

		this.updatedBy = updatedBy;
	}

	public String getPaidBy()
	{

		return paidBy;
	}

	public void setPaidBy( String paidBy )
	{

		this.paidBy = paidBy;
	}

	public int getCount()
	{

		return count;
	}

	public void setCount( int count )
	{

		this.count = count;
	}

	public BigDecimal getShipping()
	{

		return shipping;
	}

	public void setShipping( BigDecimal shipping )
	{

		this.shipping = shipping;
	}

	public int getPos()
	{

		return pos;
	}

	public void setPos( int pos )
	{

		this.pos = pos;
	}

	public BigDecimal getPaid()
	{

		return paid;
	}

	public void setPaid( BigDecimal paid )
	{

		this.paid = paid;
	}

	public String getCcNo()
	{

		return ccNo;
	}

	public void setCcNo( String ccNo )
	{

		this.ccNo = ccNo;
	}

	public String getCcHolder()
	{

		return ccHolder;
	}

	public void setCcHolder( String ccHolder )
	{

		this.ccHolder = ccHolder;
	}

	public String getChecqueNo()
	{

		return checqueNo;
	}

	public void setChecqueNo( String checqueNo )
	{

		this.checqueNo = checqueNo;
	}

	public String getEventTime()
	{

		return eventTime;
	}

	public void setEventTime( String eventTime )
	{

		this.eventTime = eventTime;
	}

	public String getAction()
	{

		return action;
	}

	public void setAction( String action )
	{

		this.action = action;
	}

	public String getStatus()
	{

		return status;
	}

	public void setStatus( String status )
	{

		this.status = status;
	}

}

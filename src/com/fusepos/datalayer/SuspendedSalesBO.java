package com.fusepos.datalayer;

import java.math.BigDecimal;

public class SuspendedSalesBO
{
	private int			id;
	private int			suspendedId;
	private int			productId;
	private String		productCode;
	private String		productName;
	private String		productUnit;
	private int			taxRateId;
	private String		tax;
	private int			quantity;
	private BigDecimal	unitPrice;
	private BigDecimal	grossTotal;
	private BigDecimal	valTax;
	private String		discount;
	private int			discountId;
	private BigDecimal	discountVal;
	private String		serialNo;
	private String		status;

	public SuspendedSalesBO( int id, int suspendedId, int productId, String productCode, String productName, String productUnit, int taxRateId, String tax, int quantity, BigDecimal unitPrice, BigDecimal grossTotal, BigDecimal valTax, String discount, int discountId, BigDecimal discountVal, String serialNo, String status )
	{

		super();
		this.id = id;
		this.suspendedId = suspendedId;
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.productUnit = productUnit;
		this.taxRateId = taxRateId;
		this.tax = tax;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.grossTotal = grossTotal;
		this.valTax = valTax;
		this.discount = discount;
		this.discountId = discountId;
		this.discountVal = discountVal;
		this.serialNo = serialNo;
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

	public int getSuspendedId()
	{

		return suspendedId;
	}

	public void setSuspendedId( int suspendedId )
	{

		this.suspendedId = suspendedId;
	}

	public int getProductId()
	{

		return productId;
	}

	public void setProductId( int productId )
	{

		this.productId = productId;
	}

	public String getProductCode()
	{

		return productCode;
	}

	public void setProductCode( String productCode )
	{

		this.productCode = productCode;
	}

	public String getProductName()
	{

		return productName;
	}

	public void setProductName( String productName )
	{

		this.productName = productName;
	}

	public String getProductUnit()
	{

		return productUnit;
	}

	public void setProductUnit( String productUnit )
	{

		this.productUnit = productUnit;
	}

	public int getTaxRateId()
	{

		return taxRateId;
	}

	public void setTaxRateId( int taxRateId )
	{

		this.taxRateId = taxRateId;
	}

	public String getTax()
	{

		return tax;
	}

	public void setTax( String tax )
	{

		this.tax = tax;
	}

	public int getQuantity()
	{

		return quantity;
	}

	public void setQuantity( int quantity )
	{

		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice()
	{

		return unitPrice;
	}

	public void setUnitPrice( BigDecimal unitPrice )
	{

		this.unitPrice = unitPrice;
	}

	public BigDecimal getGrossTotal()
	{

		return grossTotal;
	}

	public void setGrossTotal( BigDecimal grossTotal )
	{

		this.grossTotal = grossTotal;
	}

	public BigDecimal getValTax()
	{

		return valTax;
	}

	public void setValTax( BigDecimal valTax )
	{

		this.valTax = valTax;
	}

	public String getDiscount()
	{

		return discount;
	}

	public void setDiscount( String discount )
	{

		this.discount = discount;
	}

	public int getDiscountId()
	{

		return discountId;
	}

	public void setDiscountId( int discountId )
	{

		this.discountId = discountId;
	}

	public BigDecimal getDiscountVal()
	{

		return discountVal;
	}

	public void setDiscountVal( BigDecimal discountVal )
	{

		this.discountVal = discountVal;
	}

	public String getSerialNo()
	{

		return serialNo;
	}

	public void setSerialNo( String serialNo )
	{

		this.serialNo = serialNo;
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

/**
 * 
 */
package com.fusepos.datalayer;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Waqas Ahmad
 * 
 */
public class ProductBO
{
	private int			id;
	private String		code;
	private String		name;
	private String		unit;
	private String		size;
	private BigDecimal	cost;
	private BigDecimal	price;
	private String		alertQuality;
	private String		image;
	private int			categoryId;
	private int			subCategoryId;
	private String		quantity;
	private BigDecimal	taxRate;
	private int			taxQuantity;
	private String		details;

	public ProductBO( int id, String code, String name, String unit, String size, BigDecimal cost, BigDecimal price, String alertQuality, String image, int categoryId, int subCategoryId, String quantity, BigDecimal taxRate, int taxQuantity, String details )
	{

		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.unit = unit;
		this.size = size;
		this.cost = cost;
		this.price = price;
		this.alertQuality = alertQuality;
		this.image = image;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.quantity = quantity;
		this.taxRate = taxRate;
		this.taxQuantity = taxQuantity;
		this.details = details;
	}

	public int getId()
	{

		return id;
	}

	public void setId( int id )
	{

		this.id = id;
	}

	public String getCode()
	{

		return code;
	}

	public void setCode( String code )
	{

		this.code = code;
	}

	public String getName()
	{

		return name;
	}

	public void setName( String name )
	{

		this.name = name;
	}

	public String getUnit()
	{

		return unit;
	}

	public void setUnit( String unit )
	{

		this.unit = unit;
	}

	public String getSize()
	{

		return size;
	}

	public void setSize( String size )
	{

		this.size = size;
	}

	public BigDecimal getCost()
	{

		return cost;
	}

	public void setCost( BigDecimal cost )
	{

		this.cost = cost;
	}

	public BigDecimal getPrice()
	{

		return price;
	}

	public void setPrice( BigDecimal price )
	{

		this.price = price;
	}

	public String getAlertQuality()
	{

		return alertQuality;
	}

	public void setAlertQuality( String alertQuality )
	{

		this.alertQuality = alertQuality;
	}

	public String getImage()
	{

		return image;
	}

	public void setImage( String image )
	{

		this.image = image;
	}

	public int getCategoryId()
	{

		return categoryId;
	}

	public void setCategoryId( int categoryId )
	{

		this.categoryId = categoryId;
	}

	public int getSubCategoryId()
	{

		return subCategoryId;
	}

	public void setSubCategoryId( int subCategoryId )
	{

		this.subCategoryId = subCategoryId;
	}

	public String getQuantity()
	{

		return quantity;
	}

	public void setQuantity( String quantity )
	{

		this.quantity = quantity;
	}

	public BigDecimal getTaxRate()
	{

		return taxRate;
	}

	public void setTaxRate( BigDecimal taxRate )
	{

		this.taxRate = taxRate;
	}

	public int getTaxQuantity()
	{

		return taxQuantity;
	}

	public void setTaxQuantity( int taxQuantity )
	{

		this.taxQuantity = taxQuantity;
	}

	public String getDetails()
	{

		return details;
	}

	public void setDetails( String details )
	{

		this.details = details;
	}

	public boolean isListHasProduct( ProductBO product, List<ProductBO> list )
	{

		for( int i = 0 ; i < list.size() ; i++ )
		{
			if( list.get( i ).getId() == product.id )
				return true;
		}
		return false;
	}

}

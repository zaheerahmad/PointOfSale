/**
 * 
 */
package com.fusepos.wrapper;

/**
 * @author Waqas Ahmed
 * 
 */
public class ProductWrapper
{

	private String			id;
	private String			code;
	private String			name;
	private String			unit;
	private String			size;
	private String			cost;
	private String			price;
	private String			alertQuality;
	private String			image;
	private String			categoryId;
	private CategoryWrapper	categoryDetail;
	private String			subCategoryId;
	private String			quantity;
	private String			taxRate;
	private String			taxQuantity;
	private String			details;

	/**
	 * @param id
	 * @param code
	 * @param name
	 * @param unit
	 * @param size
	 * @param cost
	 * @param price
	 * @param alertQuality
	 * @param image
	 * @param categoryId
	 * @param categoryDetail
	 * @param subCategoryId
	 * @param quantity
	 * @param taxRate
	 * @param taxQuantity
	 * @param details
	 */
	public ProductWrapper( String id, String code, String name, String unit, String size, String cost, String price, String alertQuality, String image, String categoryId, CategoryWrapper categoryDetail, String subCategoryId, String quantity, String taxRate, String taxQuantity, String details )
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
		this.categoryDetail = categoryDetail;
		this.subCategoryId = subCategoryId;
		this.quantity = quantity;
		this.taxRate = taxRate;
		this.taxQuantity = taxQuantity;
		this.details = details;
	}

	public ProductWrapper( String id, String code, String name, String unit, String size, String cost, String price, String alertQuality, String image, String categoryId, String subCategoryId, String quantity, String taxRate, String taxQuantity, String details )
	{

		super();
		this.id = id == null ? "" : id;
		this.code = code == null ? "" : code;
		this.name = name == null ? "" : name;
		this.unit = unit == null ? "" : unit;
		this.size = size == null ? "" : size;
		this.cost = cost == null ? "" : cost;
		this.price = price == null ? "" : price;
		this.alertQuality = alertQuality == null ? "" : alertQuality;
		this.image = image == null ? "" : image;
		this.categoryId = categoryId == null ? "" : categoryId;
		this.subCategoryId = subCategoryId == null ? "" : subCategoryId;
		this.quantity = quantity == null ? "" : quantity;
		this.taxRate = taxRate == null ? "" : taxRate;
		this.taxQuantity = taxQuantity == null ? "" : taxQuantity;
		this.details = details == null ? "" : details;
	}

	public String getId()
	{

		return id;
	}

	public void setId( String id )
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

	public String getCost()
	{

		return cost;
	}

	public void setCost( String cost )
	{

		this.cost = cost;
	}

	public String getPrice()
	{

		return price;
	}

	public void setPrice( String price )
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

	public String getCategoryId()
	{

		return categoryId;
	}

	public void setCategoryId( String categoryId )
	{

		this.categoryId = categoryId;
	}

	public String getSubCategoryId()
	{

		return subCategoryId;
	}

	public void setSubCategoryId( String subCategoryId )
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

	public String getTaxRate()
	{

		return taxRate;
	}

	public void setTaxRate( String taxRate )
	{

		this.taxRate = taxRate;
	}

	public String getTaxQuantity()
	{

		return taxQuantity;
	}

	public void setTaxQuantity( String taxQuantity )
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

	/**
	 * @return the categoryDetail
	 */
	public CategoryWrapper getCategoryDetail()
	{

		return categoryDetail;
	}

	/**
	 * @param categoryDetail
	 *            the categoryDetail to set
	 */
	public void setCategoryDetail( CategoryWrapper categoryDetail )
	{

		this.categoryDetail = categoryDetail;
	}

}

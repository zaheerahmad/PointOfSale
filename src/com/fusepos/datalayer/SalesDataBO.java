package com.fusepos.datalayer;

import java.util.List;

public class SalesDataBO
{
	// SalesData with data members (Sales sale, SalesHistory saleHistory,
	// List<SaleItems> saleItemsList, List<SaleItemsHistory> saleItemHistory)
	SalesBO						salesBO;
	SalesHistoryBO				salesHistoryBO;
	List<SaleItemsBO>			saleItemsList;
	List<SaleItemsHistoryBO>	saleItemsHistoryBOList;

	public SalesDataBO( SalesBO salesBO, SalesHistoryBO salesHistoryBO, List<SaleItemsBO> saleItemsList, List<SaleItemsHistoryBO> saleItemsHistoryBOList )
	{

		super();
		this.salesBO = salesBO;
		this.salesHistoryBO = salesHistoryBO;
		this.saleItemsList = saleItemsList;
		this.saleItemsHistoryBOList = saleItemsHistoryBOList;
	}

	public SalesBO getSalesBO()
	{

		return salesBO;
	}

	public void setSalesBO( SalesBO salesBO )
	{

		this.salesBO = salesBO;
	}

	public SalesHistoryBO getSalesHistoryBO()
	{

		return salesHistoryBO;
	}

	public void setSalesHistoryBO( SalesHistoryBO salesHistoryBO )
	{

		this.salesHistoryBO = salesHistoryBO;
	}

	public List<SaleItemsBO> getSaleItemsList()
	{

		return saleItemsList;
	}

	public void setSaleItemsList( List<SaleItemsBO> saleItemsList )
	{

		this.saleItemsList = saleItemsList;
	}

	public List<SaleItemsHistoryBO> getSaleItemsHistoryBOList()
	{

		return saleItemsHistoryBOList;
	}

	public void setSaleItemsHistoryBOList( List<SaleItemsHistoryBO> saleItemsHistoryBOList )
	{

		this.saleItemsHistoryBOList = saleItemsHistoryBOList;
	}

}

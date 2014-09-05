package com.fusepos.wrapper;

import java.util.List;

import com.fusepos.datalayer.SuspendedBillsBO;
import com.fusepos.datalayer.SuspendedSalesBO;

public class SuspendedSyncResponseWrapper
{
	int						code;
	List<SuspendedSalesBO>	suspendedSalesBOList;
	List<SuspendedBillsBO>	suspendedBillsBOList;

	public int getCode()
	{

		return code;
	}

	public void setCode( int code )
	{

		this.code = code;
	}

	public List<SuspendedSalesBO> getSuspendedSalesBOList()
	{

		return suspendedSalesBOList;
	}

	public void setSuspendedSalesBOList( List<SuspendedSalesBO> suspendedSalesBOList )
	{

		this.suspendedSalesBOList = suspendedSalesBOList;
	}

	public List<SuspendedBillsBO> getSuspendedBillsBOList()
	{

		return suspendedBillsBOList;
	}

	public void setSuspendedBillsBOList( List<SuspendedBillsBO> suspendedBillsBOList )
	{

		this.suspendedBillsBOList = suspendedBillsBOList;
	}

}

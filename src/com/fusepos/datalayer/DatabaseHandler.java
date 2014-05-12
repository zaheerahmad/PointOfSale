package com.fusepos.datalayer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fusepos.utils.AppGlobal;

/**
 * @author Zaheer Ahmad
 * 
 */
public class DatabaseHandler extends SQLiteOpenHelper
{

	String	tblName	= "";

	/**
	 * @param context
	 * @param tblName
	 */
	public DatabaseHandler( Context context, String tblName )
	{

		super( context, AppGlobal.DATABASE_NAME, null, AppGlobal.DATABASE_VERSION );
		this.tblName = tblName;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate( SQLiteDatabase db )
	{

		String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + AppGlobal.TABLE_LOGIN + "(" + AppGlobal.LOGIN_ID + " INTEGER," + AppGlobal.USER_NAME + " TEXT," + AppGlobal.FNAME + " TEXT," + AppGlobal.LNAME + " TEXT," + AppGlobal.PASSWORD + " TEXT," + AppGlobal.EMAIL + " TEXT," + AppGlobal.COMPANY + " TEXT" + "," + AppGlobal.PHONE + " TEXT" + ")";
		db.execSQL( CREATE_FEEDBACK_TABLE );

		String CREATE_PRODUCT_TABLE = "CREATE TABLE " + AppGlobal.TABLE_PRODUCT + "(" + AppGlobal.PRODUCT_ID + " INTEGER," + AppGlobal.PRODUCT_CODE + " TEXT," + AppGlobal.PRODUCT_NAME + " TEXT," + AppGlobal.PRODUCT_UNIT + " TEXT," + AppGlobal.PRODUCT_SIZE + " TEXT," + AppGlobal.PRODUCT_COST + " DOUBLE PRECISION," + AppGlobal.PRODUCT_PRICE + " DOUBLE PRECISION" + "," + AppGlobal.PRODUCT_ALERT_QUALITY + " TEXT," + AppGlobal.PRODUCT_IMAGE + " TEXT," + AppGlobal.PRODUCT_CATEGORY_ID + " INTEGER," + AppGlobal.PRODUCT_SUB_CATEGORY_ID + " INTEGER," + AppGlobal.PRODUCT_QUANTITY + " TEXT," + AppGlobal.PRODUCT_TAX_RATE + " DOUBLE PRECISION," + AppGlobal.PRODUCT_TAX_QUANTITY + " INTEGER," + AppGlobal.PRODUCT_DETAILS + " TEXT" + ")";
		db.execSQL( CREATE_PRODUCT_TABLE );

		String CREATE_CATEGORY_TABLE = "CREATE TABLE " + AppGlobal.TABLE_CATEGORY + "(" + AppGlobal.CATEGORY_ID + " INTEGER," + AppGlobal.CATEGORY_CODE + " TEXT," + AppGlobal.CATEGORY_NAME + " TEXT)";
		db.execSQL( CREATE_CATEGORY_TABLE );

		String CREATE_SUSPENDED_SALES_TABLE = "CREATE TABLE " + AppGlobal.TABLE_SUSPENDED_SALES + "(" + AppGlobal.SUSPENDED_SALES_ID + " INTEGER," + AppGlobal.SUSPENDED_SALES_JSON + " TEXT," + AppGlobal.SUSPENDED_SALES_DATE + " TEXT)";
		db.execSQL( CREATE_SUSPENDED_SALES_TABLE );

		String CREATE_TAX_RATE_TABLE = "CREATE TABLE " + AppGlobal.TABLE_TAX_RATE + "(" + AppGlobal.TAX_RATE_ID + " INTEGER," + AppGlobal.TAX_RATE_NAME + " TEXT," + AppGlobal.TAX_RATE_RATE + " TEXT," + AppGlobal.TAX_RATE_TYPE + " TEXT)";
		db.execSQL( CREATE_TAX_RATE_TABLE );

		String CREATE_SALES_TABLE = "CREATE TABLE " + AppGlobal.TABLE_SALES + "(" + AppGlobal.SALES_ID + " INTEGER," + AppGlobal.SALES_REFERENCE_NO + " TEXT," + AppGlobal.SALES_WAREHOUSE_ID + " INTEGER," + AppGlobal.SALES_BILLER_ID + " INTEGER," + AppGlobal.SALES_BILLER_NAME + " TEXT," + AppGlobal.SALES_CUSTOMER_ID + " INTEGER," + AppGlobal.SALES_CUSTOMER_NAME + " TEXT," + AppGlobal.SALES_DATE + " TEXT," + AppGlobal.SALES_NOTE + " TEXT," + AppGlobal.SALES_INTERNAL_NOTE + " TEXT," + AppGlobal.SALES_INV_TOTAL + " TEXT," + AppGlobal.SALES_TOTAL_TAX + " TEXT," + AppGlobal.SALES_TOTAL + " TEXT," + AppGlobal.SALES_INVOICE_TYPE + " INTEGER," + AppGlobal.SALES_IN_TYPE + " TEXT," + AppGlobal.SALES_TOTAL_TAX2 + " TEXT," + AppGlobal.SALES_TAX_RATE2_ID + " INTEGER," + AppGlobal.SALES_INV_DISCOUNT + " TEXT," + AppGlobal.SALES_DISCOUNT_ID + " INTEGER," + AppGlobal.SALES_USER + " TEXT," + AppGlobal.SALES_UPDATED_BY + " TEXT," + AppGlobal.SALES_PAID_BY + " TEXT," + AppGlobal.SALES_COUNT + " INTEGER," + AppGlobal.SALES_SHIPPING + " TEXT," + AppGlobal.SALES_POS + " INTEGER," + AppGlobal.SALES_PAID + " TEXT," + AppGlobal.SALES_CC_NO + " TEXT," + AppGlobal.SALES_CC_HOLDER + " TEXT," + AppGlobal.SALES_CHECQUE_NO + " TEXT)";
		db.execSQL( CREATE_SALES_TABLE );

		String CREATE_SALES_HISTORY_TABLE = "CREATE TABLE " + AppGlobal.TABLE_SALES_HISTORY + "(" + AppGlobal.SALES_HISTORY_ID + " INTEGER," + AppGlobal.SALES_HISTORY_SALES_ID + " INTEGER," + AppGlobal.SALES_HISTORY_REFERENCE_NO + " TEXT," + AppGlobal.SALES_HISTORY_WAREHOUSE_ID + " INTEGER," + AppGlobal.SALES_HISTORY_BILLER_ID + " INTEGER," + AppGlobal.SALES_HISTORY_BILLER_NAME + " TEXT," + AppGlobal.SALES_HISTORY_CUSTOMER_ID + " INTEGER," + AppGlobal.SALES_HISTORY_CUSTOMER_NAME + " TEXT," + AppGlobal.SALES_HISTORY_DATE + " TEXT," + AppGlobal.SALES_HISTORY_NOTE + " TEXT," + AppGlobal.SALES_HISTORY_INTERNAL_NOTE + " TEXT," + AppGlobal.SALES_HISTORY_INV_TOTAL + " TEXT," + AppGlobal.SALES_HISTORY_TOTAL_TAX + " TEXT," + AppGlobal.SALES_HISTORY_TOTAL + " TEXT," + AppGlobal.SALES_HISTORY_INVOICE_TYPE + " INTEGER," + AppGlobal.SALES_HISTORY_IN_TYPE + " TEXT," + AppGlobal.SALES_HISTORY_TOTAL_TAX2 + " TEXT," + AppGlobal.SALES_HISTORY_TAX_RATE2_ID + " INTEGER," + AppGlobal.SALES_HISTORY_INV_DISCOUNT + " TEXT," + AppGlobal.SALES_HISTORY_DISCOUNT_ID + " INTEGER," + AppGlobal.SALES_HISTORY_USER + " TEXT," + AppGlobal.SALES_HISTORY_UPDATED_BY + " TEXT," + AppGlobal.SALES_HISTORY_PAID_BY + " TEXT," + AppGlobal.SALES_HISTORY_COUNT + " INTEGER," + AppGlobal.SALES_HISTORY_SHIPPING + " TEXT," + AppGlobal.SALES_HISTORY_POS + " INTEGER," + AppGlobal.SALES_HISTORY_PAID + " TEXT," + AppGlobal.SALES_HISTORY_CC_NO + " TEXT," + AppGlobal.SALES_HISTORY_CC_HOLDER + " TEXT," + AppGlobal.SALES_HISTORY_CHECQUE_NO + " TEXT," + AppGlobal.SALES_HISTORY_EVENT_TIME + " TEXT," + AppGlobal.SALES_HISTORY_ACTION + " TEXT)";
		db.execSQL( CREATE_SALES_HISTORY_TABLE );

		String CREATE_SALE_ITEMS_TABLE = "CREATE TABLE " + AppGlobal.TABLE_SALE_ITEMS + "(" + AppGlobal.SALE_ITEMS_ID + " INTEGER," + AppGlobal.SALE_ITEMS_SALE_ID + " INTEGER," + AppGlobal.SALE_ITEMS_PRODUCT_ID + " INTEGER," + AppGlobal.SALE_ITEMS_PRODUCT_CODE + " TEXT," + AppGlobal.SALE_ITEMS_PRODUCT_NAME + " TEXT," + AppGlobal.SALE_ITEMS_PRODUCT_UNIT + " TEXT," + AppGlobal.SALE_ITEMS_TAX_RATE_ID + " INTEGER," + AppGlobal.SALE_ITEMS_TAX + " TEXT," + AppGlobal.SALE_ITEMS_QUANTITY + " INTEGER," + AppGlobal.SALE_ITEMS_UNIT_PRICE + " TEXT," + AppGlobal.SALE_ITEMS_GROSS_TOTAL + " TEXT," + AppGlobal.SALE_ITEMS_VAL_TAX + " TEXT," + AppGlobal.SALE_ITEMS_SERIAL_NO + " TEXT," + AppGlobal.SALE_ITEMS_DISCOUNT_VAL + " TEXT," + AppGlobal.SALE_ITEMS_DISCOUNT + " TEXT," + AppGlobal.SALE_ITEMS_DISCOUNT_ID + " INTEGER)";
		db.execSQL( CREATE_SALE_ITEMS_TABLE );

		String CREATE_SALE_ITEMS_HISTORY_TABLE = "CREATE TABLE " + AppGlobal.TABLE_SALE_ITEMS_HISTORY + "(" + AppGlobal.SALE_ITEMS_HISTORY_ID + " INTEGER," + AppGlobal.SALE_ITEMS_HISTORY_SALE_ITEMS_ID + " INTEGER," + AppGlobal.SALE_ITEMS_HISTORY_SALE_ID + " INTEGER," + AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_ID + " INTEGER," + AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_CODE + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_NAME + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_UNIT + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_TAX_RATE_ID + " INTEGER," + AppGlobal.SALE_ITEMS_HISTORY_TAX + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_QUANTITY + " INTEGER," + AppGlobal.SALE_ITEMS_HISTORY_UNIT_PRICE + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_GROSS_TOTAL + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_VAL_TAX + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_SERIAL_NO + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_DISCOUNT_VAL + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_DISCOUNT + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_DISCOUNT_ID + " INTEGER," + AppGlobal.SALE_ITEMS_HISTORY_EVENT_TIME + " TEXT," + AppGlobal.SALE_ITEMS_HISTORY_ACTION + " TEXT)";
		db.execSQL( CREATE_SALE_ITEMS_HISTORY_TABLE );
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{

		// Drop older table if existed
		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_LOGIN );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_PRODUCT );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_CATEGORY );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_SUSPENDED_SALES );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_TAX_RATE );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_SALES );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_SALES_HISTORY );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_SALE_ITEMS );

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_SALE_ITEMS_HISTORY );
		// Create tables again
		onCreate( db );

	}

	/*
	 * sales table methods
	 */

	public void addSales( SalesBO salesBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.SALES_ID, salesBO.getId() );
		values.put( AppGlobal.SALES_REFERENCE_NO, salesBO.getReferenceNo() );
		values.put( AppGlobal.SALES_WAREHOUSE_ID, salesBO.getWarehouseId() );
		values.put( AppGlobal.SALES_BILLER_ID, salesBO.getBillerId() );
		values.put( AppGlobal.SALES_BILLER_NAME, salesBO.getBillerName() );
		values.put( AppGlobal.SALES_CUSTOMER_ID, salesBO.getCustomerId() );
		values.put( AppGlobal.SALES_CUSTOMER_NAME, salesBO.getCustomerName() );
		values.put( AppGlobal.SALES_DATE, salesBO.getDate() );
		values.put( AppGlobal.SALES_NOTE, salesBO.getNote() );
		values.put( AppGlobal.SALES_INTERNAL_NOTE, salesBO.getInternalNote() );
		values.put( AppGlobal.SALES_INV_TOTAL, salesBO.getInvTotal().toPlainString() );
		values.put( AppGlobal.SALES_TOTAL_TAX, salesBO.getTotalTax().toPlainString() );
		values.put( AppGlobal.SALES_TOTAL, salesBO.getTotal().toPlainString() );
		values.put( AppGlobal.SALES_INVOICE_TYPE, salesBO.getInvoiceType() );
		values.put( AppGlobal.SALES_IN_TYPE, salesBO.getInType() );
		values.put( AppGlobal.SALES_TOTAL_TAX2, salesBO.getTotalTax2().toPlainString() );
		values.put( AppGlobal.SALES_TAX_RATE2_ID, salesBO.getTaxRate2Id() );
		values.put( AppGlobal.SALES_INV_DISCOUNT, salesBO.getInvDiscount().toPlainString() );
		values.put( AppGlobal.SALES_DISCOUNT_ID, salesBO.getDiscountId() );
		values.put( AppGlobal.SALES_USER, salesBO.getUser() );
		values.put( AppGlobal.SALES_UPDATED_BY, salesBO.getUpdatedBy() );
		values.put( AppGlobal.SALES_PAID_BY, salesBO.getPaidBy() );
		values.put( AppGlobal.SALES_COUNT, salesBO.getCount() );
		values.put( AppGlobal.SALES_SHIPPING, salesBO.getShipping().toPlainString() );
		values.put( AppGlobal.SALES_POS, salesBO.getPos() );
		values.put( AppGlobal.SALES_PAID, salesBO.getPaid().toPlainString() );
		values.put( AppGlobal.SALES_CC_NO, salesBO.getCcNo() );
		values.put( AppGlobal.SALES_CC_HOLDER, salesBO.getCcHolder() );
		values.put( AppGlobal.SALES_CHECQUE_NO, salesBO.getChecqueNo() );

		long id = db.insert( AppGlobal.TABLE_SALES, null, values );
		db.close(); // Closing database connection
	}

	public int getLastInsertedSalesId()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_SALES + " ORDER BY " + AppGlobal.SALES_ID + " DESC LIMIT 1";
		SQLiteDatabase db1 = this.getReadableDatabase();
		Cursor cursor = db1.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			return Integer.parseInt( cursor.getString( 0 ) );
		}
		db1.close();
		return 0;
	}

	public List<SalesBO> getAllSales()
	{

		List<SalesBO> salesBOList = new ArrayList<SalesBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SALES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SalesBO salesBO = new SalesBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getInt( 2 ), cursor.getInt( 3 ), cursor.getString( 4 ), cursor.getInt( 5 ), cursor.getString( 6 ), cursor.getString( 7 ), cursor.getString( 8 ), cursor.getString( 9 ), new BigDecimal( cursor.getString( 10 ) ), new BigDecimal( cursor.getString( 11 ) ), new BigDecimal( cursor.getString( 12 ) ), cursor.getInt( 13 ), cursor.getString( 14 ), new BigDecimal( cursor.getString( 15 ) ), cursor.getInt( 16 ), new BigDecimal( cursor.getString( 17 ) ), cursor.getInt( 18 ), cursor.getString( 19 ), cursor.getString( 20 ), cursor.getString( 21 ), cursor.getInt( 22 ), new BigDecimal( cursor.getString( 23 ) ), cursor.getInt( 24 ), new BigDecimal( cursor.getString( 25 ) ), cursor.getString( 26 ), cursor.getString( 27 ), cursor.getString( 28 ) );
				salesBOList.add( salesBO );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return salesBOList;
	}

	/*
	 * sales history table methods
	 */
	public int getLastInsertedSalesHistoryId()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_SALES_HISTORY + " ORDER BY " + AppGlobal.SALES_HISTORY_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			return Integer.parseInt( cursor.getString( 0 ) );
		}
		db.close();
		return 0;
	}

	public void addSalesHistory( SalesHistoryBO salesHistoryBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.SALES_HISTORY_ID, salesHistoryBO.getId() );
		values.put( AppGlobal.SALES_HISTORY_SALES_ID, salesHistoryBO.getSaleId() );
		values.put( AppGlobal.SALES_HISTORY_REFERENCE_NO, salesHistoryBO.getReferenceNo() );
		values.put( AppGlobal.SALES_HISTORY_WAREHOUSE_ID, salesHistoryBO.getWarehouseId() );
		values.put( AppGlobal.SALES_HISTORY_BILLER_ID, salesHistoryBO.getBillerId() );
		values.put( AppGlobal.SALES_HISTORY_BILLER_NAME, salesHistoryBO.getBillerName() );
		values.put( AppGlobal.SALES_HISTORY_CUSTOMER_ID, salesHistoryBO.getCustomerId() );
		values.put( AppGlobal.SALES_HISTORY_CUSTOMER_NAME, salesHistoryBO.getCustomerName() );
		values.put( AppGlobal.SALES_HISTORY_DATE, salesHistoryBO.getDate() );
		values.put( AppGlobal.SALES_HISTORY_NOTE, salesHistoryBO.getNote() );
		values.put( AppGlobal.SALES_HISTORY_INTERNAL_NOTE, salesHistoryBO.getInternalNote() );
		values.put( AppGlobal.SALES_HISTORY_INV_TOTAL, salesHistoryBO.getInvTotal().toPlainString() );
		values.put( AppGlobal.SALES_HISTORY_TOTAL_TAX, salesHistoryBO.getTotalTax().toPlainString() );
		values.put( AppGlobal.SALES_HISTORY_TOTAL, salesHistoryBO.getTotal().toPlainString() );
		values.put( AppGlobal.SALES_HISTORY_INVOICE_TYPE, salesHistoryBO.getInvoiceType() );
		values.put( AppGlobal.SALES_HISTORY_IN_TYPE, salesHistoryBO.getInType() );
		values.put( AppGlobal.SALES_HISTORY_TOTAL_TAX2, salesHistoryBO.getTotalTax2().toPlainString() );
		values.put( AppGlobal.SALES_HISTORY_TAX_RATE2_ID, salesHistoryBO.getTaxRate2Id() );
		values.put( AppGlobal.SALES_HISTORY_INV_DISCOUNT, salesHistoryBO.getInvDiscount().toPlainString() );
		values.put( AppGlobal.SALES_HISTORY_DISCOUNT_ID, salesHistoryBO.getDiscountId() );
		values.put( AppGlobal.SALES_HISTORY_USER, salesHistoryBO.getUser() );
		values.put( AppGlobal.SALES_HISTORY_UPDATED_BY, salesHistoryBO.getUpdatedBy() );
		values.put( AppGlobal.SALES_HISTORY_PAID_BY, salesHistoryBO.getPaidBy() );
		values.put( AppGlobal.SALES_HISTORY_COUNT, salesHistoryBO.getCount() );
		values.put( AppGlobal.SALES_HISTORY_SHIPPING, salesHistoryBO.getShipping().toPlainString() );
		values.put( AppGlobal.SALES_HISTORY_POS, salesHistoryBO.getPos() );
		values.put( AppGlobal.SALES_HISTORY_PAID, salesHistoryBO.getPaid().toPlainString() );
		values.put( AppGlobal.SALES_HISTORY_CC_NO, salesHistoryBO.getCcNo() );
		values.put( AppGlobal.SALES_HISTORY_CC_HOLDER, salesHistoryBO.getCcHolder() );
		values.put( AppGlobal.SALES_HISTORY_CHECQUE_NO, salesHistoryBO.getChecqueNo() );
		values.put( AppGlobal.SALES_HISTORY_EVENT_TIME, salesHistoryBO.getEventTime() );
		values.put( AppGlobal.SALES_HISTORY_ACTION, salesHistoryBO.getAction() );

		long id = db.insert( AppGlobal.TABLE_SALES_HISTORY, null, values );
		db.close(); // Closing database connection
	}

	public List<SalesHistoryBO> getAllSalesHistory()
	{

		List<SalesHistoryBO> salesHistoryBOList = new ArrayList<SalesHistoryBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SALES_HISTORY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SalesHistoryBO salesHistoryBO = new SalesHistoryBO( cursor.getInt( 0 ), cursor.getInt( 1 ), cursor.getString( 2 ), cursor.getInt( 3 ), cursor.getInt( 4 ), cursor.getString( 5 ), cursor.getInt( 6 ), cursor.getString( 7 ), cursor.getString( 8 ), cursor.getString( 9 ), cursor.getString( 10 ), new BigDecimal( cursor.getString( 11 ) ), new BigDecimal( cursor.getString( 12 ) ), new BigDecimal( cursor.getString( 13 ) ), cursor.getInt( 14 ), cursor.getString( 15 ), new BigDecimal( cursor.getString( 16 ) ), cursor.getInt( 17 ), new BigDecimal( cursor.getString( 18 ) ), cursor.getInt( 19 ), cursor.getString( 20 ), cursor.getString( 21 ), cursor.getString( 22 ), cursor.getInt( 23 ), new BigDecimal( cursor.getString( 24 ) ), cursor.getInt( 25 ), new BigDecimal( cursor.getString( 26 ) ), cursor.getString( 27 ), cursor.getString( 28 ), cursor.getString( 29 ), cursor.getString( 30 ), cursor.getString( 31 ) );
				salesHistoryBOList.add( salesHistoryBO );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return salesHistoryBOList;
	}

	/*
	 * sale items table methods
	 */
	public int getLastInsertedSaleItemsId()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_SALE_ITEMS + " ORDER BY " + AppGlobal.SALE_ITEMS_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			return Integer.parseInt( cursor.getString( 0 ) );
		}
		db.close();
		return 0;
	}

	public void addSaleItems( SaleItemsBO saleItemsBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.SALE_ITEMS_ID, saleItemsBO.getId() );
		values.put( AppGlobal.SALE_ITEMS_SALE_ID, saleItemsBO.getSaleId() );
		values.put( AppGlobal.SALE_ITEMS_PRODUCT_ID, saleItemsBO.getProductId() );
		values.put( AppGlobal.SALE_ITEMS_PRODUCT_CODE, saleItemsBO.getProductCode() );
		values.put( AppGlobal.SALE_ITEMS_PRODUCT_NAME, saleItemsBO.getProductName() );
		values.put( AppGlobal.SALE_ITEMS_PRODUCT_UNIT, saleItemsBO.getProductUnit() );
		values.put( AppGlobal.SALE_ITEMS_TAX_RATE_ID, saleItemsBO.getTaxRateId() );
		values.put( AppGlobal.SALE_ITEMS_TAX, saleItemsBO.getTax() );
		values.put( AppGlobal.SALE_ITEMS_QUANTITY, saleItemsBO.getQuantity() );
		values.put( AppGlobal.SALE_ITEMS_UNIT_PRICE, saleItemsBO.getUnitPrice().toPlainString() );
		values.put( AppGlobal.SALE_ITEMS_GROSS_TOTAL, saleItemsBO.getGrossTotal().toPlainString() );
		values.put( AppGlobal.SALE_ITEMS_VAL_TAX, saleItemsBO.getValTax().toString() );
		values.put( AppGlobal.SALE_ITEMS_SERIAL_NO, saleItemsBO.getSerialNo() );
		values.put( AppGlobal.SALE_ITEMS_DISCOUNT_VAL, saleItemsBO.getDiscountVal().toPlainString() );
		values.put( AppGlobal.SALE_ITEMS_DISCOUNT, saleItemsBO.getDiscount() );
		values.put( AppGlobal.SALE_ITEMS_DISCOUNT_ID, saleItemsBO.getDiscountId() );

		long id = db.insert( AppGlobal.TABLE_SALE_ITEMS, null, values );
		db.close();
	}

	public List<SaleItemsBO> getAllSaleItems()
	{

		List<SaleItemsBO> saleItemsBOList = new ArrayList<SaleItemsBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SALE_ITEMS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SaleItemsBO saleItemsBO = new SaleItemsBO( cursor.getInt( 0 ), cursor.getInt( 1 ), cursor.getInt( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getInt( 6 ), cursor.getString( 7 ), cursor.getInt( 8 ), new BigDecimal( cursor.getString( 9 ) ), new BigDecimal( cursor.getString( 10 ) ), new BigDecimal( cursor.getString( 11 ) ), cursor.getString( 12 ), new BigDecimal( cursor.getString( 13 ) ), cursor.getString( 14 ), cursor.getInt( 15 ) );
				saleItemsBOList.add( saleItemsBO );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return saleItemsBOList;
	}

	public List<SaleItemsBO> getSaleItemsForId( int id )
	{

		List<SaleItemsBO> saleItemsBOList = new ArrayList<SaleItemsBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SALE_ITEMS + " WHERE " + AppGlobal.SALE_ITEMS_SALE_ID + "=" + id;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SaleItemsBO saleItemsBO = new SaleItemsBO( cursor.getInt( 0 ), cursor.getInt( 1 ), cursor.getInt( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getInt( 6 ), cursor.getString( 7 ), cursor.getInt( 8 ), new BigDecimal( cursor.getString( 9 ) ), new BigDecimal( cursor.getString( 10 ) ), new BigDecimal( cursor.getString( 11 ) ), cursor.getString( 12 ), new BigDecimal( cursor.getString( 13 ) ), cursor.getString( 14 ), cursor.getInt( 15 ) );
				saleItemsBOList.add( saleItemsBO );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return saleItemsBOList;
	}

	/*
	 * sale items history table methods
	 */
	public int getLastInsertedSaleItemsHistoryId()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_SALE_ITEMS_HISTORY + " ORDER BY " + AppGlobal.SALE_ITEMS_HISTORY_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			return Integer.parseInt( cursor.getString( 0 ) );
		}
		db.close();
		return 0;
	}

	public void addSaleItemsHistory( SaleItemsHistoryBO saleItemsHistoryBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.SALE_ITEMS_HISTORY_ID, saleItemsHistoryBO.getId() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_SALE_ITEMS_ID, saleItemsHistoryBO.getSaleItemId() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_SALE_ID, saleItemsHistoryBO.getSaleId() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_ID, saleItemsHistoryBO.getProductId() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_CODE, saleItemsHistoryBO.getProductCode() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_NAME, saleItemsHistoryBO.getProductName() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_PRODUCT_UNIT, saleItemsHistoryBO.getProductUnit() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_TAX_RATE_ID, saleItemsHistoryBO.getTaxRateId() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_TAX, saleItemsHistoryBO.getTax() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_QUANTITY, saleItemsHistoryBO.getQuantity() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_UNIT_PRICE, saleItemsHistoryBO.getUnitPrice().toPlainString() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_GROSS_TOTAL, saleItemsHistoryBO.getGrossTotal().toPlainString() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_VAL_TAX, saleItemsHistoryBO.getValTax().toPlainString() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_SERIAL_NO, saleItemsHistoryBO.getSerialNo() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_DISCOUNT_VAL, saleItemsHistoryBO.getDiscountVal().toPlainString() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_DISCOUNT, saleItemsHistoryBO.getDiscount() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_DISCOUNT_ID, saleItemsHistoryBO.getDiscountId() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_EVENT_TIME, saleItemsHistoryBO.getEventTime() );
		values.put( AppGlobal.SALE_ITEMS_HISTORY_ACTION, saleItemsHistoryBO.getAction() );

		long id = db.insert( AppGlobal.TABLE_SALE_ITEMS_HISTORY, null, values );
		db.close();
	}

	public List<SaleItemsHistoryBO> getAllSaleItemsHistory()
	{

		List<SaleItemsHistoryBO> saleItemsHistoryBOList = new ArrayList<SaleItemsHistoryBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SALE_ITEMS_HISTORY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SaleItemsHistoryBO saleItemsHistoryBO = new SaleItemsHistoryBO( cursor.getInt( 0 ), cursor.getInt( 1 ), cursor.getInt( 2 ), cursor.getInt( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getString( 6 ), cursor.getInt( 7 ), cursor.getString( 8 ), cursor.getInt( 9 ), new BigDecimal( cursor.getString( 10 ) ), new BigDecimal( cursor.getString( 11 ) ), new BigDecimal( cursor.getString( 12 ) ), cursor.getString( 13 ), new BigDecimal( cursor.getString( 14 ) ), cursor.getString( 15 ), cursor.getInt( 16 ), cursor.getString( 17 ), cursor.getString( 18 ) );
				saleItemsHistoryBOList.add( saleItemsHistoryBO );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return saleItemsHistoryBOList;
	}

	public List<SaleItemsHistoryBO> getSaleItemsHistoryForId( int id )
	{

		List<SaleItemsHistoryBO> saleItemsHistoryBOList = new ArrayList<SaleItemsHistoryBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SALE_ITEMS_HISTORY + " WHERE " + AppGlobal.SALE_ITEMS_HISTORY_SALE_ID + "=" + id;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SaleItemsHistoryBO saleItemsHistoryBO = new SaleItemsHistoryBO( cursor.getInt( 0 ), cursor.getInt( 1 ), cursor.getInt( 2 ), cursor.getInt( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getString( 6 ), cursor.getInt( 7 ), cursor.getString( 8 ), cursor.getInt( 9 ), new BigDecimal( cursor.getString( 10 ) ), new BigDecimal( cursor.getString( 11 ) ), new BigDecimal( cursor.getString( 12 ) ), cursor.getString( 13 ), new BigDecimal( cursor.getString( 14 ) ), cursor.getString( 15 ), cursor.getInt( 16 ), cursor.getString( 17 ), cursor.getString( 18 ) );
				saleItemsHistoryBOList.add( saleItemsHistoryBO );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return saleItemsHistoryBOList;
	}

	/*
	 * tax table methods
	 */

	public String updateTaxRate( TaxBO tax )
	{

		String selectQuery = "SELECT * FROM " + AppGlobal.TABLE_TAX_RATE;
		SQLiteDatabase db1 = this.getReadableDatabase();
		Cursor cursor = db1.rawQuery( selectQuery, null );

		if( cursor != null && cursor.getCount() > 0 )
			cursor.moveToFirst();

		TaxBO taxBO = new TaxBO( Integer.parseInt( cursor.getString( 0 ) ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ) );
		db1.close();

		if( !taxBO.getRate().equals( tax.getRate() ) )
		{
			SQLiteDatabase db = this.getReadableDatabase();
			ContentValues values = new ContentValues();

			values.put( AppGlobal.TAX_RATE_NAME, tax.getName() );
			values.put( AppGlobal.TAX_RATE_RATE, tax.getRate() );
			values.put( AppGlobal.TAX_RATE_TYPE, tax.getType() );
			values.put( AppGlobal.TAX_RATE_ID, tax.getTaxId() );
			// updating row
			db.update( AppGlobal.TABLE_TAX_RATE, values, AppGlobal.TAX_RATE_ID + " = ?", new String[] { String.valueOf( taxBO.getTaxId() ) } );
			db.close();
			return tax.getRate();
		}
		else
			return tax.getRate(); // Should be fixed...

	}

	public void addTaxRate( TaxBO taxBo )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put( AppGlobal.TAX_RATE_NAME, taxBo.getName() );
		values.put( AppGlobal.TAX_RATE_RATE, taxBo.getRate() );
		values.put( AppGlobal.TAX_RATE_TYPE, taxBo.getType() );
		values.put( AppGlobal.TAX_RATE_ID, taxBo.getTaxId() );
		db.insert( AppGlobal.TABLE_TAX_RATE, null, values );
		db.close(); // Closing database connection
	}

	public boolean isTaxRateExist()
	{

		String selectQuery = "SELECT * FROM " + AppGlobal.TABLE_TAX_RATE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		cursor.moveToFirst();
		int total = cursor.getCount();
		cursor.close();
		db.close();

		if( total > 0 )
			return true;
		return false;
	}

	public String getLastInsertedTaxRateRate()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_TAX_RATE + " ORDER BY " + AppGlobal.TAX_RATE_RATE + " DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();

			return cursor.getString( 2 );
		}
		db.close();
		return "0.0";
	}

	public int getLastInsertedTaxRateId()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_TAX_RATE + " ORDER BY " + AppGlobal.TAX_RATE_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();

			return Integer.parseInt( cursor.getString( 0 ) );
		}
		db.close();
		return 0;
	}

	/*
	 * suspended sales table methods
	 */

	public void addSuspendedSales( SuspendedSalesBO suspendedSalesBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.SUSPENDED_SALES_ID, suspendedSalesBO.getId() );
		values.put( AppGlobal.SUSPENDED_SALES_DATE, suspendedSalesBO.getSuspendProductDate() );
		values.put( AppGlobal.SUSPENDED_SALES_JSON, suspendedSalesBO.getSuspendProductJson() );

		long id = db.insert( AppGlobal.TABLE_SUSPENDED_SALES, null, values );
		db.close(); // Closing database connection
	}

	public List<SuspendedSalesBO> getAllSuspendedSales()
	{

		List<SuspendedSalesBO> suspendedSalesList = new ArrayList<SuspendedSalesBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SUSPENDED_SALES;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SuspendedSalesBO sP = new SuspendedSalesBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ) );
				suspendedSalesList.add( sP );
			}
			while ( cursor.moveToNext() );
		}
		db.close();
		return suspendedSalesList;
	}

	public void deletesuspendedSales( int id )
	{

		SQLiteDatabase db = this.getWritableDatabase();

		db.delete( AppGlobal.TABLE_SUSPENDED_SALES, AppGlobal.SUSPENDED_SALES_ID + " = ?", new String[] { String.valueOf( id ) } );
		db.close();
	}

	public int getLastInsertedSuspendedSalesId()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_SUSPENDED_SALES + " ORDER BY " + AppGlobal.SUSPENDED_SALES_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();

			return Integer.parseInt( cursor.getString( 0 ) );
		}
		db.close();
		return 0;
	}

	/*
	 * login table methods
	 */

	public void addLogin( LoginBO loginBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.USER_NAME, loginBO.getUsername() );
		values.put( AppGlobal.FNAME, loginBO.getFname() );
		values.put( AppGlobal.LNAME, loginBO.getLname() );
		values.put( AppGlobal.PHONE, loginBO.getPhone() );
		values.put( AppGlobal.EMAIL, loginBO.getEmail() );
		values.put( AppGlobal.PASSWORD, loginBO.getPassword() );
		values.put( AppGlobal.COMPANY, loginBO.getCompany() );
		values.put( AppGlobal.LOGIN_ID, loginBO.getLoginId() );

		db.insert( AppGlobal.TABLE_LOGIN, null, values );
		db.close(); // Closing database connection
	}

	/**
	 * @param id
	 * @return
	 */
	public LoginBO getLogin( int id )
	{

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query( AppGlobal.TABLE_LOGIN, new String[] { AppGlobal.LOGIN_ID, AppGlobal.USER_NAME, AppGlobal.PASSWORD, AppGlobal.EMAIL, AppGlobal.FNAME, AppGlobal.LNAME, AppGlobal.COMPANY, AppGlobal.PHONE }, AppGlobal.LOGIN_ID + "=?", new String[] { String.valueOf( id ) }, null, null, null, null );
		if( cursor != null )
			cursor.moveToFirst();

		LoginBO login = new LoginBO( Integer.parseInt( cursor.getString( 0 ) ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getString( 6 ), cursor.getString( 7 ) );
		db.close();
		return login;
	}

	/**
	 * @return
	 */
	public List<LoginBO> getAllLogins()
	{

		List<LoginBO> loginList = new ArrayList<LoginBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_LOGIN;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				LoginBO login = new LoginBO( Integer.parseInt( cursor.getString( 0 ) ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getString( 6 ), cursor.getString( 7 ) );
				loginList.add( login );
			}
			while ( cursor.moveToNext() );
		}
		db.close();
		return loginList;
	}

	/**
	 * @return
	 */
	public int getLoginCount()
	{

		String countQuery = "SELECT  * FROM " + AppGlobal.TABLE_LOGIN;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );

		int count = -1;

		if( cursor != null && !cursor.isClosed() )
		{
			count = cursor.getCount();
			cursor.close();
		}
		db.close();
		return count;
	}

	/**
	 * @return
	 */
	public LoginBO getLastInsertedLoginId()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_LOGIN + " ORDER BY " + AppGlobal.LOGIN_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();

			return new LoginBO( Integer.parseInt( cursor.getString( 0 ) ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getString( 6 ), cursor.getString( 7 ) );
		}
		db.close();
		return null;
	}

	/**
	 * @param login
	 * @return
	 */
	public int updateLogin( LoginBO login )
	{

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put( AppGlobal.USER_NAME, login.getUsername() );
		values.put( AppGlobal.FNAME, login.getFname() );
		values.put( AppGlobal.LNAME, login.getLname() );
		values.put( AppGlobal.PHONE, login.getPhone() );
		values.put( AppGlobal.EMAIL, login.getEmail() );
		values.put( AppGlobal.PASSWORD, login.getPassword() );
		values.put( AppGlobal.COMPANY, login.getCompany() );

		// updating row
		return db.update( AppGlobal.TABLE_LOGIN, values, AppGlobal.LOGIN_ID + " = ?", new String[] { String.valueOf( login.getLoginId() ) } );
	}

	/**
	 * @param login
	 */
	public void deleteLogin( LoginBO login )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete( AppGlobal.TABLE_LOGIN, AppGlobal.LOGIN_ID + " = ?", new String[] { String.valueOf( login.getLoginId() ) } );
		db.close();
	}

	/**
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean validateLogin( String email, String password )
	{

		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_LOGIN + " WHERE " + AppGlobal.EMAIL + " = '" + email + "' AND " + AppGlobal.PASSWORD + " = '" + password + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		if( cursor != null && cursor.getCount() > 0 )
		{
			db.close();
			return true;
		}
		db.close();
		return false;
	}

	public LoginBO getLoginDetailsAfterLogin(String email, String password)
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_LOGIN + " WHERE " + AppGlobal.EMAIL + " = '" + email + "' AND " + AppGlobal.PASSWORD + " = '" + password + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			db.close();
			return new LoginBO( Integer.parseInt( cursor.getString( 0 ) ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getString( 6 ), cursor.getString( 7 ) );
		}
		db.close();
		return null;
	}

	/**
	 * @param email
	 * @return
	 */
	public boolean isUserExist( String email )
	{

		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_LOGIN + " WHERE " + AppGlobal.EMAIL + " = '" + email + "'";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		if( cursor != null && cursor.getCount() > 0 )
		{
			db.close();
			return true;
		}
		db.close();
		return false;
	}

	/*
	 * Products Table Methods started
	 */

	public void addProduct( ProductBO productBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.PRODUCT_ID, productBO.getId() );
		values.put( AppGlobal.PRODUCT_CODE, productBO.getCode() );
		values.put( AppGlobal.PRODUCT_NAME, productBO.getName() );
		values.put( AppGlobal.PRODUCT_UNIT, productBO.getUnit() );
		values.put( AppGlobal.PRODUCT_SIZE, productBO.getSize() );
		values.put( AppGlobal.PRODUCT_COST, productBO.getCost().toPlainString() );
		values.put( AppGlobal.PRODUCT_PRICE, productBO.getPrice().toPlainString() );
		values.put( AppGlobal.PRODUCT_ALERT_QUALITY, productBO.getAlertQuality() );

		values.put( AppGlobal.PRODUCT_IMAGE, productBO.getImage() );
		values.put( AppGlobal.PRODUCT_CATEGORY_ID, productBO.getCategoryId() );
		values.put( AppGlobal.PRODUCT_SUB_CATEGORY_ID, productBO.getSubCategoryId() );
		values.put( AppGlobal.PRODUCT_QUANTITY, productBO.getQuantity() );
		values.put( AppGlobal.PRODUCT_TAX_RATE, productBO.getTaxRate().toPlainString() );
		values.put( AppGlobal.PRODUCT_TAX_QUANTITY, productBO.getTaxQuantity() );
		values.put( AppGlobal.PRODUCT_DETAILS, productBO.getDetails() );

		db.insert( AppGlobal.TABLE_PRODUCT, null, values );
		db.close(); // Closing database connection

	}

	/**
	 * @param id
	 * @return
	 */
	public ProductBO getProduct( int id )
	{

		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_PRODUCT + " WHERE " + AppGlobal.PRODUCT_ID + " = " + id;

		// SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );
		ProductBO product = null;
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			product = new ProductBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), new BigDecimal( cursor.getString( 5 ) ), new BigDecimal( cursor.getString( 6 ) ), cursor.getString( 7 ), cursor.getString( 8 ), cursor.getInt( 9 ), cursor.getInt( 10 ), cursor.getString( 11 ), new BigDecimal( cursor.getString( 12 ) ), cursor.getInt( 13 ), cursor.getString( 14 ) );
		}
		db.close();
		return product;
	}

	/**
	 * @return
	 */
	public List<ProductBO> getAllProducts()
	{

		List<ProductBO> productList = new ArrayList<ProductBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_PRODUCT;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				ProductBO product = new ProductBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), new BigDecimal( cursor.getString( 5 ) ), new BigDecimal( cursor.getString( 6 ) ), cursor.getString( 7 ), cursor.getString( 8 ), cursor.getInt( 9 ), cursor.getInt( 10 ), cursor.getString( 11 ), new BigDecimal( cursor.getString( 12 ) ), cursor.getInt( 13 ), cursor.getString( 14 ) );
				productList.add( product );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return productList;
	}

	/**
	 * @author Waqas Ahmed
	 * @return
	 */
	public int getProductCount()
	{

		String countQuery = "SELECT  * FROM " + AppGlobal.TABLE_PRODUCT;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );

		int count = -1;

		if( cursor != null && !cursor.isClosed() )
		{
			count = cursor.getCount();
			cursor.close();
		}
		db.close();
		return count;
	}

	/**
	 * @author Waqas Ahmed
	 * @return
	 */
	public ProductBO getLastInsertedProduct()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_PRODUCT + " ORDER BY " + AppGlobal.PRODUCT_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			return new ProductBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), new BigDecimal( cursor.getString( 5 ) ), new BigDecimal( cursor.getString( 6 ) ), cursor.getString( 7 ), cursor.getString( 8 ), cursor.getInt( 9 ), cursor.getInt( 10 ), cursor.getString( 11 ), new BigDecimal( cursor.getString( 12 ) ), cursor.getInt( 13 ), cursor.getString( 14 ) );
		}
		return null;
	}

	/**
	 * @author Waqas Ahmed
	 * @return
	 */
	public int getLastInsertedProductId()
	{

		if( this.getLastInsertedProduct() != null )
			return this.getLastInsertedProduct().getId();
		return -1;
	}

	/**
	 * @param product
	 * @return
	 */
	public int updateProduct( ProductBO product )
	{

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put( AppGlobal.PRODUCT_CODE, product.getCode() );
		values.put( AppGlobal.PRODUCT_NAME, product.getName() );
		values.put( AppGlobal.PRODUCT_UNIT, product.getUnit() );
		values.put( AppGlobal.PRODUCT_SIZE, product.getSize() );
		values.put( AppGlobal.PRODUCT_COST, product.getCost().toPlainString() );
		values.put( AppGlobal.PRODUCT_PRICE, product.getPrice().toPlainString() );
		values.put( AppGlobal.PRODUCT_ALERT_QUALITY, product.getAlertQuality() );

		values.put( AppGlobal.PRODUCT_IMAGE, product.getImage() );
		values.put( AppGlobal.PRODUCT_CATEGORY_ID, product.getCategoryId() );
		values.put( AppGlobal.PRODUCT_SUB_CATEGORY_ID, product.getSubCategoryId() );
		values.put( AppGlobal.PRODUCT_QUANTITY, product.getQuantity() );
		values.put( AppGlobal.PRODUCT_TAX_RATE, product.getTaxRate().toPlainString() );
		values.put( AppGlobal.PRODUCT_TAX_QUANTITY, product.getTaxQuantity() );
		values.put( AppGlobal.PRODUCT_DETAILS, product.getDetails() );
		// updating row
		return db.update( AppGlobal.TABLE_PRODUCT, values, AppGlobal.PRODUCT_ID + " = ?", new String[] { String.valueOf( product.getId() ) } );
	}

	/**
	 * @param product
	 */
	public void deleteProduct( ProductBO product )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete( AppGlobal.TABLE_PRODUCT, AppGlobal.PRODUCT_ID + " = ?", new String[] { String.valueOf( product.getId() ) } );
		db.close();
	}

	/**
	 * @param email
	 * @param password
	 * @return
	 *         //
	 */
	// public boolean validateLogin( String email, String password )
	// {
	//
	// String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_LOGIN +
	// " WHERE " + AppGlobal.EMAIL + " = '" + email + "' AND " +
	// AppGlobal.PASSWORD + " = '" + password + "'";
	// SQLiteDatabase db = this.getWritableDatabase();
	// Cursor cursor = db.rawQuery( selectQuery, null );
	//
	// if( cursor != null && !cursor.isClosed() )
	// {
	// if( cursor.moveToFirst() )
	// return true;
	// }
	// return false;
	// }

	/**
	 * @author Waqas Ahmed
	 * @param id
	 * @return {@link Boolean}
	 */
	public boolean isProductExist( int id )
	{

		return ( this.getProduct( id ) != null );

	}

	// category table methods started

	public void addCategory( CategoryBO categoryBO )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.CATEGORY_ID, categoryBO.getId() );
		values.put( AppGlobal.CATEGORY_CODE, categoryBO.getCode() );
		values.put( AppGlobal.CATEGORY_NAME, categoryBO.getName() );

		db.insert( AppGlobal.TABLE_CATEGORY, null, values );
		db.close(); // Closing database connection

	}

	/**
	 * @param id
	 * @return
	 */
	public CategoryBO getCategory( int id )
	{

		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_CATEGORY + " WHERE " + AppGlobal.CATEGORY_ID + " = " + id;

		// SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );
		CategoryBO category = null;
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			category = new CategoryBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ) );
		}
		db.close();
		return category;
	}

	/**
	 * @return
	 */
	public List<CategoryBO> getAllCategory()
	{

		List<CategoryBO> categoryList = new ArrayList<CategoryBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_CATEGORY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				CategoryBO category = new CategoryBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ) );
				categoryList.add( category );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return categoryList;
	}

	/**
	 * @author Waqas Ahmed
	 * @return
	 */
	public int getCategoryCount()
	{

		String countQuery = "SELECT  * FROM " + AppGlobal.TABLE_CATEGORY;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );

		int count = -1;

		if( cursor != null && !cursor.isClosed() )
		{
			count = cursor.getCount();
			cursor.close();
		}
		db.close();
		return count;
	}

	/**
	 * @author Waqas Ahmed
	 * @return
	 */
	public CategoryBO getLastInsertedCategory()
	{

		String countQuery = "SELECT * FROM " + AppGlobal.TABLE_CATEGORY + " ORDER BY " + AppGlobal.CATEGORY_ID + " DESC LIMIT 1";
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( countQuery, null );
		if( cursor != null && cursor.getCount() > 0 )
		{
			cursor.moveToFirst();
			return new CategoryBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ) );
		}
		return null;
	}

	/**
	 * @author Waqas Ahmed
	 * @return
	 */
	public int getLastInsertedCategoryId()
	{

		if( this.getLastInsertedCategory() != null )
			return this.getLastInsertedCategory().getId();
		return -1;
	}

	/**
	 * @param category
	 * @return
	 */
	public int updateCategory( CategoryBO category )
	{

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put( AppGlobal.CATEGORY_CODE, category.getCode() );
		values.put( AppGlobal.CATEGORY_NAME, category.getName() );
		// updating row
		return db.update( AppGlobal.TABLE_CATEGORY, values, AppGlobal.CATEGORY_ID + " = ?", new String[] { String.valueOf( category.getId() ) } );
	}

	/**
	 * @param category
	 */
	public void deleteCategory( CategoryBO category )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		db.delete( AppGlobal.TABLE_CATEGORY, AppGlobal.CATEGORY_ID + " = ?", new String[] { String.valueOf( category.getId() ) } );
		db.close();
	}

	/**
	 * @author Adeel
	 * @return
	 */
	public List<ProductBO> getProductswithCategoryId( int id )
	{

		List<ProductBO> productList = new ArrayList<ProductBO>();
		String selectQuery = "SELECT * FROM " + AppGlobal.TABLE_PRODUCT + " WHERE " + AppGlobal.CATEGORY_ID + " = " + id;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		if( cursor.moveToFirst() )
		{
			do
			{
				ProductBO product = new ProductBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), new BigDecimal( cursor.getString( 5 ) ), new BigDecimal( cursor.getString( 6 ) ), cursor.getString( 7 ), cursor.getString( 8 ), cursor.getInt( 9 ), cursor.getInt( 10 ), cursor.getString( 11 ), new BigDecimal( cursor.getString( 12 ) ), cursor.getInt( 13 ), cursor.getString( 14 ) );
				productList.add( product );
			}
			while ( cursor.moveToNext() );

		}
		db.close();
		return productList;

	}

	/**
	 * Feedback Table Methods ended
	 */

}

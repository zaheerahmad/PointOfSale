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

		String CREATE_SUSPEND_PRODUCT_TABLE = "CREATE TABLE " + AppGlobal.TABLE_SUSPEND_PRODUCT + "(" + AppGlobal.SUSPEND_PRODUCT_ID + " INTEGER," + AppGlobal.SUSPEND_PRODUCT_DATE + " TEXT," + AppGlobal.SUSPEND_PRODUCT_JSON + " TEXT)";
		db.execSQL( CREATE_SUSPEND_PRODUCT_TABLE );
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

		db.execSQL( "DROP TABLE IF EXISTS " + AppGlobal.TABLE_SUSPEND_PRODUCT );
		// Create tables again
		onCreate( db );

	}

	/**
	 * @author Adeel
	 * @return
	 */
	public void addSuspendProduct( String suspendProductJson, String suspendProductDate )
	{

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put( AppGlobal.SUSPEND_PRODUCT_DATE, suspendProductDate );
		values.put( AppGlobal.SUSPEND_PRODUCT_JSON, suspendProductJson );

		db.insert( AppGlobal.TABLE_SUSPEND_PRODUCT, null, values );
		db.close(); // Closing database connection
	}

	/**
	 * @author Adeel
	 * @return
	 */
	public List<SuspendProductBO> getAllSuspendProduct()
	{

		List<SuspendProductBO> suspendProductList = new ArrayList<SuspendProductBO>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + AppGlobal.TABLE_SUSPEND_PRODUCT;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery( selectQuery, null );

		// looping through all rows and adding to list
		if( cursor.moveToFirst() )
		{
			do
			{
				SuspendProductBO sP = new SuspendProductBO( cursor.getInt( 0 ), cursor.getString( 1 ), cursor.getString( 2 ) );
				suspendProductList.add( sP );
			}
			while ( cursor.moveToNext() );
		}
		db.close();
		return suspendProductList;
	}

	/**
	 * Feedback Table Methods started
	 * 
	 * @param loginBO
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

	/**
	 * Feedback Table Methods ended
	 */

	// Feedback Table Methods started

	/**
	 * 
	 * 
	 * @param productBO
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

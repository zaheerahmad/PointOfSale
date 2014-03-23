package com.fusepos.datalayer;

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
		// Create tables again
		onCreate( db );

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
		return login;
	}

	/**
	 * @return
	 */
	public List<LoginBO> getAllFeedbacks()
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
		if( cursor != null )
			cursor.moveToFirst();

		return new LoginBO( Integer.parseInt( cursor.getString( 0 ) ), cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getString( 4 ), cursor.getString( 5 ), cursor.getString( 6 ), cursor.getString( 7 ) );
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

		if( cursor != null && !cursor.isClosed() )
		{
			if( cursor.moveToFirst() )
				return true;
		}
		return false;
	}

	/**
	 * @param email
	 * @return
	 */
	public boolean isUserExist( String email )
	{

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query( AppGlobal.TABLE_LOGIN, new String[] { AppGlobal.LOGIN_ID, AppGlobal.USER_NAME, AppGlobal.PASSWORD, AppGlobal.EMAIL, AppGlobal.FNAME, AppGlobal.LNAME, AppGlobal.COMPANY, AppGlobal.PHONE }, AppGlobal.EMAIL + "=?", new String[] { email }, null, null, null, null );

		if( cursor != null && !cursor.isClosed() )
		{
			if( cursor.moveToFirst() )
				return true;
		}
		return false;
	}

	/**
	 * Feedback Table Methods ended
	 */
}

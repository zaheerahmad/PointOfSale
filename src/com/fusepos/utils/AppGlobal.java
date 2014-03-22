package com.fusepos.utils;

public class AppGlobal
{
	public static final boolean	isDebugMode											= false;
	public static final boolean	shouldMaintainLogOfFeeds							= true;

	// Response Statuses
	public static final int		RESPONSE_STATUS_FAIL								= 1;
	public static final String	RESPONSE_STATUS_FAIL_MESSAGE						= "Some error occured while processing request.";

	public static final int		RESPONSE_STATUS_SUCCESS								= 2;
	public static final String	RESPONSE_STATUS_SUCCESS_MESSAGE						= "Request succefully completed!";

	public static final int		RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION			= 3;
	public static final String	RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION_MESSAGE	= "No network connection found!";

	public static final int		RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION				= 4;
	public static final String	RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION_MESSAGE		= "Some random error occured, please try again later!";

	/**
	 * Databases constants
	 */

	/**
	 * Databases constants ended
	 */

	// Activity Toast Messages
	public static final String	TOAST_MISSING_MANDATORY_FIELDS						= "Couldn't process request, \nSome mandatory fields were missing";
	public static final String	TOAST_THANKS_FOR_FEEDBACK							= "Thank you for your feedback!";
	public static final String	TOAST_INTERNET_CONNECTION_FOUND						= "Internet connection on your device found! Uploading your data on server";
	public static final String	TOAST_DATA_UPLOADED_SUCCESSFULLY					= "Data has been uploaded successfully on server!";
	public static final String	TOAST_DATA_SAVED									= "Data has been saved successfully!";

	// DataFetcher Actions
	public static final String	DATAFETCHER_ACTION_INSERT_FEEDBACK					= "_insert_feedback_";

	// Broadcast Filters
	public static final String	BROADCAST_FILTER_INTERNET_CONNECTION_FOUND			= "_internet_connection_found_";

	public static final String	AlchemyAPI_Key										= "3bb77eb7d9347bdd4807989f1e9876de91e2cb67";

	public static final long	SERVICE_DELAY										= 2 * 60 * 1000;

	public static final String	APP_PREF_IS_DATA_DIRTY								= "_isDataDirty_";
	public static final String	APP_PREF_LAST_INSERTED_ID							= "_lastInsertedId_";
	public static final String	APP_PREF_NAME										= "RECEPTIONIST_APP_PREFS";
	public static final String	APP_PREF_HOST_NAME									= "_host_name_";
	public static final String	APP_PREF_DB_NAME									= "_db_name_";
	public static final String	APP_PREF_DB_USERNAME								= "_db_username_";
	public static final String	APP_PREF_DB_PASSWORD								= "_db_password_";
	public static final String	APP_PREF_USERNAME									= "_username_";
	public static final String	APP_PREF_PASSWORD									= "_password_";

}

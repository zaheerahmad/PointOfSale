package com.fusepos.utils;

/**
 * @author Zaheer Ahmad
 * 
 */
public class AppGlobal
{
	public static final boolean	isDebugMode											= false;
	public static final boolean	shouldMaintainLogOfFeeds							= false;

	// Response Statuses
	public static final int		RESPONSE_STATUS_FAIL								= 1;
	public static final String	RESPONSE_STATUS_FAIL_MESSAGE						= "Some error occured while processing request.";

	public static final int		RESPONSE_STATUS_SUCCESS								= 2;
	public static final String	RESPONSE_STATUS_SUCCESS_MESSAGE						= "Request succefully completed!";

	public static final int		RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION			= 3;
	public static final String	RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION_MESSAGE	= "No network connection found!";

	public static final int		RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION				= 4;
	public static final String	RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION_MESSAGE		= "Some random error occured, please try again later!";

	public static final int		RESPONSE_STATUS_db_connection_failed				= -201;
	public static final int		RESPONSE_STATUS_request_success						= 200;
	public static final int		RESPONSE_STATUS_request_success_but_none_found		= -200;

	/**
	 * Databases constants
	 */

	public static final String	DATABASE_NAME										= "POS_DB";
	public static final int		DATABASE_VERSION									= 1;

	public static final String	TABLE_LOGIN											= "tbl_login";
	public static final String	LOGIN_ID											= "loginId";
	public static final String	USER_NAME											= "username";
	public static final String	EMAIL												= "email";
	public static final String	PASSWORD											= "password";
	public static final String	FNAME												= "fname";
	public static final String	LNAME												= "lname";
	public static final String	COMPANY												= "company";
	public static final String	PHONE												= "phone";

	// for Product table
	public static final String	TABLE_PRODUCT										= "tbl_product";
	public static final String	PRODUCT_ID											= "productId";
	public static final String	PRODUCT_CODE										= "code";
	public static final String	PRODUCT_NAME										= "name";
	public static final String	PRODUCT_UNIT										= "unit";
	public static final String	PRODUCT_SIZE										= "size";
	public static final String	PRODUCT_COST										= "cost";
	public static final String	PRODUCT_PRICE										= "price";
	public static final String	PRODUCT_ALERT_QUALITY								= "alertquality";
	public static final String	PRODUCT_IMAGE										= "image";
	public static final String	PRODUCT_CATEGORY_ID									= "categoryId";
	public static final String	PRODUCT_SUB_CATEGORY_ID								= "subcategoryId";
	public static final String	PRODUCT_QUANTITY									= "quantity";
	public static final String	PRODUCT_TAX_RATE									= "taxrate";
	public static final String	PRODUCT_TAX_QUANTITY								= "taxquantity";
	public static final String	PRODUCT_DETAILS										= "details";
	/**
	 * Databases constants ended
	 */

	// Activity Toast Messages
	public static final String	TOAST_MISSING_MANDATORY_FIELDS						= "Couldn't process request, \nSome mandatory fields were missing";
	public static final String	TOAST_THANKS_FOR_FEEDBACK							= "Thank you for your feedback!";
	public static final String	TOAST_INTERNET_CONNECTION_FOUND						= "Internet connection on your device found! Uploading your data on server";
	public static final String	TOAST_DATA_UPLOADED_SUCCESSFULLY					= "Data has been uploaded successfully on server!";
	public static final String	TOAST_DATA_SAVED									= "Data has been saved successfully!";
	public static final String	TOAST_PLEASE_WAIT									= "Please wait...";

	// DataFetcher Actions
	public static final String	DATAFETCHER_ACTION_INSERT_FEEDBACK					= "_insert_feedback_";
	public static final String	DATAFETCHER_ACTION_LOGIN_USER						= "_login_user_";
	public static final String	DATAFETCHER_ACTION_PRODUCTS_SYNC					= "_product_sync_";

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

	public static final String	SERVER_URL_LOGIN_WEBSERVICE							= "http://fusepos.com/zaheer/AndroidAppWebservices/LoginWebService.php";
	public static final String	SERVER_URL_GET_ALL_PRODUCT_WEBSERVICE				= "http://fusepos.com/zaheer/AndroidAppWebservices/GetAllProductsAfterIdWebService.php";

	public static final String	PARAM_DB_HOST										= "host";
	public static final String	PARAM_DB_USER										= "uDB";
	public static final String	PARAM_DB_PASSWPRD									= "pwddb";
	public static final String	PARAM_DB_NAME										= "db";

	public static final String	PARAM_DB_HOST_DEFAULT_VALUE							= "localhost";

	public static final String	PARAM_LOGIN_USERNAME								= "un";
	public static final String	PARAM_LOGIN_PASSWORD								= "pwd";
	public static final String	PARAM_GETALLPRODUCTS_ID								= "id";

}

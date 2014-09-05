package com.fusepos.utils;

import java.text.DecimalFormat;

/**
 * @author Zaheer Ahmad
 * 
 */
public class AppGlobal
{
	public static final boolean	isDebugMode											= false;
	public static final boolean	shouldMaintainLogOfFeeds							= false;
	public static final boolean	isPaymentDone										= true;
	public static final String	BROADCAST_CLOUD										= "_broadcast_cloud";

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

	public static final String	PAYLEVEN_API_KEY									= "9f71f167318143bdae4fdd9822cca25c";

	/**
	 * Databases constants
	 */

	public static final String	DATABASE_NAME										= "POS_DB";
	public static final int		DATABASE_VERSION									= 1;

	public static final String	SYNC												= "sync";
	public static final String	UNSYNC												= "unsync";
	public static final String	DELETE_FROM_CLIENT									= "deleteFromClient";
	public static final String	DELETE_FROM_SERVER									= "deleteFromServer";

	// for login table
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

	// for category table
	public static final String	TABLE_CATEGORY										= "tbl_category";
	public static final String	CATEGORY_ID											= "categoryId";
	public static final String	CATEGORY_CODE										= "code";
	public static final String	CATEGORY_NAME										= "name";

	// for suspended sales table
	public static final String	TABLE_SUSPENDED_SALES								= "tbl_suspendSales";
	public static final String	SUSPENDED_SALES_ID									= "suspendedSalesId";
	public static final String	SUSPENDED_SALES_SUSPENDED_ID						= "suspendedId";
	public static final String	SUSPENDED_SALES_PRODUCT_ID							= "productId";
	public static final String	SUSPENDED_SALES_PRODUCT_CODE						= "productCode";
	public static final String	SUSPENDED_SALES_PRODUCT_NAME						= "productName";
	public static final String	SUSPENDED_SALES_PRODUCT_UNIT						= "productUnit";
	public static final String	SUSPENDED_SALES_TAX_RATE_ID							= "taxRateId";
	public static final String	SUSPENDED_SALES_TAX									= "tax";
	public static final String	SUSPENDED_SALES_QUANTITY							= "quantity";
	public static final String	SUSPENDED_SALES_UNIT_PRICE							= "unitPrice";
	public static final String	SUSPENDED_SALES_GROSS_TOTAL							= "grossTotal";
	public static final String	SUSPENDED_SALES_VAL_TAX								= "valTax";
	public static final String	SUSPENDED_SALES_DISCOUNT							= "discount";
	public static final String	SUSPENDED_SALES_DISCOUNT_ID							= "discountId";
	public static final String	SUSPENDED_SALES_DISCOUNT_VAL						= "discountVal";
	public static final String	SUSPENDED_SALES_SERIAL_NO							= "serialNo";
	public static final String	SUSPENDED_SALES_DATE								= "date";
	public static final String	SUSPENDED_SALES_STATUS								= "status";

	// for suspended bills table
	public static final String	TABLE_SUSPENDED_BILLS								= "tbl_suspendedBills";
	public static final String	SUSPENDED_BILLS_ID									= "suspendedBillsId";
	public static final String	SUSPENDED_BILLS_DATE								= "date";
	public static final String	SUSPENDED_BILLS_CUSTOMER_ID							= "customerId";
	public static final String	SUSPENDED_BILLS_COUNT								= "count";
	public static final String	SUSPENDED_BILLS_TAX_1								= "tax1";
	public static final String	SUSPENDED_BILLS_TAX_2								= "tax2";
	public static final String	SUSPENDED_BILLS_DISCOUNT							= "discount";
	public static final String	SUSPENDED_BILLS_INV_TOTAL							= "invTotal";
	public static final String	SUSPENDED_BILLS_TOTAL								= "total";
	public static final String	SUSPENDED_BILLS_STATUS								= "status";
	public static final String	SUSPENDED_BILLS_SERVER_ID							= "server_Id";

	// for tax table
	public static final String	TABLE_TAX_RATE										= "tbl_taxRate";
	public static final String	TAX_RATE_ID											= "taxId";
	public static final String	TAX_RATE_NAME										= "name";
	public static final String	TAX_RATE_RATE										= "rate";
	public static final String	TAX_RATE_TYPE										= "type";

	// for sales table
	public static final String	TABLE_SALES											= "tbl_sales";
	public static final String	SALES_ID											= "salesId";
	public static final String	SALES_REFERENCE_NO									= "referenceNo";
	public static final String	SALES_WAREHOUSE_ID									= "warehouseId";
	public static final String	SALES_BILLER_ID										= "billerId";
	public static final String	SALES_BILLER_NAME									= "billerName";
	public static final String	SALES_CUSTOMER_ID									= "customerId";
	public static final String	SALES_CUSTOMER_NAME									= "customerName";
	public static final String	SALES_DATE											= "date";
	public static final String	SALES_NOTE											= "note";
	public static final String	SALES_INTERNAL_NOTE									= "internalNote";
	public static final String	SALES_INV_TOTAL										= "invTotal";
	public static final String	SALES_TOTAL_TAX										= "totalTax";
	public static final String	SALES_TOTAL											= "total";
	public static final String	SALES_INVOICE_TYPE									= "invoiceType";
	public static final String	SALES_IN_TYPE										= "inType";
	public static final String	SALES_TOTAL_TAX2									= "totalTax2";
	public static final String	SALES_TAX_RATE2_ID									= "taxRate2Id";
	public static final String	SALES_INV_DISCOUNT									= "invDiscount";
	public static final String	SALES_DISCOUNT_ID									= "discountId";
	public static final String	SALES_USER											= "user";
	public static final String	SALES_UPDATED_BY									= "updatedBy";
	public static final String	SALES_PAID_BY										= "paidBy";
	public static final String	SALES_COUNT											= "count";
	public static final String	SALES_SHIPPING										= "shipping";
	public static final String	SALES_POS											= "pos";
	public static final String	SALES_PAID											= "paid";
	public static final String	SALES_CC_NO											= "ccNo";
	public static final String	SALES_CC_HOLDER										= "ccHolder";
	public static final String	SALES_CHECQUE_NO									= "checqueNo";
	public static final String	SALES_STATUS										= "status";

	// for sales history table
	public static final String	TABLE_SALES_HISTORY									= "tbl_salesHistory";
	public static final String	SALES_HISTORY_ID									= "salesHistoryId";
	public static final String	SALES_HISTORY_SALES_ID								= "salesId";
	public static final String	SALES_HISTORY_REFERENCE_NO							= "referenceNo";
	public static final String	SALES_HISTORY_WAREHOUSE_ID							= "warehouseId";
	public static final String	SALES_HISTORY_BILLER_ID								= "billerId";
	public static final String	SALES_HISTORY_BILLER_NAME							= "billerName";
	public static final String	SALES_HISTORY_CUSTOMER_ID							= "customerId";
	public static final String	SALES_HISTORY_CUSTOMER_NAME							= "customerName";
	public static final String	SALES_HISTORY_DATE									= "date";
	public static final String	SALES_HISTORY_NOTE									= "note";
	public static final String	SALES_HISTORY_INTERNAL_NOTE							= "internalNote";
	public static final String	SALES_HISTORY_INV_TOTAL								= "invTotal";
	public static final String	SALES_HISTORY_TOTAL_TAX								= "totalTax";
	public static final String	SALES_HISTORY_TOTAL									= "total";
	public static final String	SALES_HISTORY_INVOICE_TYPE							= "invoiceType";
	public static final String	SALES_HISTORY_IN_TYPE								= "inType";
	public static final String	SALES_HISTORY_TOTAL_TAX2							= "totalTax2";
	public static final String	SALES_HISTORY_TAX_RATE2_ID							= "taxRate2Id";
	public static final String	SALES_HISTORY_INV_DISCOUNT							= "invDiscount";
	public static final String	SALES_HISTORY_DISCOUNT_ID							= "discountId";
	public static final String	SALES_HISTORY_USER									= "user";
	public static final String	SALES_HISTORY_UPDATED_BY							= "updatedBy";
	public static final String	SALES_HISTORY_PAID_BY								= "paidBy";
	public static final String	SALES_HISTORY_COUNT									= "count";
	public static final String	SALES_HISTORY_SHIPPING								= "shipping";
	public static final String	SALES_HISTORY_POS									= "pos";
	public static final String	SALES_HISTORY_PAID									= "paid";
	public static final String	SALES_HISTORY_CC_NO									= "ccNo";
	public static final String	SALES_HISTORY_CC_HOLDER								= "ccHolder";
	public static final String	SALES_HISTORY_CHECQUE_NO							= "checqueNo";
	public static final String	SALES_HISTORY_EVENT_TIME							= "eventTime";
	public static final String	SALES_HISTORY_ACTION								= "action";
	public static final String	SALES_HISTORY_STATUS								= "status";
	// for sale_items
	public static final String	TABLE_SALE_ITEMS									= "tbl_saleItems";
	public static final String	SALE_ITEMS_ID										= "saleItemsId";
	public static final String	SALE_ITEMS_SALE_ID									= "salesId";
	public static final String	SALE_ITEMS_PRODUCT_ID								= "productId";
	public static final String	SALE_ITEMS_PRODUCT_CODE								= "productCode";
	public static final String	SALE_ITEMS_PRODUCT_NAME								= "productName";
	public static final String	SALE_ITEMS_PRODUCT_UNIT								= "productUnit";
	public static final String	SALE_ITEMS_TAX_RATE_ID								= "taxRateId";
	public static final String	SALE_ITEMS_TAX										= "tax";
	public static final String	SALE_ITEMS_QUANTITY									= "quantity";
	public static final String	SALE_ITEMS_UNIT_PRICE								= "unitPrice";
	public static final String	SALE_ITEMS_GROSS_TOTAL								= "grossTotal";
	public static final String	SALE_ITEMS_VAL_TAX									= "valTax";
	public static final String	SALE_ITEMS_SERIAL_NO								= "serialNo";
	public static final String	SALE_ITEMS_DISCOUNT_VAL								= "discountVal";
	public static final String	SALE_ITEMS_DISCOUNT									= "discount";
	public static final String	SALE_ITEMS_DISCOUNT_ID								= "discountId";
	public static final String	SALE_ITEMS_STATUS									= "status";
	// for sale_items_history
	public static final String	TABLE_SALE_ITEMS_HISTORY							= "tbl_saleItemsHistory";
	public static final String	SALE_ITEMS_HISTORY_ID								= "saleItemsHistoryId";
	public static final String	SALE_ITEMS_HISTORY_SALE_ITEMS_ID					= "saleItemId";
	public static final String	SALE_ITEMS_HISTORY_SALE_ID							= "saleId";
	public static final String	SALE_ITEMS_HISTORY_PRODUCT_ID						= "productId";
	public static final String	SALE_ITEMS_HISTORY_PRODUCT_CODE						= "productCode";
	public static final String	SALE_ITEMS_HISTORY_PRODUCT_NAME						= "productName";
	public static final String	SALE_ITEMS_HISTORY_PRODUCT_UNIT						= "productUnit";
	public static final String	SALE_ITEMS_HISTORY_TAX_RATE_ID						= "taxRateId";
	public static final String	SALE_ITEMS_HISTORY_TAX								= "tax";
	public static final String	SALE_ITEMS_HISTORY_QUANTITY							= "quantity";
	public static final String	SALE_ITEMS_HISTORY_UNIT_PRICE						= "unitPrice";
	public static final String	SALE_ITEMS_HISTORY_GROSS_TOTAL						= "grossTotal";
	public static final String	SALE_ITEMS_HISTORY_VAL_TAX							= "valTax";
	public static final String	SALE_ITEMS_HISTORY_SERIAL_NO						= "serialNo";
	public static final String	SALE_ITEMS_HISTORY_DISCOUNT_VAL						= "discountVal";
	public static final String	SALE_ITEMS_HISTORY_DISCOUNT							= "discount";
	public static final String	SALE_ITEMS_HISTORY_DISCOUNT_ID						= "discountId";
	public static final String	SALE_ITEMS_HISTORY_EVENT_TIME						= "eventTime";
	public static final String	SALE_ITEMS_HISTORY_ACTION							= "action";
	public static final String	SALE_ITEMS_HISTORY_STATUS							= "status";
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
	public static final String	DATAFETCHER_ACTION_GET_TAX_RATE						= "_tax_rate_";

	// Broadcast Filters
	public static final String	BROADCAST_FILTER_INTERNET_CONNECTION_FOUND			= "_internet_connection_found_";

	public static final String	AlchemyAPI_Key										= "3bb77eb7d9347bdd4807989f1e9876de91e2cb67";

	public static final long	SERVICE_DELAY										= 1 * 60 * 1000;

	public static final String	APP_PREF_IS_DATA_DIRTY								= "_isDataDirty_";
	public static final String	APP_PREF_LAST_INSERTED_ID							= "_lastInsertedId_";
	public static final String	APP_PREF_NAME										= "FUSEPOS_APP_PREFS";
	public static final String	APP_PREF_HOST_NAME									= "_host_name_";
	public static final String	APP_PREF_DB_NAME									= "_db_name_";
	public static final String	APP_PREF_DB_USERNAME								= "_db_username_";
	public static final String	APP_PREF_DB_PASSWORD								= "_db_password_";
	public static final String	APP_PREF_USERNAME									= "_username_";
	public static final String	APP_PREF_PASSWORD									= "_password_";
	public static final String	APP_PREF_TIMER										= "timer";
	public static final String	APP_PREF_FIRST_NAME									= "_first_name_";
	public static final String	APP_PREF_LAST_NAME									= "_last_name_";

	// Web Service links
	public static final String	SERVER_URL_UPDATE_SALES_DATA_WEBSERVICE				= "http://fusepos.com/zaheer/AndroidAppWebservices/SyncSalesWebService.php";
	public static final String	SERVER_URL_UPDATE_SUSPENDED_SALES_WEBSERVICE		= "http://fusepos.com/zaheer/AndroidAppWebservices/SyncSuspendedSalesWebService.php";
	public static final String	SERVER_URL_LOGIN_WEBSERVICE							= "http://fusepos.com/zaheer/AndroidAppWebservices/LoginWebService.php";
	public static final String	SERVER_URL_GET_ALL_PRODUCT_WEBSERVICE				= "http://fusepos.com/zaheer/AndroidAppWebservices/GetAllProductsAfterIdWebService.php";
	public static final String	SERVER_URL_GET_TAX_RATE_WEBSERVICE					= "http://fusepos.com/zaheer/AndroidAppWebservices/GetTaxRateWebService.php";
	public static final String	PARAM_DB_HOST										= "host";
	public static final String	PARAM_DB_USER										= "uDB";
	public static final String	PARAM_DB_PASSWPRD									= "pwddb";
	public static final String	PARAM_DB_NAME										= "db";

	public static final String	PARAM_DB_HOST_DEFAULT_VALUE							= "localhost";

	public static final String	PARAM_LOGIN_USERNAME								= "un";
	public static final String	PARAM_LOGIN_PASSWORD								= "pwd";
	public static final String	PARAM_GETALLPRODUCTS_ID								= "id";

}

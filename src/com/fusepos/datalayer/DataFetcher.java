package com.fusepos.datalayer;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.fusepos.service.DataSendService;
import com.fusepos.service.ServiceHandler;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.Utils;
import com.fusepos.wrapper.CategoryWrapper;
import com.fusepos.wrapper.IAsyncTask;
import com.fusepos.wrapper.LoginServiceResponseWrapper;
import com.fusepos.wrapper.LoginWrapper;
import com.fusepos.wrapper.ProductServiceResponseWrapper;
import com.fusepos.wrapper.ProductWrapper;
import com.fusepos.wrapper.ResponseStatusWrapper;
import com.fusepos.wrapper.ResponseSuspendedSaleWrapper;
import com.fusepos.wrapper.ServerResponseSuspendedSaleWrapper;
import com.fusepos.wrapper.ServerResponseWrapper;
import com.fusepos.wrapper.SuspendedSyncResponseWrapper;
import com.fusepos.wrapper.TaxServiceResponseWrapper;
import com.fusepos.wrapper.TaxWrapper;
import com.google.gson.Gson;

/**
 * @author Zaheer Ahmad
 * 
 */
public class DataFetcher extends AsyncTask<String, String, ResponseStatusWrapper>
{

	IAsyncTask	async;
	Context		context;
	boolean		hasInternet	= false;

	public DataFetcher( IAsyncTask asyncTask, Context context )
	{

		this.async = asyncTask;
		this.context = context;

	}

	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute()
	{

		/**
		 * Check If Network exists, otherwise return immediately.
		 */
		if( Utils.hasInternetConnection( this.context ) )
		{
			Utils.isSynchronizing = true;

			this.hasInternet = true;
			async.doWait();
		}
		else
			Utils.isSynchronizing = false;
		/**
		 * Network checking ended.
		 */
	}

	static int		lastIndexS		= -1;
	static boolean	isFileWritten	= false;

	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected synchronized ResponseStatusWrapper doInBackground( String... params )
	{

		Gson gson = new Gson();
		ServiceHandler sh = new ServiceHandler();
		InputStream inputStream = null;

		SharedPreferences pref = Utils.getSharedPreferences( context );

		String host = AppGlobal.PARAM_DB_HOST_DEFAULT_VALUE;
		String dbName = pref.getString( AppGlobal.APP_PREF_DB_NAME, "" );
		String dbUser = pref.getString( AppGlobal.APP_PREF_DB_USERNAME, "" );
		String dbPassword = pref.getString( AppGlobal.APP_PREF_DB_PASSWORD, "" );

		try
		{
			if( !this.hasInternet )
			{
				ResponseStatusWrapper response = new ResponseStatusWrapper();

				response.status = AppGlobal.RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION;
				response.message = AppGlobal.RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION_MESSAGE;
				return response;
			}

			if( params != null && params[0].equalsIgnoreCase( AppGlobal.DATAFETCHER_ACTION_LOGIN_USER ) )
			{
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>( 1 );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_HOST, host ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_NAME, dbName ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_USER, dbUser ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_PASSWPRD, dbPassword ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_LOGIN_USERNAME, params[1] ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_LOGIN_PASSWORD, params[2] ) );

				inputStream = sh.makeServiceCall( AppGlobal.SERVER_URL_LOGIN_WEBSERVICE, nameValuePairs );
				Reader reader = new InputStreamReader( inputStream );

				// LoginServiceResponseWrapper loginResponseWrapper =
				// gson.fromJson( reader, LoginServiceResponseWrapper.class );
				LoginServiceResponseWrapper serverResponseWrapper = gson.fromJson( reader, LoginServiceResponseWrapper.class );
				if( serverResponseWrapper != null )
				{

					if( serverResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_db_connection_failed )
					{
						ResponseStatusWrapper response = new ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_db_connection_failed;
						response.message = serverResponseWrapper.getMessage();
						return response;
					}
					else if( serverResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success_but_none_found )
					{
						ResponseStatusWrapper response = new ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_request_success_but_none_found;
						response.message = serverResponseWrapper.getMessage();
						return response;
					}

					else if( serverResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success )
					{

						if( serverResponseWrapper != null )
						{
							LoginWrapper loginWrapper = serverResponseWrapper.getResponse();

							LoginBO loginBO = new LoginBO( Integer.parseInt( loginWrapper.getId() ), loginWrapper.getUsername(), loginWrapper.getFirstName(), loginWrapper.getLastName(), loginWrapper.getPassword(), loginWrapper.getEmail(), loginWrapper.getCompany(), loginWrapper.getPhone() );
							SharedPreferences pref1 = Utils.getSharedPreferences( context );
							pref1.edit().putString( AppGlobal.APP_PREF_FIRST_NAME, loginWrapper.getFirstName() ).commit();
							pref1.edit().putString( AppGlobal.APP_PREF_LAST_NAME, loginWrapper.getLastName() ).commit();

							DatabaseHandler db = new DatabaseHandler( context, AppGlobal.TABLE_LOGIN );
							db.addLogin( loginBO );
							db.close();

							ResponseStatusWrapper response = new ResponseStatusWrapper();

							response.status = AppGlobal.RESPONSE_STATUS_request_success;
							response.message = serverResponseWrapper.getMessage();
							return response;
						}
					}
				}
			}

			if( params != null && params[0].equalsIgnoreCase( AppGlobal.DATAFETCHER_ACTION_PRODUCTS_SYNC ) )
			{
				ResponseStatusWrapper response = new ResponseStatusWrapper();
				URL url;
				HttpURLConnection httpUrlConnection;
				DataOutputStream outputStream;
				InputStream responseStream;
				Reader saleUpdateReader;
				SalesDataBO salesDataBO;

				List<SalesDataBO> salesDataBOList = new ArrayList<SalesDataBO>();
				List<SalesBO> salesBOList = new ArrayList<SalesBO>();
				List<SalesHistoryBO> salesHistoryBOList = new ArrayList<SalesHistoryBO>();
				List<SaleItemsBO> saleItemsBOList = new ArrayList<SaleItemsBO>();
				List<SaleItemsHistoryBO> saleItemsHistoryBOList = new ArrayList<SaleItemsHistoryBO>();

				DatabaseHandler db = new DatabaseHandler( context, AppGlobal.TABLE_PRODUCT );

				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>( 1 );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_HOST, host ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_NAME, dbName ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_USER, dbUser ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_PASSWPRD, dbPassword ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_GETALLPRODUCTS_ID, String.valueOf( db.getLastInsertedProductId() ) ) );

				inputStream = sh.makeServiceCall( AppGlobal.SERVER_URL_GET_ALL_PRODUCT_WEBSERVICE, nameValuePairs );
				Reader reader = new InputStreamReader( inputStream );

				// LoginServiceResponseWrapper loginResponseWrapper =
				// gson.fromJson( reader, LoginServiceResponseWrapper.class );
				ProductServiceResponseWrapper serverResponseWrapper = gson.fromJson( reader, ProductServiceResponseWrapper.class );
				if( serverResponseWrapper != null )
				{

					// to sync Product and Categories table
					if( serverResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_db_connection_failed )
					{

						response.status = AppGlobal.RESPONSE_STATUS_db_connection_failed;
						response.message = serverResponseWrapper.getMessage();
						// db.close();
						// return response;
					}
					else if( serverResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success_but_none_found )
					{
						// ResponseStatusWrapper response = new
						// ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_request_success_but_none_found;
						response.message = serverResponseWrapper.getMessage();
						// db.close();
						// return response;
					}

					else if( serverResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success )
					{

						if( serverResponseWrapper != null )
						{
							for( ProductWrapper productWrapper : serverResponseWrapper.getResponse() )
							{

								ProductBO productBO = new ProductBO( Integer.parseInt( ( productWrapper.getId() == null ? "-1" : productWrapper.getId() ) ), productWrapper.getCode(), productWrapper.getName(), productWrapper.getUnit(), productWrapper.getSize(), new BigDecimal( ( productWrapper.getCost() == null ? "-1" : productWrapper.getCost() ) ), new BigDecimal( productWrapper.getPrice() == null ? "-1" : productWrapper.getPrice() ), productWrapper.getAlertQuality(), productWrapper.getImage(), Integer.valueOf( productWrapper.getCategoryId() == null ? "-1" : productWrapper.getCategoryId() ), Integer.valueOf( productWrapper.getSubCategoryId() == null ? "-1" : productWrapper.getSubCategoryId() ), productWrapper.getQuantity(), new BigDecimal( productWrapper.getTaxRate() == null ? "-1" : productWrapper.getTaxRate() ), Integer.valueOf( productWrapper.getTaxQuantity() == null ? "-1" : productWrapper.getTaxQuantity() ), productWrapper.getDetails() );
								CategoryWrapper categoryWrapper = null;
								if( productWrapper.getCategoryDetail() != null )
								{
									categoryWrapper = productWrapper.getCategoryDetail();
								}
								// DatabaseHandler db = new DatabaseHandler(
								// context, AppGlobal.TABLE_LOGIN );
								if( db.getProduct( productBO.getId() ) == null )
									db.addProduct( productBO );
								if( categoryWrapper != null )
								{
									CategoryBO categoryBO = new CategoryBO( Integer.parseInt( categoryWrapper.getId() ), categoryWrapper.getCode(), categoryWrapper.getName() );
									DatabaseHandler dbHandlerCategory = new DatabaseHandler( context, AppGlobal.TABLE_CATEGORY );
									if( dbHandlerCategory.getCategory( categoryBO.getId() ) == null )
										dbHandlerCategory.addCategory( categoryBO );
									dbHandlerCategory.close();
								}
							}
							// ResponseStatusWrapper response = new
							// ResponseStatusWrapper();

							response.status = AppGlobal.RESPONSE_STATUS_request_success;
							response.message = serverResponseWrapper.getMessage();
							// db.close();
							// return response;

						}
					}
				}

				// to sync Tax Rate Table
				db = new DatabaseHandler( context, AppGlobal.TABLE_TAX_RATE );
				nameValuePairs = new ArrayList<NameValuePair>( 1 );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_HOST, host ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_NAME, dbName ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_USER, dbUser ) );
				nameValuePairs.add( new BasicNameValuePair( AppGlobal.PARAM_DB_PASSWPRD, dbPassword ) );

				inputStream = sh.makeServiceCall( AppGlobal.SERVER_URL_GET_TAX_RATE_WEBSERVICE, nameValuePairs );
				reader = new InputStreamReader( inputStream );

				TaxServiceResponseWrapper taxServiceResponseWrapper = gson.fromJson( reader, TaxServiceResponseWrapper.class );

				if( taxServiceResponseWrapper != null )
				{

					if( taxServiceResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_db_connection_failed )
					{
						// ResponseStatusWrapper response = new
						// ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_db_connection_failed;
						response.message = taxServiceResponseWrapper.getMessage();
						// db.close();
						// return response;
					}
					else if( taxServiceResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success_but_none_found )
					{
						// ResponseStatusWrapper response = new
						// ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_request_success_but_none_found;
						response.message = taxServiceResponseWrapper.getMessage();
						// db.close();
						// return response;
					}

					else if( taxServiceResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success )
					{

						if( taxServiceResponseWrapper != null )
						{
							TaxWrapper taxWrapper = taxServiceResponseWrapper.getResponse();

							TaxBO taxBO = new TaxBO( Integer.parseInt( taxWrapper.getId() ), taxWrapper.getName(), taxWrapper.getRate(), taxWrapper.getType() );
							if( !db.isTaxRateExist() )
							{
								db.addTaxRate( taxBO );
								Utils.taxRate = taxBO.getRate();
							}
							else
								Utils.taxRate = db.updateTaxRate( taxBO );
						}
					}

				}

				// We need to sync all sales tables data from sqllite to
				// server.. So here you will get all data from each 4 tables
				// step by step and convert whole data for one table in json
				// object using Gson and I will provide you the service
				// You will send that json in that service. Just like we did in
				// Receptionist. Wait

				// for updating sales table

				db = new DatabaseHandler( context, AppGlobal.TABLE_SALES );
				salesBOList = db.getAllUnsyncSales();
				if( salesBOList.size() > 0 )
				{
					db = new DatabaseHandler( context, AppGlobal.TABLE_SALES_HISTORY );
					salesHistoryBOList = db.getAllUnsyncSalesHistory();

					for( int i = 0 ; i < salesBOList.size() ; i++ )
					{
						int id = salesBOList.get( i ).getId();

						DatabaseHandler db1 = new DatabaseHandler( context, AppGlobal.TABLE_SALE_ITEMS );
						saleItemsBOList = db1.getSaleItemsForId( id );

						db1 = new DatabaseHandler( context, AppGlobal.TABLE_SALE_ITEMS_HISTORY );
						saleItemsHistoryBOList = db1.getSaleItemsHistoryForId( id );

						salesDataBO = new SalesDataBO( salesBOList.get( i ), salesHistoryBOList.get( i ), saleItemsBOList, saleItemsHistoryBOList );
						salesDataBOList.add( salesDataBO );
					}

					StringBuilder salesDataBOListJson = new StringBuilder( gson.toJson( salesDataBOList ) );

					String u = AppGlobal.SERVER_URL_UPDATE_SALES_DATA_WEBSERVICE + "?" + AppGlobal.PARAM_DB_HOST + "=" + host + "&" + AppGlobal.PARAM_DB_NAME + "=" + dbName + "&" + AppGlobal.PARAM_DB_USER + "=" + dbUser + "&" + AppGlobal.PARAM_DB_PASSWPRD + "=" + dbPassword;

					url = new URL( u );
					httpUrlConnection = ( HttpURLConnection ) url.openConnection();
					httpUrlConnection.setRequestMethod( "POST" );
					httpUrlConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
					// httpUrlConnection.setRequestProperty(
					// "Content-Length", "" + Integer.toString(
					// jsonParams.getBytes().length ) );
					httpUrlConnection.setRequestProperty( "Content-Language", "en-US" );
					httpUrlConnection.setUseCaches( false );
					httpUrlConnection.setDoInput( true );
					httpUrlConnection.setDoOutput( true );
					outputStream = new DataOutputStream( httpUrlConnection.getOutputStream() );
					outputStream.writeBytes( "requestParam=" + salesDataBOListJson );
					outputStream.flush();
					outputStream.close();
					// get response
					responseStream = new BufferedInputStream( httpUrlConnection.getInputStream() );
					saleUpdateReader = new InputStreamReader( responseStream );

					ServerResponseWrapper serverResponseWrapper1 = gson.fromJson( saleUpdateReader, ServerResponseWrapper.class );
					if( serverResponseWrapper1.getCode() == AppGlobal.RESPONSE_STATUS_SUCCESS )
					{
						db = new DatabaseHandler( context, AppGlobal.TABLE_SALES );
						int effectedRowSales = db.updateSalesStatus( salesBOList );

						db = new DatabaseHandler( context, AppGlobal.TABLE_SALES_HISTORY );
						int effectedRowSalesHistory = db.updateSalesHistoryStatus( salesHistoryBOList );

						for( int i = 0 ; i < salesBOList.size() ; i++ )
						{
							int id = salesBOList.get( i ).getId();

							DatabaseHandler db1 = new DatabaseHandler( context, AppGlobal.TABLE_SALE_ITEMS );
							saleItemsBOList = db1.getSaleItemsForId( id );
							int effectedRowSaleItems = db1.updateSaleItemsStatus( saleItemsBOList );

							db1 = new DatabaseHandler( context, AppGlobal.TABLE_SALE_ITEMS_HISTORY );
							saleItemsHistoryBOList = db1.getSaleItemsHistoryForId( id );
							int effectedRowSaleItemsHistory = db1.updateSaleItemsHistoryStatus( saleItemsHistoryBOList );

							salesDataBO = new SalesDataBO( salesBOList.get( i ), salesHistoryBOList.get( i ), saleItemsBOList, saleItemsHistoryBOList );
							salesDataBOList.add( salesDataBO );
						}

						response.status = AppGlobal.RESPONSE_STATUS_SUCCESS;
						response.message = AppGlobal.RESPONSE_STATUS_SUCCESS_MESSAGE;
					}

					else
					{
						response.status = AppGlobal.RESPONSE_STATUS_FAIL;
						response.message = AppGlobal.RESPONSE_STATUS_FAIL_MESSAGE;
					}

				}

				/*
				 * Syncing Suspended Bills and Suspended Sales to Server
				 */

				db = new DatabaseHandler( context, AppGlobal.TABLE_SUSPENDED_SALES );
				List<SuspendedSalesBO> suspendedSalesBOList = new ArrayList<SuspendedSalesBO>();
				suspendedSalesBOList = db.getAllSuspendedSales();

				db = new DatabaseHandler( context, AppGlobal.TABLE_SUSPENDED_BILLS );
				List<SuspendedBillsBO> suspendedBillsBOList = new ArrayList<SuspendedBillsBO>();
				suspendedBillsBOList = db.getAllSuspendedBills();

				SuspendedSyncResponseWrapper sendPostRequestWrapper = new SuspendedSyncResponseWrapper();
				sendPostRequestWrapper.setSuspendedBillsBOList( suspendedBillsBOList );
				sendPostRequestWrapper.setSuspendedSalesBOList( suspendedSalesBOList );
				if( suspendedBillsBOList.size() > 0 )
				{

					// StringBuilder suspendedSalesBOListJson = new
					// StringBuilder( gson.toJson( suspendedSalesBOList ) );
					// StringBuilder suspendedBillsBOListJson = new
					// StringBuilder( gson.toJson( suspendedBillsBOList ) );
					StringBuilder sendRequestParamJson = new StringBuilder( gson.toJson( sendPostRequestWrapper ) );

					String u = AppGlobal.SERVER_URL_UPDATE_SUSPENDED_SALES_WEBSERVICE + "?" + AppGlobal.PARAM_DB_HOST + "=" + host + "&" + AppGlobal.PARAM_DB_NAME + "=" + dbName + "&" + AppGlobal.PARAM_DB_USER + "=" + dbUser + "&" + AppGlobal.PARAM_DB_PASSWPRD + "=" + dbPassword;
					url = new URL( u );
					httpUrlConnection = ( HttpURLConnection ) url.openConnection();
					httpUrlConnection.setRequestMethod( "POST" );
					httpUrlConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
					// httpUrlConnection.setRequestProperty(
					// "Content-Length", "" + Integer.toString(
					// jsonParams.getBytes().length ) );
					httpUrlConnection.setRequestProperty( "Content-Language", "en-US" );
					httpUrlConnection.setUseCaches( false );
					httpUrlConnection.setDoInput( true );
					httpUrlConnection.setDoOutput( true );
					outputStream = new DataOutputStream( httpUrlConnection.getOutputStream() );
					outputStream.writeBytes( "requestParam=" + sendRequestParamJson );
					// outputStream.writeBytes( "suspendedBills=" +
					// suspendedBillsBOListJson );
					outputStream.flush();
					outputStream.close();
					// get response
					responseStream = new BufferedInputStream( httpUrlConnection.getInputStream() );
					saleUpdateReader = new InputStreamReader( responseStream );
					ServerResponseSuspendedSaleWrapper serverResponseWrapper1 = gson.fromJson( saleUpdateReader, ServerResponseSuspendedSaleWrapper.class );
					List<ResponseSuspendedSaleWrapper> responseList = new ArrayList<ResponseSuspendedSaleWrapper>();
					responseList = serverResponseWrapper1.getResponse();
					for( int i = 0 ; i < responseList.size() ; i++ )
					{

						ResponseSuspendedSaleWrapper suspendedSalesWrapper = responseList.get( i );
						int suspendedSaleClientId = Integer.parseInt( suspendedSalesWrapper.getClientId() );
						int suspendedSaleServerId = Integer.parseInt( suspendedSalesWrapper.getServerId() );
						String status = suspendedSalesWrapper.getStatus();

						if( status.equals( AppGlobal.DELETE_FROM_CLIENT ) )
						{
							db = new DatabaseHandler( context, AppGlobal.TABLE_SUSPENDED_BILLS );
							db.deleteSuspendedBills( suspendedSaleClientId );

							db = new DatabaseHandler( context, AppGlobal.TABLE_SUSPENDED_SALES );
							db.deleteSuspendedSales( suspendedSaleClientId );
						}
						else if( status.equals( AppGlobal.SYNC ) )
						{
							db = new DatabaseHandler( context, AppGlobal.TABLE_SUSPENDED_BILLS );
							db.updateSuspendedBillsStatus( suspendedSaleClientId, status );
							db.updateSuspendedServerId( suspendedSaleClientId, suspendedSaleServerId );
						}

					}
				}

				return response;
			}
		}
		catch ( Exception ex )
		{
			ResponseStatusWrapper response = new ResponseStatusWrapper();

			response.status = AppGlobal.RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION;
			response.message = AppGlobal.RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION_MESSAGE;
			response.debugInfo = ex.getMessage();
			return response;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute( ResponseStatusWrapper response )
	{

		if( response != null )
		{

			if( response.status == AppGlobal.RESPONSE_STATUS_request_success || response.status == AppGlobal.RESPONSE_STATUS_SUCCESS )
				async.success( response );
			else
				async.fail( response );
		}
		DataSendService.isServiceRunning = false;
	}
}

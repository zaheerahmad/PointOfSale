package com.fusepos.datalayer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
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

							LoginBO loginBO = new LoginBO( Integer.parseInt( loginWrapper.getId() ), loginWrapper.getUsername(), loginWrapper.getPassword(), loginWrapper.getEmail(), loginWrapper.getFirstName(), loginWrapper.getLastName(), loginWrapper.getCompany(), loginWrapper.getPhone() );
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
						ResponseStatusWrapper response = new ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_db_connection_failed;
						response.message = serverResponseWrapper.getMessage();
						// db.close();
						// return response;
					}
					else if( serverResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success_but_none_found )
					{
						ResponseStatusWrapper response = new ResponseStatusWrapper();

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
							ResponseStatusWrapper response = new ResponseStatusWrapper();

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
						ResponseStatusWrapper response = new ResponseStatusWrapper();

						response.status = AppGlobal.RESPONSE_STATUS_db_connection_failed;
						response.message = taxServiceResponseWrapper.getMessage();
						// db.close();
						// return response;
					}
					else if( taxServiceResponseWrapper.getCode() == AppGlobal.RESPONSE_STATUS_request_success_but_none_found )
					{
						ResponseStatusWrapper response = new ResponseStatusWrapper();

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

							TaxBO taxBO = new TaxBO( Integer.parseInt( ( taxWrapper.getId() == null ? "-1" : taxWrapper.getId() ) ), taxWrapper.getName(), taxWrapper.getRate(), taxWrapper.getType() );

							db = new DatabaseHandler( context, AppGlobal.TABLE_TAX_RATE );
							db.updateTaxRate( taxBO );
						}
					}
					db.close();
				}
			}

			/*
			 * if( params != null && params[0].equalsIgnoreCase(
			 * AppGlobal.DATAFETCHER_ACTION_PAYMENT_PROCESS ) )
			 * {
			 * int totalPayable = Integer.parseInt( params[1] );
			 * int amount = totalPayable;
			 * String description = "testing payment";
			 * //Bitmap image = BitmapFactory.decodeResource( getResources(),
			 * R.drawable.fuseposlogo );
			 * TransactionRequestBuilder builder = new
			 * TransactionRequestBuilder( amount, Currency.getInstance( "EUR" )
			 * );
			 * builder.setDescription( description );//.setBitmap( image );
			 * String email = etEmail.getText().toString();
			 * if( !TextUtils.isEmpty( email ) )
			 * {
			 * builder.setEmail( email );
			 * }
			 * TransactionRequest request = builder.createTransactionRequest();
			 * // create a unique id for the payment.
			 * // For reasons of simplicity the UUID class is used here.
			 * // In a production environment it would be more feasible to use
			 * // an ascending numbering scheme
			 * String orderId = UUID.randomUUID().toString();
			 * PaylevenApi.initiatePayment(( Activity ) context , orderId,
			 * request );
			 * }
			 */

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

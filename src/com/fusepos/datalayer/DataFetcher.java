package com.fusepos.datalayer;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
import com.fusepos.wrapper.IAsyncTask;
import com.fusepos.wrapper.LoginServiceResponseWrapper;
import com.fusepos.wrapper.LoginWrapper;
import com.fusepos.wrapper.ResponseStatusWrapper;
import com.fusepos.wrapper.ServerResponseWrapper;
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
			this.hasInternet = true;
			async.doWait();
		}
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
				ServerResponseWrapper serverResponseWrapper = gson.fromJson( reader, ServerResponseWrapper.class );
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
						reader = new InputStreamReader( inputStream );
						LoginServiceResponseWrapper loginResponseWrapper = gson.fromJson( reader, LoginServiceResponseWrapper.class );
						if( loginResponseWrapper != null )
						{
							LoginWrapper loginWrapper = loginResponseWrapper.getResponse();

							LoginBO loginBO = new LoginBO( Integer.parseInt( loginWrapper.getId() ), loginWrapper.getUsername(), loginWrapper.getPassword(), loginWrapper.getPassword(), loginWrapper.getFirstName(), loginWrapper.getLastName(), loginWrapper.getCompany(), loginWrapper.getPhone() );
							DatabaseHandler db = new DatabaseHandler( context, AppGlobal.TABLE_LOGIN );
							db.addLogin( loginBO );

							ResponseStatusWrapper response = new ResponseStatusWrapper();

							response.status = AppGlobal.RESPONSE_STATUS_request_success;
							response.message = loginResponseWrapper.getMessage();
							return response;
						}
					}
				}
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

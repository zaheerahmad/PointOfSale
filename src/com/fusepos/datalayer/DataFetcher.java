package com.fusepos.datalayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.fusepos.service.DataSendService;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.Utils;
import com.fusepos.wrapper.IAsyncTask;
import com.fusepos.wrapper.ResponseStatusWrapper;

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

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected synchronized ResponseStatusWrapper doInBackground( String... params )
	{

		try
		{
			if( !this.hasInternet )
			{
				ResponseStatusWrapper response = new ResponseStatusWrapper();

				response.status = AppGlobal.RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION;
				response.message = AppGlobal.RESPONSE_STATUS_FAIL_NO_INTERNET_CONNECTION_MESSAGE;
				return response;
			}
		}
		catch ( Exception ex )
		{
			ResponseStatusWrapper response = new ResponseStatusWrapper();

			response.status = AppGlobal.RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION;
			response.message = AppGlobal.RESPONSE_STATUS_FAIL_RANDOM_EXCEPTION_MESSAGE;
			response.debugInfo = ex.getMessage();

			if( AppGlobal.shouldMaintainLogOfFeeds && isFileWritten )
			{
				SharedPreferences prefs = Utils.getSharedPreferences( context );
				prefs.edit().putInt( AppGlobal.APP_PREF_LAST_INSERTED_ID, lastIndexS ).commit();
				isFileWritten = false;
			}
			return response;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute( ResponseStatusWrapper response )
	{

		if( response != null )
		{

			if( response.status == AppGlobal.RESPONSE_STATUS_SUCCESS )
				async.success( response );
			else
				async.fail( response );
		}
		DataSendService.isServiceRunning = false;
	}
}

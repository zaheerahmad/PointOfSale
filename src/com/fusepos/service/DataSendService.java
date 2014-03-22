package com.fusepos.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.fusepos.datalayer.DataFetcher;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.Utils;
import com.fusepos.wrapper.IAsyncTask;
import com.fusepos.wrapper.ResponseStatusWrapper;

/**
 * @author Zaheer Ahmad
 *
 */
public class DataSendService extends Service
{
	public static boolean	isServiceRunning	= false;

	@Override
	public void onCreate()
	{

		// TODO Auto-generated method stub
		super.onCreate();

		if( AppGlobal.isDebugMode )
			Toast.makeText( getApplicationContext(), "Service Started...", Toast.LENGTH_LONG ).show();
	}

	@Override
	public int onStartCommand( Intent intent, int flags, int startId )
	{

		// TODO do something useful
		if( isServiceRunning )
			return super.onStartCommand( intent, flags, startId );
		isServiceRunning = true;
		if( AppGlobal.isDebugMode )
			Toast.makeText( getApplicationContext(), "Service Started...", Toast.LENGTH_LONG ).show();

		new DataFetcher( new IAsyncTask()
		{

			/**
			 * @param response
			 */
			@Override
			public void success( ResponseStatusWrapper response )
			{

				// TODO Auto-generated method stub
				if( AppGlobal.isDebugMode )
					Toast.makeText( getApplicationContext(), AppGlobal.TOAST_DATA_UPLOADED_SUCCESSFULLY, Toast.LENGTH_LONG ).show();

				
				isServiceRunning = false;
			}

			@Override
			public void fail( ResponseStatusWrapper response )
			{

				// TODO Auto-generated method stub
				if( AppGlobal.isDebugMode )
				{
					Toast.makeText( getApplicationContext(), "Data Uploading Error: " + response.message, Toast.LENGTH_LONG ).show();
					if( !Utils.isNullOrEmpty( response.debugInfo ) )
						Toast.makeText( getApplicationContext(), "Error Debug Info: " + response.debugInfo, Toast.LENGTH_LONG ).show();
				}
				isServiceRunning = false;
			}

			@Override
			public void doWait()
			{

				// TODO Auto-generated method stub
				if( AppGlobal.isDebugMode )
					Toast.makeText( getApplicationContext(), AppGlobal.TOAST_INTERNET_CONNECTION_FOUND, Toast.LENGTH_LONG ).show();
			}
		}, getApplicationContext() ).execute( new String[] { AppGlobal.DATAFETCHER_ACTION_INSERT_FEEDBACK } );
		return super.onStartCommand( intent, flags, startId );
	}

	@Override
	public void onDestroy()
	{

		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind( Intent intent )
	{

		// TODO for communication return IBinder implementation
		return null;
	}

}

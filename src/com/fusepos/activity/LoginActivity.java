package com.fusepos.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fusepos.datalayer.DataFetcher;
import com.fusepos.datalayer.DatabaseHandler;
import com.fusepos.service.DataSendService;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.SAutoBgButton;
import com.fusepos.utils.Utils;
import com.fusepos.wrapper.IAsyncTask;
import com.fusepos.wrapper.ResponseStatusWrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class LoginActivity extends Activity
{
	EditText		txtUsername		= null;
	EditText		txtPassword		= null;
	ProgressDialog	loadingDialog	= null;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_login );

		txtUsername = ( EditText ) findViewById( R.id.login_username_et );
		txtPassword = ( EditText ) findViewById( R.id.login_password_et );

		SAutoBgButton btnSettings = ( SAutoBgButton ) findViewById( R.id.login_setting_btn );
		btnSettings.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				// TODO Auto-generated method stub
				// ViewGroup viewGroup = ( ViewGroup ) v;
				LayoutInflater li = LayoutInflater.from( LoginActivity.this );
				View promptsView = li.inflate( R.layout.setting_db_instance, null );

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( LoginActivity.this );
				alertDialogBuilder.setView( promptsView );

				final EditText hostName = ( EditText ) promptsView.findViewById( R.id.instance_d_host_name );

				final EditText dbName = ( EditText ) promptsView.findViewById( R.id.instance_d_db_name );

				final EditText dbUserName = ( EditText ) promptsView.findViewById( R.id.instance_d_db_username );

				final EditText dbPassword = ( EditText ) promptsView.findViewById( R.id.instance_d_db_password );

				alertDialogBuilder.setCancelable( false ).setPositiveButton( "Save", new DialogInterface.OnClickListener()
				{

					public void onClick( DialogInterface dialog, int id )
					{

						String host = hostName.getText().toString();
						String db = dbName.getText().toString();
						String user = dbUserName.getText().toString();
						String pass = dbPassword.getText().toString();

						if( Utils.isNullOrEmpty( host ) || Utils.isNullOrEmpty( db ) || Utils.isNullOrEmpty( user ) || Utils.isNullOrEmpty( pass ) )
						{
							Toast.makeText( getApplicationContext(), AppGlobal.TOAST_MISSING_MANDATORY_FIELDS, Toast.LENGTH_LONG ).show();
						}
						else
						{
							SharedPreferences prefs = Utils.getSharedPreferences( getApplicationContext() );
							if( prefs != null )
							{
								prefs.edit().putString( AppGlobal.APP_PREF_HOST_NAME, host ).commit();
								prefs.edit().putString( AppGlobal.APP_PREF_DB_NAME, db ).commit();
								prefs.edit().putString( AppGlobal.APP_PREF_DB_USERNAME, user ).commit();
								prefs.edit().putString( AppGlobal.APP_PREF_DB_PASSWORD, pass ).commit();

								Toast.makeText( getApplicationContext(), AppGlobal.TOAST_DATA_SAVED, Toast.LENGTH_SHORT ).show();
							}
						}
					}
				} ).setNegativeButton( "Cancel", new DialogInterface.OnClickListener()
				{

					public void onClick( DialogInterface dialog, int id )
					{

						dialog.cancel();
					}
				} );

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		} );

		SAutoBgButton btnLogin = ( SAutoBgButton ) findViewById( R.id.login_login_btn );
		btnLogin.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				if( Utils.isNullOrEmpty( txtUsername.getText().toString() ) || Utils.isNullOrEmpty( txtPassword.getText().toString() ) )
				{
					Toast.makeText( getApplicationContext(), AppGlobal.TOAST_MISSING_MANDATORY_FIELDS, Toast.LENGTH_SHORT ).show();
				}
				else
				{
					// TODO Auto-generated method stub
					DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_LOGIN );
					if( dbHandler.isUserExist( txtUsername.getText().toString() ) )
					{
						if( dbHandler.validateLogin( txtUsername.getText().toString(), txtPassword.getText().toString() ) )
						{
							Toast.makeText( getApplicationContext(), "User validated from SQLLite DB", Toast.LENGTH_SHORT ).show();

							if( !DataSendService.isServiceRunning )
							{
								final Calendar TIME = Calendar.getInstance();
								TIME.set( Calendar.MINUTE, 0 );
								TIME.set( Calendar.SECOND, 0 );
								TIME.set( Calendar.MILLISECOND, 0 );

								final AlarmManager m = ( AlarmManager ) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
								final Intent i = new Intent( getApplicationContext(), DataSendService.class );
								PendingIntent serviceIntent = PendingIntent.getService( getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT );
								m.setRepeating( AlarmManager.RTC, TIME.getTime().getTime(), AppGlobal.SERVICE_DELAY, serviceIntent );
							}

							Intent saleActivityIntent = new Intent( LoginActivity.this, SaleActivity.class );
							startActivity( saleActivityIntent );
						}
						else
						{
							Toast.makeText( getApplicationContext(), "Invalid User/Password", Toast.LENGTH_SHORT ).show();
						}
					}
					else
					{
						SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );

						String host = pref.getString( AppGlobal.APP_PREF_HOST_NAME, "" );
						String db = pref.getString( AppGlobal.APP_PREF_DB_NAME, "" );
						String dbUser = pref.getString( AppGlobal.APP_PREF_DB_USERNAME, "" );
						String dbPassword = pref.getString( AppGlobal.APP_PREF_DB_PASSWORD, "" );

						if( Utils.isNullOrEmpty( host ) || Utils.isNullOrEmpty( db ) || Utils.isNullOrEmpty( dbUser ) || Utils.isNullOrEmpty( dbPassword ) )
						{
							Toast.makeText( getApplicationContext(), "Please add DB Instance from settings!", Toast.LENGTH_LONG ).show();
						}
						else
						{
							new DataFetcher( new IAsyncTask()
							{

								@Override
								public void success( ResponseStatusWrapper response )
								{

									// TODO Auto-generated method stub
									if( loadingDialog != null )
										loadingDialog.dismiss();
									Toast.makeText( getApplicationContext(), response.message, Toast.LENGTH_SHORT ).show();
									if( !DataSendService.isServiceRunning )
									{
										final Calendar TIME = Calendar.getInstance();
										TIME.set( Calendar.MINUTE, 0 );
										TIME.set( Calendar.SECOND, 0 );
										TIME.set( Calendar.MILLISECOND, 0 );

										final AlarmManager m = ( AlarmManager ) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
										final Intent i = new Intent( getApplicationContext(), DataSendService.class );
										PendingIntent serviceIntent = PendingIntent.getService( getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT );
										m.setRepeating( AlarmManager.RTC, TIME.getTime().getTime(), AppGlobal.SERVICE_DELAY, serviceIntent );
									}
									Intent saleActivityIntent = new Intent( LoginActivity.this, SaleActivity.class );
									startActivity( saleActivityIntent );
								}

								@Override
								public void fail( ResponseStatusWrapper response )
								{

									// TODO Auto-generated method stub
									if( loadingDialog != null )
										loadingDialog.dismiss();
									Toast.makeText( getApplicationContext(), response.message, Toast.LENGTH_SHORT ).show();
								}

								@Override
								public void doWait()
								{

									// TODO Auto-generated method stub
									loadingDialog = new ProgressDialog( LoginActivity.this );
									loadingDialog.setMessage( AppGlobal.TOAST_PLEASE_WAIT );
									loadingDialog.setCancelable( false );
									loadingDialog.show();
								}
							}, getApplicationContext() ).execute( AppGlobal.DATAFETCHER_ACTION_LOGIN_USER, txtUsername.getText().toString(), txtPassword.getText().toString() );
						}
					}
				}
			}
		} );
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

}

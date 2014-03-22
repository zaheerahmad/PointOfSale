package com.fusepos.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.SAutoBgButton;
import com.fusepos.utils.Utils;

/**
 * @author Zaheer Ahmad
 * 
 */
public class LoginActivity extends Activity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_login );

		SAutoBgButton btnSettings = ( SAutoBgButton ) findViewById( R.id.login_setting_btn );
		btnSettings.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				// TODO Auto-generated method stub
				//ViewGroup viewGroup = ( ViewGroup ) v;
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
	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
	}

}

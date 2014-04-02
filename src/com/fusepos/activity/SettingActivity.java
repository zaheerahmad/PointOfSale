package com.fusepos.activity;

import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.SAutoBgButton;
import com.fusepos.utils.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity
{
	public void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.setting_layout );

		SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );
		final EditText hostName = ( EditText ) findViewById( R.id.instance_d_host_name );

		final EditText dbName = ( EditText ) findViewById( R.id.instance_d_db_name );

		final EditText dbUserName = ( EditText ) findViewById( R.id.instance_d_db_username );

		final EditText dbPassword = ( EditText ) findViewById( R.id.instance_d_db_password );

		if( pref != null )
		{
			hostName.setText( pref.getString( AppGlobal.APP_PREF_HOST_NAME, "" ) );
			dbName.setText( pref.getString( AppGlobal.APP_PREF_DB_NAME, "" ) );
			dbUserName.setText( pref.getString( AppGlobal.APP_PREF_DB_USERNAME, "" ) );
			dbPassword.setText( pref.getString( AppGlobal.APP_PREF_DB_PASSWORD, "" ) );
		}

		SAutoBgButton btnSave = ( SAutoBgButton ) findViewById( R.id.setting_save_btn );
		btnSave.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				// TODO Auto-generated method stub
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
		} );
	}
}

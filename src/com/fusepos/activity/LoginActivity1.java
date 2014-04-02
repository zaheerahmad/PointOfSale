package com.fusepos.activity;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fusepos.datalayer.DataFetcher;
import com.fusepos.datalayer.DatabaseHandler;
import com.fusepos.datalayer.LoginBO;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.SAutoBgButton;
import com.fusepos.utils.Utils;
import com.fusepos.wrapper.IAsyncTask;
import com.fusepos.wrapper.ResponseStatusWrapper;

/**
 * @author Zaheer Ahmad
 * 
 */
public class LoginActivity1 extends Activity
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

		SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );

		if( pref != null )
		{
			txtUsername.setText( pref.getString( AppGlobal.APP_PREF_USERNAME, "" ) );
			txtPassword.setText( pref.getString( AppGlobal.APP_PREF_PASSWORD, "" ) );
		}

		SAutoBgButton btnSettings = ( SAutoBgButton ) findViewById( R.id.login_setting_btn );
		btnSettings.setOnClickListener( new View.OnClickListener()
		{

			@Override
			public void onClick( View v )
			{

				// TODO Auto-generated method stub
				// ViewGroup viewGroup = ( ViewGroup ) v;
				LayoutInflater li = LayoutInflater.from( LoginActivity1.this );
				View promptsView = li.inflate( R.layout.setting_db_instance, null );
				SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( LoginActivity1.this );
				alertDialogBuilder.setView( promptsView );

				final EditText hostName = ( EditText ) promptsView.findViewById( R.id.instance_d_host_name );

				final EditText dbName = ( EditText ) promptsView.findViewById( R.id.instance_d_db_name );

				final EditText dbUserName = ( EditText ) promptsView.findViewById( R.id.instance_d_db_username );

				final EditText dbPassword = ( EditText ) promptsView.findViewById( R.id.instance_d_db_password );

				if( pref != null )
				{
					hostName.setText( pref.getString( AppGlobal.APP_PREF_HOST_NAME, "" ) );
					dbName.setText( pref.getString( AppGlobal.APP_PREF_DB_NAME, "" ) );
					dbUserName.setText( pref.getString( AppGlobal.APP_PREF_DB_USERNAME, "" ) );
					dbPassword.setText( pref.getString( AppGlobal.APP_PREF_DB_PASSWORD, "" ) );
				}

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
					List<LoginBO> logins = dbHandler.getAllLogins();
					if( dbHandler.isUserExist( txtUsername.getText().toString() ) )
					{
						if( dbHandler.validateLogin( txtUsername.getText().toString(), txtPassword.getText().toString() ) )
						{
							dbHandler.close();
							Toast.makeText( getApplicationContext(), "User validated from SQLLite DB", Toast.LENGTH_SHORT ).show();
							SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );
							pref.edit().putString( AppGlobal.APP_PREF_USERNAME, txtUsername.getText().toString() ).commit();
							pref.edit().putString( AppGlobal.APP_PREF_PASSWORD, txtPassword.getText().toString() ).commit();

							// Loading Products First Time.. Manually
							// Internet should be available here.

							new DataFetcher( new IAsyncTask()
							{

								@Override
								public void success( ResponseStatusWrapper response )
								{

									// TODO Auto-generated method stub
									if( loadingDialog != null )
										loadingDialog.dismiss();

									Intent saleActivityIntent = new Intent( LoginActivity1.this, SaleActivity.class );
									startActivity( saleActivityIntent );
								}

								@Override
								public void fail( ResponseStatusWrapper response )
								{

									// TODO Auto-generated method stub
									if( loadingDialog != null )
										loadingDialog.dismiss();
									DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
									if( db.getProductCount() > 0 )
									{
										db.close();
										Intent saleActivityIntent = new Intent( LoginActivity1.this, SaleActivity.class );
										startActivity( saleActivityIntent );
									}
									else
									{
										Toast.makeText( getApplicationContext(), "Couldn't load sync products from server.", Toast.LENGTH_LONG ).show();
									}
								}

								@Override
								public void doWait()
								{

									// TODO Auto-generated method stub
									loadingDialog = new ProgressDialog( LoginActivity1.this );
									loadingDialog.setMessage( AppGlobal.TOAST_PLEASE_WAIT );
									loadingDialog.setCancelable( false );
									loadingDialog.show();
								}
							}, getApplicationContext() ).execute( AppGlobal.DATAFETCHER_ACTION_PRODUCTS_SYNC );
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

									SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );
									pref.edit().putString( AppGlobal.APP_PREF_USERNAME, txtUsername.getText().toString() ).commit();
									pref.edit().putString( AppGlobal.APP_PREF_PASSWORD, txtPassword.getText().toString() ).commit();

									// Loading Products First Time.. Manually
									// Internet should be available here.

									new DataFetcher( new IAsyncTask()
									{

										@Override
										public void success( ResponseStatusWrapper response )
										{

											// TODO Auto-generated method stub
											if( loadingDialog != null )
												loadingDialog.dismiss();

											Intent saleActivityIntent = new Intent( LoginActivity1.this, SaleActivity.class );
											startActivity( saleActivityIntent );
										}

										@Override
										public void fail( ResponseStatusWrapper response )
										{

											// TODO Auto-generated method stub
											if( loadingDialog != null )
												loadingDialog.dismiss();
											Toast.makeText( getApplicationContext(), "Couldn't load sync products from server.", Toast.LENGTH_LONG ).show();
										}

										@Override
										public void doWait()
										{

											// TODO Auto-generated method stub
											loadingDialog = new ProgressDialog( LoginActivity1.this );
											loadingDialog.setMessage( AppGlobal.TOAST_PLEASE_WAIT );
											loadingDialog.setCancelable( false );
											loadingDialog.show();
										}
									}, getApplicationContext() ).execute( AppGlobal.DATAFETCHER_ACTION_PRODUCTS_SYNC );

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
									loadingDialog = new ProgressDialog( LoginActivity1.this );
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
}

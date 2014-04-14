package com.fusepos.activity;

import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity
{

	Button	btnPOS, btnManage;
	String	timer;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		// TODO Auto-generated method stub
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );

		btnPOS = ( Button ) findViewById( R.id.activity_main_pos_btn );
		btnManage = ( Button ) findViewById( R.id.activity_main_manage_btn );

		btnPOS.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				Intent i = new Intent( MainActivity.this, AndroidTabLayoutActivity.class );
				startActivity( i );
			}
		} );

		btnManage.setOnClickListener( new OnClickListener()
		{
			@Override
			public void onClick( View arg0 )
			{

				SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );
				timer = pref.getString( AppGlobal.APP_PREF_TIMER, null );

				if( Utils.isNullOrEmpty( timer ) )
				{
					Intent i = new Intent( MainActivity.this, WebViewActivity.class );
					startActivity( i );
				}
			}
		} );
	}
}

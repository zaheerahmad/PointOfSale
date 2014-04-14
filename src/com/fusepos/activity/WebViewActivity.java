package com.fusepos.activity;

import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity
{
	String	timer	= null;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.web_view );

		
			WebView myWebView = ( WebView ) findViewById( R.id.webview );
			myWebView.loadUrl( "http://fusepos.com/zaheer/index.php" );

			WebSettings webSettings = myWebView.getSettings();
			webSettings.setJavaScriptEnabled( true );

			myWebView.setWebViewClient( new WebViewClient() );

			new CountDownTimer( 60000, 1000 )
			{

				public void onTick( long millisUntilFinished )
				{

					Log.d( "time", String.valueOf( millisUntilFinished ) );
				}

				public void onFinish()
				{

					SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );
					pref.edit().putString( AppGlobal.APP_PREF_TIMER, "done" ).commit();
					finish();
				}
			}.start();
	}
}

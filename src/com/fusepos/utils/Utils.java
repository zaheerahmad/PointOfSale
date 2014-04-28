package com.fusepos.utils;

import java.io.InputStream;
import java.io.OutputStream;

import org.jsoup.Jsoup;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.StrictMode;
import android.view.inputmethod.InputMethodManager;

/**
 * @author Zaheer Ahmad
 * 
 */
public class Utils
{
	public static boolean isSynchronizing = false;
	public static String taxRate = null;
	
	public static void hideSoftKeyboard( Activity activity )
	{

		InputMethodManager inputMethodManager = ( InputMethodManager ) activity.getSystemService( Activity.INPUT_METHOD_SERVICE );
		inputMethodManager.hideSoftInputFromWindow( activity.getCurrentFocus().getWindowToken(), 0 );
	}

	@TargetApi( VERSION_CODES.HONEYCOMB )
	public static void enableStrictMode()
	{

		if( Utils.hasGingerbread() )
		{
			StrictMode.ThreadPolicy.Builder threadPolicyBuilder = new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog();
			StrictMode.VmPolicy.Builder vmPolicyBuilder = new StrictMode.VmPolicy.Builder().detectAll().penaltyLog();

			if( Utils.hasHoneycomb() )
			{
				threadPolicyBuilder.penaltyFlashScreen();
				// vmPolicyBuilder.setClassInstanceLimit( NewsFragment.class, 1
				// );
			}
			StrictMode.setThreadPolicy( threadPolicyBuilder.build() );
			StrictMode.setVmPolicy( vmPolicyBuilder.build() );
		}
	}

	public static boolean hasFroyo()
	{

		// Can use static final constants like FROYO, declared in later versions
		// of the OS since they are inlined at compile time. This is guaranteed
		// behavior.
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasHoneycomb()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasJellyBean()
	{

		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	// public static boolean hasKitKat() {
	// return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	// }

	public static void CopyStream( InputStream is, OutputStream os )
	{

		final int buffer_size = 1024;
		try
		{
			byte[] bytes = new byte[buffer_size];
			for( ; ; )
			{
				int count = is.read( bytes, 0, buffer_size );
				if( count == -1 )
					break;
				os.write( bytes, 0, count );
			}
		}
		catch ( Exception ex )
		{
		}
	}

	public static String html2text( String html )
	{

		String str = Jsoup.parse( html ).text();
		str = str.replaceAll( "&nbsp;", "" );
		str = str.replaceAll( "&amp;", "" );
		str = str.replaceAll( "<p>", "" );
		str = str.replaceAll( "</p>", "" );
		str = str.replaceAll( "<ul>", "" );
		str = str.replaceAll( "</ul>", "" );
		str = str.replaceAll( "<li>", "" );
		str = str.replaceAll( "</li>", "" );
		return str;
	}

	public static boolean hasInternetConnection( Context context )
	{

		ConnectivityManager cm = ( ConnectivityManager ) context.getSystemService( Context.CONNECTIVITY_SERVICE );
		NetworkInfo wifiNetwork = cm.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
		if( wifiNetwork != null && wifiNetwork.isConnected() )
		{
			return true;
		}
		NetworkInfo mobileNetwork = cm.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );
		if( mobileNetwork != null && mobileNetwork.isConnected() )
		{
			return true;
		}
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if( activeNetwork != null && activeNetwork.isConnected() )
		{
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmpty( String string )
	{

		if( string != null && !string.equals( "" ) )
			return false;
		return true;
	}

	public static SharedPreferences getSharedPreferences( Context context )
	{

		return context.getSharedPreferences( AppGlobal.APP_PREF_NAME, Context.MODE_PRIVATE );
	}
}

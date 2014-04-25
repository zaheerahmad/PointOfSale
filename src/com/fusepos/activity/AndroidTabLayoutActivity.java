package com.fusepos.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class AndroidTabLayoutActivity extends TabActivity
{
	String		orangeColor	= "#ffffff";
	String		greyColor	= "#f6b79d";
	ViewGroup	vg;
	TextView	tv;

	public void setTabColor( TabHost tabhost )
	{

		for( int i = 0 ; i < tabhost.getTabWidget().getChildCount() ; i++ )
		{
			// working for unselected
			tabhost.getTabWidget().getChildAt( i ).setBackgroundColor( Color.parseColor( greyColor ) ); // unselected
			vg = ( ViewGroup ) tabhost.getTabWidget().getChildAt( i );
			tv = ( TextView ) vg.getChildAt( 1 );
			tv.setTextColor( Color.parseColor( orangeColor ) );

		}

		if( tabhost.getCurrentTab() == 0 )
		{
			// working for tab selected
			tabhost.getTabWidget().getChildAt( tabhost.getCurrentTab() ).setBackgroundColor( Color.parseColor( orangeColor ) ); // 1st
			vg = ( ViewGroup ) tabhost.getTabWidget().getChildAt( tabhost.getCurrentTab() );
			tv = ( TextView ) vg.getChildAt( 1 );
			tv.setTextColor( Color.parseColor( "#000000" ) );
		}

		else
		{
			tabhost.getTabWidget().getChildAt( tabhost.getCurrentTab() ).setBackgroundColor( Color.parseColor( orangeColor ) ); // 2nd
			vg = ( ViewGroup ) tabhost.getTabWidget().getChildAt( tabhost.getCurrentTab() );
			tv = ( TextView ) vg.getChildAt( 1 );
			tv.setTextColor( Color.parseColor( "#000000" ) );
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		TabHost tabHost = getTabHost();
		final TabHost tH = tabHost;

		tabHost.setOnTabChangedListener( new OnTabChangeListener()
		{

			@Override
			public void onTabChanged( String arg0 )
			{

				setTabColor( tH );
				/*
				 * View v;
				 * v = tH.getCurrentTabView();
				 * v.setBackgroundColor( Color.GRAY );
				 */
			}
		} );
		// setTabColor( tabHost );
		// tabHost.getCurrentTabView().setBackgroundColor( Color.parseColor(
		// orangeColor ) );
		// Tab for Photos
		TabSpec photospec = tabHost.newTabSpec( "Login" );
		photospec.setIndicator( "Login", getResources().getDrawable( R.drawable.icon_photos_tab ) );
		Intent photosIntent = new Intent( this, LoginActivity.class );
		photospec.setContent( photosIntent );

		// Tab for Songs
		TabSpec songspec = tabHost.newTabSpec( "Settings" );
		// setting Title and Icon for the Tab
		songspec.setIndicator( "Settings", getResources().getDrawable( R.drawable.icon_songs_tab ) );
		Intent songsIntent = new Intent( this, SettingActivity.class );
		songspec.setContent( songsIntent );

		// Tab for Videos
		TabSpec videospec = tabHost.newTabSpec( "Help" );
		videospec.setIndicator( "Help", getResources().getDrawable( R.drawable.icon_videos_tab ) );
		Intent videosIntent = new Intent( this, VideosActivity.class );
		videospec.setContent( videosIntent );

		// Adding all TabSpec to TabHost
		tabHost.addTab( photospec ); // Adding photos tab
		tabHost.addTab( songspec ); // Adding songs tab
		tabHost.addTab( videospec ); // Adding videos tab
		
		
		for( int i = 0 ; i < tabHost.getTabWidget().getChildCount() ; i++ )
		{
			// working for unselected
			tabHost.getTabWidget().getChildAt( i ).setBackgroundColor( Color.parseColor( greyColor ) ); // unselected
			vg = ( ViewGroup ) tabHost.getTabWidget().getChildAt( i );
			tv = ( TextView ) vg.getChildAt( 1 );
			tv.setTextColor( Color.parseColor( orangeColor ) );

		}

		if( tabHost.getCurrentTab() == 0 )
		{
			// working for tab selected
			tabHost.getTabWidget().getChildAt( tabHost.getCurrentTab() ).setBackgroundColor( Color.parseColor( orangeColor ) ); // 1st
			vg = ( ViewGroup ) tabHost.getTabWidget().getChildAt( tabHost.getCurrentTab() );
			tv = ( TextView ) vg.getChildAt( 1 );
			tv.setTextColor( Color.parseColor( "#000000" ) );
		}

		else
		{
			tabHost.getTabWidget().getChildAt( tabHost.getCurrentTab() ).setBackgroundColor( Color.parseColor( orangeColor ) ); // 2nd
			vg = ( ViewGroup ) tabHost.getTabWidget().getChildAt( tabHost.getCurrentTab() );
			tv = ( TextView ) vg.getChildAt( 1 );
			tv.setTextColor( Color.parseColor( "#000000" ) );
		}
	}
}

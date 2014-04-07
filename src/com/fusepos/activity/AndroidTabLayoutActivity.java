package com.fusepos.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity
{
	String	orangeColor	= "#ff6600";
	String	greyColor	= "#808069";

	public void setTabColor( TabHost tabhost )
	{

		for( int i = 0 ; i < tabhost.getTabWidget().getChildCount() ; i++ )
			tabhost.getTabWidget().getChildAt( i ).setBackgroundColor( Color.parseColor( greyColor ) ); // unselected

		if( tabhost.getCurrentTab() == 0 )
			tabhost.getTabWidget().getChildAt( tabhost.getCurrentTab() ).setBackgroundColor( Color.parseColor( orangeColor ) ); // 1st
		// tab
		// selected
		else
			tabhost.getTabWidget().getChildAt( tabhost.getCurrentTab() ).setBackgroundColor( Color.parseColor( orangeColor ) ); // 2nd
		// tab
		// selected
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
	}
}

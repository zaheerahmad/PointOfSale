package com.fusepos.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AndroidTabLayoutActivity extends TabActivity
{
	/** Called when the activity is first created. */
	@Override
	public void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		setContentView( R.layout.main );

		TabHost tabHost = getTabHost();

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
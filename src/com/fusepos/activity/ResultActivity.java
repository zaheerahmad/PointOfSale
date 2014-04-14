package com.fusepos.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.payleven.payment.api.OpenTransactionDetailsCompletedStatus;
import com.payleven.payment.api.PaylevenApi;
import com.payleven.payment.api.PaylevenResponseListener;
import com.payleven.payment.api.PaymentCompletedStatus;
import com.payleven.payment.api.TransactionRequest;

public class ResultActivity extends ListActivity
{

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		super.onCreate( savedInstanceState );
		Intent i = new Intent();
		if( getIntent().hasExtra( "result" ) )
		{
			i.putExtras( getIntent().getExtras().getBundle( "result" ) );
		}
		int requestCode = getIntent().getIntExtra( "request_code", 0 );
		PaylevenApi.handleIntent( requestCode, i, new PaylevenResponseListener()
		{

			@Override
			public void onPaymentFinished( String orderId, TransactionRequest originalRequest, Map<String, String> result, PaymentCompletedStatus status )
			{

				SharedPreferences prefs = getSharedPreferences( "api_example", Context.MODE_PRIVATE );
				prefs.edit().putString( "order_id", orderId ).apply();

				showResult( orderId, status.name(), result );
			}

			@Override
			public void onOpenSalesHistoryFinished()
			{

				Toast.makeText( ResultActivity.this, "sales history finished", Toast.LENGTH_LONG ).show();
				finish();
			}

			@Override
			public void onNoPaylevenResponse( Intent data )
			{

			}

			@Override
			public void onOpenTransactionDetailsFinished( String orderId, Map<String, String> transactionData, OpenTransactionDetailsCompletedStatus status )
			{

				showResult( orderId, status.name(), transactionData );
			}

		} );

	}

	protected void showResult( String orderId, String statusName, Map<String, String> result )
	{

		List<String> resultList = new ArrayList<String>();
		resultList.add( "OrderId = " + orderId );
		resultList.add( "Status = " + statusName );
		if( result != null )
		{
			for( Entry<String, String> entry : result.entrySet() )
			{
				resultList.add( entry.getKey() + " = " + entry.getValue() );
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( ResultActivity.this, android.R.layout.simple_list_item_1, resultList );
		setListAdapter( adapter );
	}

}

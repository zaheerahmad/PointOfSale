/**
 * 
 */
package com.fusepos.activity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fusepos.datalayer.CategoryBO;
import com.fusepos.datalayer.DatabaseHandler;
import com.fusepos.datalayer.ProductBO;
import com.fusepos.datalayer.SaleItemsBO;
import com.fusepos.datalayer.SaleItemsHistoryBO;
import com.fusepos.datalayer.SalesBO;
import com.fusepos.datalayer.SalesHistoryBO;
import com.fusepos.datalayer.SuspendedBillsBO;
import com.fusepos.datalayer.SuspendedSalesBO;
import com.fusepos.service.DataSendService;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.SAutoBgButton;
import com.fusepos.utils.Utils;
import com.payleven.payment.api.PaylevenApi;
import com.payleven.payment.api.TransactionRequest;
import com.payleven.payment.api.TransactionRequestBuilder;

/**
 * @author Zaheer Ahmad
 * 
 */
public class SaleActivity extends Activity
{

	SalesBO						salesBO;
	SalesHistoryBO				salesHistoryBO;
	SaleItemsBO					saleItemsBO;
	SaleItemsHistoryBO			saleItemsHistoryBO;
	SuspendedSalesBO			suspendedSalesBO;

	List<SalesBO>				salesBOList;
	List<SalesHistoryBO>		salesHistoryBOList;
	List<SaleItemsBO>			saleItemsBOList;
	List<SaleItemsHistoryBO>	saleItemsHistoryBOList;

	List<ProductBO>				_saleListProductForListView;
	List<ProductBO>				_saleListProductForGridView;
	List<CategoryBO>			_saleListCategoryForDisplay;
	List<SuspendedBillsBO>		suspendedBillsListForSuspendedDialogListView;
	private List<ProductBO>		globalListOfProductConst	= null;
	private List<Button>		catButtonsList				= null;

	SaleListAdapter				_saleListAdapter;
	SaleGridAdapter				_saleGridAdapter;
	SuspendedListAdapter		_suspendedListAdapter;
	GridView					gridProducts;
	ListView					listProducts;
	ListView					listSuspendedSales;

	LinearLayout				parentLinearLayout;
	RelativeLayout				paymentDialogRelativeLayoutCash1;
	RelativeLayout				paymentDialogRelativeLayoutCash2;
	RelativeLayout				paymentDialogRelativeLayoutChecque;
	RelativeLayout				paymentDialogRelativeLayoutCreditCard1;
	RelativeLayout				paymentDialogRelativeLayoutCreditCard2;

	ProgressDialog				loadingDialog;
	Spinner						dialogPaymentMethodSpinner;
	CountDownTimer				cDt;
	MenuItem					syncMenu;

	EditText					paymentDialogPaidEditText;
	EditText					paymentDialogCreditCardNoEditText;
	EditText					paymentDialogCardHolderEditText;
	EditText					paymentDialogChecqueNoEditText;

	TextView					totalPayableTextView;
	TextView					totalItemsTextView;
	TextView					taxTextView;
	TextView					discountTextView;
	TextView					totalTextView;
	TextView					vatTextView;
	TextView					paymentDialogTotalPayableTextView;
	TextView					paymentDialogTotalItemsTextView;
	TextView					paymentDialogChangeTextView;

	Button						categoryButton;
	Button						suspendButton;
	Button						paymentButton;
	Button						cancelButton;
	Button						paymentDialogSubmitButton;
	Button						paymentDialogCloseButton;

	Dialog						paymentDialog;
	Dialog						suspendedSalesDialog;

	// for suspended dialog
	int							suspendedItems				= 0;
	double						suspendedProductTax			= 0.0;
	double						suspendedInvoiceTax			= 0.0;
	double						suspendedDiscount			= 0.0;
	double						suspendedTotal				= 0.0;
	// for payment dialog
	double						paymentDialogPaid			= -1;
	String						paymentDialogPaidBy			= "";
	String						paymentDialogCreditCardNo	= "";
	String						paymentDialogCardHolder		= "";
	String						paymentDialogChecqueNo		= "";
	// sale activity
	int							totalItem					= 0;
	double						totalPayable				= 0.0;
	double						tax							= 0.0;
	double						discount					= 0.0;
	double						total						= 0.0;
	double						vat							= 0.0;
	double						vatTaxRate					= 0.20;
	double						vatForEachProduct			= 0.0;
	// for category method
	String						catName						= null;
	int							catId						= -1;
	int							defaultQuantity				= 1;
	int							previousQuantity			= 0;
	int							updatedQuantity				= 0;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		_saleListProductForListView = new ArrayList<ProductBO>();
		_saleListProductForGridView = new ArrayList<ProductBO>();
		_saleListCategoryForDisplay = new ArrayList<CategoryBO>();
		globalListOfProductConst = new ArrayList<ProductBO>();

		salesBOList = new ArrayList<SalesBO>();
		salesHistoryBOList = new ArrayList<SalesHistoryBO>();
		saleItemsBOList = new ArrayList<SaleItemsBO>();
		saleItemsHistoryBOList = new ArrayList<SaleItemsHistoryBO>();

		suspendedBillsListForSuspendedDialogListView = new ArrayList<SuspendedBillsBO>();

		catButtonsList = new ArrayList<Button>();
		paymentDialog = new Dialog( SaleActivity.this );
		suspendedSalesDialog = new Dialog( SaleActivity.this );

		// to get taxRate if the call to server is delayed.
		DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_TAX_RATE );
		if( Utils.taxRate.equals( "0.0" ) )
		{
			if( !db.getLastInsertedTaxRateRate().equals( "0.0" ) )
			{
				Utils.taxRate = db.getLastInsertedTaxRateRate();
				vatTaxRate = Double.valueOf( Utils.taxRate ) / 100;
			}
		}

		super.onCreate( savedInstanceState );
		setContentView( R.layout.sale_activity );
		paymentDialog.setContentView( R.layout.sale_activity_payment_dialog );
		suspendedSalesDialog.setContentView( R.layout.sale_activity_suspended_sales_dialog );

		gridProducts = ( GridView ) findViewById( R.id.sale_grid );
		listProducts = ( ListView ) findViewById( R.id.sale_listView );
		listSuspendedSales = ( ListView ) suspendedSalesDialog.findViewById( R.id.sale_activity_suspended_products_dialog_listView );

		parentLinearLayout = ( LinearLayout ) findViewById( R.id.ll2_right );
		paymentDialogRelativeLayoutCash1 = ( RelativeLayout ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_relativeLayout_payment_method_cash1 );
		paymentDialogRelativeLayoutCash2 = ( RelativeLayout ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_relativeLayout_payment_method_cash2 );
		paymentDialogRelativeLayoutChecque = ( RelativeLayout ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_relativeLayout_payment_method_checque1 );
		paymentDialogRelativeLayoutCreditCard1 = ( RelativeLayout ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_relativeLayout_payment_method_creditCard1 );
		paymentDialogRelativeLayoutCreditCard2 = ( RelativeLayout ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_relativeLayout_payment_method_creditCard2 );

		paymentDialogPaidEditText = ( EditText ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_editText_paid );

		dialogPaymentMethodSpinner = ( Spinner ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_spinner_payment_method );

		suspendButton = ( Button ) findViewById( R.id.sale_btnSuspend );
		paymentButton = ( Button ) findViewById( R.id.sale_btnPayment );
		cancelButton = ( Button ) findViewById( R.id.sale_btnCancel );
		paymentDialogSubmitButton = ( Button ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_submit_button );
		paymentDialogCloseButton = ( Button ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_cancel_button );

		totalPayableTextView = ( TextView ) findViewById( R.id.sale_totalPayableTextView );
		totalItemsTextView = ( TextView ) findViewById( R.id.sale_totalItemText );
		taxTextView = ( TextView ) findViewById( R.id.sale_taxText );
		discountTextView = ( TextView ) findViewById( R.id.sale_discountText );
		totalTextView = ( TextView ) findViewById( R.id.sale_totalText );
		vatTextView = ( TextView ) findViewById( R.id.sale_vatText );
		paymentDialogTotalPayableTextView = ( TextView ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_textView_total_paypable );
		paymentDialogTotalItemsTextView = ( TextView ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_textView_total_items );
		paymentDialogChangeTextView = ( TextView ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_textView_change );
		paymentDialogCreditCardNoEditText = ( EditText ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_editText_creditCardNo );
		paymentDialogCardHolderEditText = ( EditText ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_editText_holderName );
		paymentDialogChecqueNoEditText = ( EditText ) paymentDialog.findViewById( R.id.sale_activity_payment_dialog_editText_checqueNo );

		totalPayableTextView.setText( String.valueOf( totalPayable ) );
		totalItemsTextView.setText( String.valueOf( totalItem ) );
		taxTextView.setText( String.valueOf( tax ) );
		discountTextView.setText( String.valueOf( discount ) );
		totalTextView.setText( String.valueOf( total ) );
		vatTextView.setText( String.valueOf( vat ) );
		paymentDialog.setTitle( "Finalize Sale" );
		suspendedSalesDialog.setTitle( "Suspended Sales" );

		dialogPaymentMethodSpinner.setOnItemSelectedListener( new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected( AdapterView<?> adapter, View v, int position, long id )
			{

				String item = adapter.getItemAtPosition( position ).toString();

				if( item.equals( "Cash" ) )
				{
					paymentDialogRelativeLayoutCash1.setVisibility( View.VISIBLE );
					paymentDialogRelativeLayoutCash2.setVisibility( View.VISIBLE );
					paymentDialogRelativeLayoutChecque.setVisibility( View.GONE );
					paymentDialogRelativeLayoutCreditCard1.setVisibility( View.GONE );
					paymentDialogRelativeLayoutCreditCard2.setVisibility( View.GONE );
					paymentDialogPaidBy = "Cash";
				}
				if( item.equals( "Checque" ) )
				{
					paymentDialogRelativeLayoutChecque.setVisibility( View.VISIBLE );
					paymentDialogRelativeLayoutCash1.setVisibility( View.GONE );
					paymentDialogRelativeLayoutCash2.setVisibility( View.GONE );
					paymentDialogRelativeLayoutCreditCard1.setVisibility( View.GONE );
					paymentDialogRelativeLayoutCreditCard2.setVisibility( View.GONE );
					paymentDialogPaidBy = "checque";
				}
				if( item.equals( "Credit Card" ) )
				{
					paymentDialogRelativeLayoutCreditCard1.setVisibility( View.VISIBLE );
					paymentDialogRelativeLayoutCreditCard2.setVisibility( View.VISIBLE );
					paymentDialogRelativeLayoutCash1.setVisibility( View.GONE );
					paymentDialogRelativeLayoutCash2.setVisibility( View.GONE );
					paymentDialogRelativeLayoutChecque.setVisibility( View.GONE );
					paymentDialogPaidBy = "Credit Card";
				}
			}

			@Override
			public void onNothingSelected( AdapterView<?> arg0 )
			{

			}
		} );

		paymentDialogPaidEditText.addTextChangedListener( new TextWatcher()
		{

			@Override
			public void onTextChanged( CharSequence arg0, int arg1, int arg2, int arg3 )
			{

			}

			@Override
			public void beforeTextChanged( CharSequence arg0, int arg1, int arg2, int arg3 )
			{

			}

			@Override
			public void afterTextChanged( Editable arg0 )
			{

				if( !Utils.isNullOrEmpty( paymentDialogPaidEditText.getText().toString() ) )
				{
					paymentDialogPaid = Double.valueOf( paymentDialogPaidEditText.getText().toString() );

					DecimalFormat df = new DecimalFormat( "######.##" );

					if( paymentDialogPaid >= totalPayable )
					{
						paymentDialogPaid = Double.valueOf( paymentDialogPaidEditText.getText().toString() );
						paymentDialogChangeTextView.setText( String.valueOf( Double.valueOf( df.format( paymentDialogPaid - totalPayable ) ) ) );
					}
					else
						paymentDialogChangeTextView.setText( String.valueOf( "0" ) );
				}
			}
		} );

		paymentDialogCloseButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				paymentDialog.dismiss();
				paymentDialogCreditCardNo = "";
				paymentDialogCardHolder = "";
				paymentDialogChecqueNo = "";
				paymentDialogCardHolderEditText.setText( "" );
				paymentDialogCreditCardNoEditText.setText( "" );
				paymentDialogPaidEditText.setText( "" );
				paymentDialogChecqueNoEditText.setText( "" );
				paymentDialogChangeTextView.setText( "" );
				paymentDialogPaid = 0;
				paymentDialogPaidEditText.setText( "" );
			}
		} );

		paymentDialogSubmitButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				try
				{
					boolean paymentViaCredit = false;
					DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_TAX_RATE );
					int taxId = dbHandler.getLastInsertedTaxRateId();

					dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SALES );
					int salesId = dbHandler.getLastInsertedSalesId();
					salesId = salesId + 1;

					SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
					String salesDateToday = sdf.format( new Date() );
					String salesEventDateTime = ( DateFormat.format( "yyyy-MM-dd hh:mm:ss", new java.util.Date() ).toString() );

					if( paymentDialogPaidBy.equalsIgnoreCase( "Cash" ) )
					{

					}
					else if( paymentDialogPaidBy.equalsIgnoreCase( "Checque" ) )
					{
						paymentDialogChecqueNo = paymentDialogChecqueNoEditText.getText().toString();
						paymentDialogCreditCardNo = "";
						paymentDialogCardHolder = "";
						paymentDialogPaid = totalPayable;
					}
					else if( paymentDialogPaidBy.equalsIgnoreCase( "Credit Card" ) && paymentDialogCreditCardNoEditText.length() == 16 && !Utils.isNullOrEmpty( paymentDialogCardHolderEditText.getText().toString() ) )
					{
						// paymentViaCredit = true;
						paymentDialogChecqueNo = "";
						paymentDialogPaid = totalPayable;
						paymentDialogCreditCardNo = paymentDialogCreditCardNoEditText.getText().toString();
						paymentDialogCardHolder = paymentDialogCardHolderEditText.getText().toString();
					}
					// else
					// Toast.makeText( getApplicationContext(),
					// "Please Enter Correct Details", Toast.LENGTH_SHORT
					// ).show();

					if( paymentDialogPaid >= totalPayable )
					{
						SharedPreferences pref = Utils.getSharedPreferences( getApplicationContext() );
						String firstName = pref.getString( AppGlobal.APP_PREF_FIRST_NAME, "firstName" );
						String lastName = pref.getString( AppGlobal.APP_PREF_LAST_NAME, "lastName" );

						// for adding in sales table
						salesBO = new SalesBO( salesId, "SL-000" + salesId, 1, 1, "Customer One", 1, "Walk In", salesDateToday, "", "", new BigDecimal( String.valueOf( total ) ), new BigDecimal( 0 ), new BigDecimal( String.valueOf( totalPayable ) ), 0, "", new BigDecimal( String.valueOf( vat ) ), taxId, new BigDecimal( 0 ), 0, firstName + " " + lastName, "", paymentDialogPaidBy, totalItem, new BigDecimal( 0 ), 1, new BigDecimal( String.valueOf( paymentDialogPaid ) ), paymentDialogCreditCardNo, paymentDialogCardHolder, paymentDialogChecqueNo, AppGlobal.UNSYNC );
						dbHandler.addSales( salesBO );

						// for adding in salesHistory table
						dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SALES_HISTORY );
						int salesHistoryId = dbHandler.getLastInsertedSalesHistoryId();
						salesHistoryId = salesHistoryId + 1;

						salesHistoryBO = new SalesHistoryBO( salesHistoryId, salesId, "SL-000" + salesId, 1, 1, "Customer One", 1, "Walk In", salesDateToday, "", "", new BigDecimal( String.valueOf( total ) ), new BigDecimal( 0 ), new BigDecimal( String.valueOf( totalPayable ) ), 0, "", new BigDecimal( String.valueOf( vat ) ), taxId, new BigDecimal( 0 ), 0, "owner", "", paymentDialogPaidBy, totalItem, new BigDecimal( 0 ), 1, new BigDecimal( String.valueOf( paymentDialogPaid ) ), paymentDialogCreditCardNo, paymentDialogCardHolder, paymentDialogChecqueNo, salesEventDateTime, "insert", AppGlobal.UNSYNC );
						dbHandler.addSalesHistory( salesHistoryBO );

						// for adding in saleItems and saleItemsHistory tables
						dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SALE_ITEMS );
						DatabaseHandler db1 = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SALE_ITEMS_HISTORY );

						for( int i = 0 ; i < _saleListProductForListView.size() ; i++ )
						{
							int saleItemsId = dbHandler.getLastInsertedSaleItemsId();
							saleItemsId = saleItemsId + 1;

							int saleItemsHistoryId = db1.getLastInsertedSaleItemsHistoryId();
							saleItemsHistoryId = saleItemsHistoryId + 1;

							int productId = _saleListProductForListView.get( i ).getId();
							String productCode = _saleListProductForListView.get( i ).getCode();
							String productName = _saleListProductForListView.get( i ).getName();
							String productUnit = _saleListProductForListView.get( i ).getUnit();
							int quantity = _saleListProductForListView.get( i ).getqQuantity();
							DecimalFormat df1 = new DecimalFormat( "######.##" );
							double uPrice = Double.valueOf( df1.format( Double.valueOf( String.valueOf( _saleListProductForListView.get( i ).getPrice() ) ) / Double.valueOf( String.valueOf( quantity ) ) ) );
							BigDecimal unitPrice = new BigDecimal( String.valueOf( uPrice ) );

							// double gross = Double.valueOf( df1.format(
							// quantity *
							// Integer.parseInt( String.valueOf(
							// _saleListProductForListView.get( i ).getPrice() )
							// ) )
							// );
							BigDecimal grossTotal = new BigDecimal( String.valueOf( _saleListProductForListView.get( i ).getPrice() ) );

							saleItemsBO = new SaleItemsBO( saleItemsId, salesId, productId, productCode, productName, productUnit, taxId, "", quantity, unitPrice, grossTotal, new BigDecimal( 0 ), "", new BigDecimal( 0 ), "", 0, AppGlobal.UNSYNC );
							dbHandler.addSaleItems( saleItemsBO );

							saleItemsHistoryBO = new SaleItemsHistoryBO( saleItemsHistoryId, saleItemsId, salesId, productId, productCode, productName, productUnit, taxId, "", quantity, unitPrice, grossTotal, new BigDecimal( 0 ), "", new BigDecimal( 0 ), "", 0, salesEventDateTime, "insert", AppGlobal.UNSYNC );
							db1.addSaleItemsHistory( saleItemsHistoryBO );

						}

						if( paymentViaCredit )
						{
							try
							{
								Double d = Double.valueOf( totalPayable );
								int amount = d.intValue();
								PaylevenApi.configure( AppGlobal.PAYLEVEN_API_KEY );
								String description = "FusePOS demo payment";
								Bitmap image = BitmapFactory.decodeResource( getResources(), R.drawable.fuseposlogo );

								TransactionRequestBuilder builder = new TransactionRequestBuilder( amount, Currency.getInstance( "EUR" ) );
								builder.setDescription( description ).setBitmap( image );
								String email = "zaheer.ahmad590@gmail.com";
								if( !TextUtils.isEmpty( email ) )
								{
									builder.setEmail( email );
								}
								TransactionRequest request = builder.createTransactionRequest();
								// create a unique id for the payment.
								// For reasons of simplicity the UUID class is
								// used
								// here.
								// In a production environment it would be more
								// feasible
								// to
								// use an ascending numbering scheme
								String orderId = UUID.randomUUID().toString();
								PaylevenApi.initiatePayment( SaleActivity.this, orderId, request );
							}
							catch ( Exception e )
							{
								AlertDialog.Builder builder = new AlertDialog.Builder( SaleActivity.this );
								builder.setTitle( "Error!" ).setMessage( "Couldn't connect to Payleven device. Make sure it is connected with your tablet via bluetooth." );
								builder.setPositiveButton( "OK", new DialogInterface.OnClickListener()
								{
									public void onClick( DialogInterface dialog, int which )
									{

										// dismiss the dialog
									}
								} );
								builder.show();
								// Toast.makeText( getApplicationContext(),
								// "Could not connect to Payleven device",
								// Toast.LENGTH_LONG
								// ).show();
							}
						}

						paymentDialog.dismiss();

						AlertDialog.Builder builder = new AlertDialog.Builder( SaleActivity.this );
						builder.setTitle( "Done!" ).setMessage( "Payment Successful" );
						builder.setPositiveButton( "OK", new DialogInterface.OnClickListener()
						{
							public void onClick( DialogInterface dialog, int which )
							{

								// clear the entries
								_saleListProductForListView.clear();
								_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, _saleListProductForListView );
								listProducts.setAdapter( _saleListAdapter );
								_saleListAdapter.notifyDataSetChanged();

								totalPayable = 0.0;
								totalItem = 0;
								tax = 0.0;
								discount = 0.0;
								total = 0.0;
								vat = 0.0;
								vatForEachProduct = 0.0;
								defaultQuantity = 1;
								previousQuantity = 0;
								updatedQuantity = 0;

								totalPayableTextView.setText( String.valueOf( totalPayable ) );
								totalItemsTextView.setText( String.valueOf( totalItem ) );
								taxTextView.setText( String.valueOf( tax ) );
								discountTextView.setText( String.valueOf( discount ) );
								totalTextView.setText( String.valueOf( total ) );
								vatTextView.setText( String.valueOf( vat ) );

								paymentDialogCreditCardNo = "";
								paymentDialogCardHolder = "";
								paymentDialogChecqueNo = "";
								paymentDialogCardHolderEditText.setText( "" );
								paymentDialogCreditCardNoEditText.setText( "" );
								paymentDialogPaidEditText.setText( "" );
								paymentDialogChecqueNoEditText.setText( "" );
							}
						} );
						builder.show();
						db1.close();
						dbHandler.close();
					}
					else
						Toast.makeText( getApplicationContext(), "Paid amount is less than Payable Amount.", Toast.LENGTH_LONG ).show();
				}
				catch ( Exception e )
				{
					Toast.makeText( getApplicationContext(), "Something went wrong" + e.toString(), Toast.LENGTH_LONG ).show();
				}
			}

		} );

		cancelButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				if( _saleListProductForListView.size() > 0 )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( SaleActivity.this );
					builder.setTitle( "Order Cancelation" ).setMessage( "Are you sure you want to cancel the order?" );
					builder.setPositiveButton( "Yes", new DialogInterface.OnClickListener()
					{
						public void onClick( DialogInterface dialog, int which )
						{

							_saleListProductForListView.clear();
							_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, _saleListProductForListView );
							listProducts.setAdapter( _saleListAdapter );
							_saleListAdapter.notifyDataSetChanged();

							totalPayable = 0.0;
							totalItem = 0;
							tax = 0.0;
							discount = 0.0;
							total = 0.0;
							vat = 0.0;
							vatForEachProduct = 0.0;
							defaultQuantity = 1;
							previousQuantity = 0;
							updatedQuantity = 0;

							totalPayableTextView.setText( String.valueOf( totalPayable ) );
							totalItemsTextView.setText( String.valueOf( totalItem ) );
							taxTextView.setText( String.valueOf( tax ) );
							discountTextView.setText( String.valueOf( discount ) );
							totalTextView.setText( String.valueOf( total ) );
							vatTextView.setText( String.valueOf( vat ) );
						}
					} );
					builder.setNegativeButton( "No", new DialogInterface.OnClickListener()
					{

						@Override
						public void onClick( DialogInterface arg0, int arg1 )
						{

						}
					} );
					builder.show();
				}
				else
				{
					totalPayable = 0.0;
					totalItem = 0;
					tax = 0.0;
					discount = 0.0;
					total = 0.0;
					vat = 0.0;
					vatForEachProduct = 0.0;
					defaultQuantity = 1;
					previousQuantity = 0;
					updatedQuantity = 0;

					totalPayableTextView.setText( String.valueOf( totalPayable ) );
					totalItemsTextView.setText( String.valueOf( totalItem ) );
					taxTextView.setText( String.valueOf( tax ) );
					discountTextView.setText( String.valueOf( discount ) );
					totalTextView.setText( String.valueOf( total ) );
					vatTextView.setText( String.valueOf( vat ) );
				}
			}
		} );

		suspendButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				if( _saleListProductForListView.size() > 0 )
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( SaleActivity.this );
					builder.setTitle( "Suspend Sale!" ).setMessage( "Are you sure you want to suspend Sale?" );
					builder.setPositiveButton( "OK", new DialogInterface.OnClickListener()
					{
						public void onClick( DialogInterface dialog, int which )
						{

							SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
							String suspendedBillsDate = sdf.format( new Date() );

							DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_BILLS );
							int suspendedBillsId = dbHandler.getLastInsertedSuspendedBillsId();
							suspendedBillsId = suspendedBillsId + 1;

							SuspendedBillsBO suspendedBillsBO = new SuspendedBillsBO( suspendedBillsId, suspendedBillsDate, 1, totalItem, ( float ) 0.0, ( float ) vat, new BigDecimal( discount ), new BigDecimal( total ), ( float ) totalPayable, AppGlobal.UNSYNC, String.valueOf( 0 ) );
							dbHandler.addSuspendedBills( suspendedBillsBO );

							dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_SALES );

							int id = dbHandler.getLastInsertedSuspendedSalesId();
							// id = id + 1;

							DecimalFormat df1 = new DecimalFormat( "######.##" );

							for( int i = 0 ; i < _saleListProductForListView.size() ; i++ )
							{
								double uPrice = Double.valueOf( df1.format( Double.valueOf( String.valueOf( _saleListProductForListView.get( i ).getPrice() ) ) / Double.valueOf( String.valueOf( _saleListProductForListView.get( i ).getqQuantity() ) ) ) );
								BigDecimal unitPrice = new BigDecimal( String.valueOf( uPrice ) );

								id = id + 1;
								suspendedSalesBO = new SuspendedSalesBO( id, suspendedBillsId, _saleListProductForListView.get( i ).getId(), _saleListProductForListView.get( i ).getCode(), _saleListProductForListView.get( i ).getName(), _saleListProductForListView.get( i ).getUnit(), 0/* taxid */, "", _saleListProductForListView.get( i ).getqQuantity(), unitPrice, _saleListProductForListView.get( i ).getPrice(), new BigDecimal( 0.00 ), "", 0, new BigDecimal( 0.00 ), "", AppGlobal.UNSYNC );
								dbHandler.addSuspendedSales( suspendedSalesBO );
							}

							_saleListProductForListView.clear();
							_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, _saleListProductForListView );
							listProducts.setAdapter( _saleListAdapter );
							_saleListAdapter.notifyDataSetChanged();

							totalPayable = 0.0;
							totalItem = 0;
							tax = 0.0;
							discount = 0.0;
							total = 0.0;
							vat = 0.0;
							vatForEachProduct = 0.0;
							defaultQuantity = 1;
							previousQuantity = 0;
							updatedQuantity = 0;

							totalPayableTextView.setText( String.valueOf( totalPayable ) );
							totalItemsTextView.setText( String.valueOf( totalItem ) );
							taxTextView.setText( String.valueOf( tax ) );
							discountTextView.setText( String.valueOf( discount ) );
							totalTextView.setText( String.valueOf( total ) );
							vatTextView.setText( String.valueOf( vat ) );

							dbHandler.close();
						}
					} );
					builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener()
					{

						@Override
						public void onClick( DialogInterface arg0, int arg1 )
						{

						}
					} );
					builder.show();
				}
				else
					Toast.makeText( getApplicationContext(), "Cannot Suspend. List is empty!", Toast.LENGTH_LONG ).show();
			}
		} );

		paymentButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				if( _saleListProductForListView.size() > 0 )
				{
					paymentDialogTotalPayableTextView.setText( String.valueOf( totalPayable ) );
					paymentDialogTotalItemsTextView.setText( String.valueOf( totalItem ) );

					paymentDialog.show();
				}
				else
				{
					AlertDialog.Builder builder = new AlertDialog.Builder( SaleActivity.this );
					builder.setTitle( "Error!" ).setMessage( "Cannot initiate Payment. List Empty!" );
					builder.setPositiveButton( "OK", new DialogInterface.OnClickListener()
					{
						public void onClick( DialogInterface dialog, int which )
						{

						}
					} );
					builder.show();
				}
			}
		} );

		bindProducts();
		bindCategory();
	}

	public void bindCategory()
	{

		DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_CATEGORY );
		_saleListCategoryForDisplay = dbHandler.getAllCategory();

		dbHandler.close();

		int count = 0;
		LinearLayout childLinearLayout = null;
		for( int i = 0 ; i < _saleListCategoryForDisplay.size() ; i++ )
		{
			if( count == 0 || count == 4 )
			{
				if( count == 4 )
				{
					count = 0;
					parentLinearLayout.addView( childLinearLayout );
					childLinearLayout = null;
				}
				childLinearLayout = new LinearLayout( getApplicationContext() );
				childLinearLayout.setOrientation( LinearLayout.HORIZONTAL );
			}
			categoryButton = new Button( this );
			catName = _saleListCategoryForDisplay.get( i ).getName().toString();
			catId = _saleListCategoryForDisplay.get( i ).getId();

			categoryButton.setText( catName );
			categoryButton.setTag( catId );
			categoryButton.setTextColor( Color.parseColor( "#ffffff" ) );
			categoryButton.setBackgroundColor( Color.parseColor( "#f6b79d" ) );
			categoryButton.setOnClickListener( new OnClickListener()
			{
				@Override
				public void onClick( View v )
				{

					int catId = Integer.parseInt( v.getTag().toString() );
					// Log.d( "catID", String.valueOf( catId ) );

					DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
					_saleListProductForGridView = dbHandler.getProductswithCategoryId( catId );

					Button b = ( Button ) v;
					b.setTextColor( Color.parseColor( "#000000" ) );
					b.setBackgroundColor( Color.parseColor( "#ffffff" ) );
					for( int j = 0 ; j < catButtonsList.size() ; j++ )
					{
						if( catButtonsList.get( j ).getTag().toString() != v.getTag().toString() )
						{
							catButtonsList.get( j ).setTextColor( Color.parseColor( "#ffffff" ) );
							catButtonsList.get( j ).setBackgroundColor( Color.parseColor( "#f6b79d" ) );
						}
					}
					// needs to be fixed
					_saleGridAdapter = new SaleGridAdapter( getApplicationContext(), -1, _saleListProductForGridView );
					gridProducts.setAdapter( _saleGridAdapter );
					dbHandler.close();
				}
			} );

			LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1 );
			lp.rightMargin = 5;

			childLinearLayout.addView( categoryButton, lp );
			count++;
			catButtonsList.add( categoryButton );
		}
		if( childLinearLayout != null )
			parentLinearLayout.addView( childLinearLayout );
	}

	public void bindProducts()
	{

		DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
		_saleListProductForGridView = dbHandler.getAllProducts();
		dbHandler.close();

		if( _saleListProductForGridView != null && _saleListProductForGridView.size() > 0 )
		{
			globalListOfProductConst = _saleListProductForGridView;
			_saleGridAdapter = new SaleGridAdapter( getApplicationContext(), -1, _saleListProductForGridView );
			gridProducts.setAdapter( _saleGridAdapter );
		}

		if( _saleListProductForListView != null )
		{
			_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, _saleListProductForListView );
			listProducts.setAdapter( _saleListAdapter );
		}

		if( !DataSendService.isServiceRunning )
		{

			final Calendar TIME = Calendar.getInstance();
			TIME.set( Calendar.MINUTE, 0 );
			TIME.set( Calendar.SECOND, 0 );
			TIME.set( Calendar.MILLISECOND, 0 );

			final AlarmManager m = ( AlarmManager ) getApplicationContext().getSystemService( Context.ALARM_SERVICE );
			final Intent i = new Intent( getApplicationContext(), DataSendService.class );
			PendingIntent serviceIntent = PendingIntent.getService( getApplicationContext(), 0, i, PendingIntent.FLAG_CANCEL_CURRENT );
			m.setRepeating( AlarmManager.RTC, TIME.getTime().getTime(), AppGlobal.SERVICE_DELAY, serviceIntent );

			DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_TAX_RATE );

			Utils.taxRate = db.getLastInsertedTaxRateRate();
		}
	}

	public class SaleGridAdapter extends ArrayAdapter<ProductBO>
	{

		Context			context;
		List<ProductBO>	productList;

		public SaleGridAdapter( Context context, int resource, List<ProductBO> list )
		{

			super( context, resource, list );
			this.context = context;
			this.productList = list;
		}

		@Override
		public View getView( int position, View convertView, ViewGroup parent )
		{

			ViewHolder holder;
			View view = convertView;
			if( view == null )
			{
				view = getLayoutInflater().inflate( R.layout.sale_grid_item, parent, false );
			}
			holder = new ViewHolder();

			holder.productImage = ( ImageView ) view.findViewById( R.id.sale_grid_productImage );
			holder.productName = ( TextView ) view.findViewById( R.id.sale_grid_productName );
			holder.productId = ( TextView ) view.findViewById( R.id.sale_grid_productId );

			holder.productName.setText( this.productList.get( position ).getName().trim() );
			holder.productId.setText( String.valueOf( this.productList.get( position ).getId() ) );

			LinearLayout ll = ( LinearLayout ) view.findViewById( R.id.sale_grid_ll_parent );
			ll.setOnClickListener( new View.OnClickListener()
			{

				@Override
				public void onClick( View v )
				{

					try
					{
						// TODO Auto-generated method stub
						LinearLayout ll = ( LinearLayout ) v;
						int productId = Integer.parseInt( ( ( TextView ) ll.getChildAt( 2 ) ).getText().toString() );

						// Getting ProductId, as I know in layout I set 2nd
						// child of LinearLayout as ProductId

						DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
						ProductBO productBO = dbHandler.getProduct( productId );
						dbHandler.close();
						vatTaxRate = Double.valueOf( Utils.taxRate ) / 100;
						if( vatTaxRate != 0.0 )
						{
							if( productBO != null )
							{

								// Updated Calculations here..
								vatForEachProduct = ( defaultQuantity * productBO.getPrice().doubleValue() ) * vatTaxRate;

								vat += vatForEachProduct;
								totalItem++;
								total += productBO.getPrice().doubleValue();
								totalPayable = total + vat + tax;

								totalItemsTextView.setText( String.valueOf( totalItem ) );
								totalTextView.setText( String.valueOf( total ) );
								totalPayableTextView.setText( String.valueOf( totalPayable ) );
								vatTextView.setText( String.valueOf( vat ) );

								_saleListProductForListView.add( productBO );
								_saleListAdapter.notifyDataSetChanged();

							}
						}
						else
						{
							Toast.makeText( getApplicationContext(), "Please Wait till Sync Completes.", Toast.LENGTH_SHORT ).show();
							vatTaxRate = Double.valueOf( Utils.taxRate ) / 100;
						}
					}
					catch ( Exception ex )
					{
						System.out.println( ex.getMessage() );
					}
				}
			} );

			view.setTag( holder );
			return view;
		}

		class ViewHolder
		{
			ImageView	productImage;
			TextView	productName;
			TextView	productId;
		}
	}

	public class SaleListAdapter extends ArrayAdapter<ProductBO>
	{

		Context			context;
		List<ProductBO>	productList;

		public SaleListAdapter( Context context, int resource, List<ProductBO> list )
		{

			super( context, resource, list );
			this.context = context;
			this.productList = list;
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView( final int position, View convertView, ViewGroup parent )
		{

			final ViewHolder holder;
			View view = convertView;
			if( view == null )
			{
				view = getLayoutInflater().inflate( R.layout.sale_list_item, parent, false );
			}
			holder = new ViewHolder();

			holder.deleteBtn = ( SAutoBgButton ) view.findViewById( R.id.sale_list_deletebtn );
			holder.productName = ( TextView ) view.findViewById( R.id.sale_list_productText );
			holder.productPrice = ( TextView ) view.findViewById( R.id.sale_list_productPriceText );
			holder.productQuantity = ( EditText ) view.findViewById( R.id.sale_list_productQttyText );
			holder.productId = ( TextView ) view.findViewById( R.id.sale_list_hidden_productId );

			holder.productName.setText( this.productList.get( position ).getName().trim() );
			holder.productPrice.setText( String.valueOf( this.productList.get( position ).getPrice() ) );
			holder.productQuantity.setText( String.valueOf( this.productList.get( position ).getqQuantity() ) );
			holder.productId.setText( String.valueOf( this.productList.get( position ).getId() ) );

			holder.deleteBtn.setOnClickListener( new View.OnClickListener()
			{

				@Override
				public void onClick( View v )
				{

					LinearLayout ll = ( LinearLayout ) v.getParent();
					LinearLayout ll1 = ( LinearLayout ) ll.getParent();
					LinearLayout lll = ( LinearLayout ) ll1.getParent();
					TextView tvId = ( TextView ) lll.getChildAt( 0 );
					TextView productPriceTextView = ( TextView ) ( ( LinearLayout ) ( ( LinearLayout ) lll.getChildAt( 2 ) ).getChildAt( 2 ) ).getChildAt( 0 );
					EditText currentQuantityTextView = ( EditText ) ( ( LinearLayout ) ( ( LinearLayout ) lll.getChildAt( 2 ) ).getChildAt( 1 ) ).getChildAt( 0 );

					String currentQuantity = currentQuantityTextView.getText().toString();
					String productPrice = productPriceTextView.getText().toString();

					ProductBO p = new ProductBO();

					vatForEachProduct = Double.valueOf( productPrice ) * vatTaxRate;
					DecimalFormat df1 = new DecimalFormat( "######.##" );
					vatForEachProduct = Double.valueOf( df1.format( vatForEachProduct ) );
					vat = vat - vatForEachProduct;

					DecimalFormat df = new DecimalFormat( "######.##" );
					vat = Double.valueOf( df.format( vat ) );
					totalItem = totalItem - Integer.parseInt( currentQuantity );
					total = total - Double.valueOf( productPrice );
					totalPayable = total + vat + tax;

					totalItemsTextView.setText( String.valueOf( totalItem ) );
					totalTextView.setText( String.valueOf( total ) );
					totalPayableTextView.setText( String.valueOf( totalPayable ) );

					vatTextView.setText( String.valueOf( vat ) );

					p.deleteProductFromList( Integer.parseInt( tvId.getText().toString() ), productList );

					_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, productList );
					listProducts.setAdapter( _saleListAdapter );
					_saleListAdapter.notifyDataSetChanged();

				}
			} );

			holder.productQuantity.addTextChangedListener( new TextWatcher()
			{

				@Override
				public void onTextChanged( CharSequence s, int start, int before, int count )
				{

				}

				@Override
				public void beforeTextChanged( CharSequence s, int start, int count, int after )
				{

					if( !Utils.isNullOrEmpty( holder.productQuantity.getText().toString() ) )
					{
						previousQuantity = Integer.parseInt( holder.productQuantity.getText().toString() );
					}
				}

				@Override
				public void afterTextChanged( Editable s )
				{

					if( !Utils.isNullOrEmpty( holder.productQuantity.getText().toString() ) )
					{

						ProductBO productBO = getProductBO( Integer.parseInt( holder.productId.getText().toString() ), globalListOfProductConst );
						if( productBO != null )
						{
							BigDecimal productPrice = productBO.getPrice();

							// to get updated quantity
							updatedQuantity = Integer.parseInt( holder.productQuantity.getText().toString() );

							double updatedPrice = updatedQuantity * productPrice.doubleValue();

							// to check if the previous quantity is greater than
							// updated quantity
							if( updatedQuantity != previousQuantity || !Utils.isNullOrEmpty( String.valueOf( updatedQuantity ) ) || updatedQuantity > 0 )
							{
								// to update in the main list
								productList.get( position ).setqQuantity( updatedQuantity );
								productList.get( position ).setPrice( new BigDecimal( updatedPrice ) );

								int totalItemAfterQuantityUpdated = 0;
								double totalAfterQuantityUpdated = 0.0;
								double vatAfterQuantityUpdated = 0.0;

								for( int z = 0 ; z < productList.size() ; z++ )
								{
									totalItemAfterQuantityUpdated += productList.get( z ).getqQuantity();
									totalAfterQuantityUpdated += productList.get( z ).getPrice().doubleValue();
									vatAfterQuantityUpdated += productList.get( z ).getPrice().doubleValue() * vatTaxRate;
								}

								DecimalFormat df = new DecimalFormat( "######.##" );
								vat = Double.valueOf( df.format( vatAfterQuantityUpdated ) );

								totalItem = totalItemAfterQuantityUpdated;
								total = totalAfterQuantityUpdated;
								totalPayable = total + vat + tax;

								totalItemsTextView.setText( String.valueOf( totalItem ) );
								totalTextView.setText( String.valueOf( total ) );
								totalPayableTextView.setText( String.valueOf( totalPayable ) );

								vatTextView.setText( String.valueOf( vat ) );

								_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, productList );
								listProducts.setAdapter( _saleListAdapter );
								_saleListAdapter.notifyDataSetChanged();
							}
							else
							{
								Toast.makeText( getApplicationContext(), "Enter correct Quantity", Toast.LENGTH_LONG ).show();
							}

						}
					}
				}
			} );

			view.setTag( holder );
			return view;
		}

		class ViewHolder
		{
			SAutoBgButton	deleteBtn;
			TextView		productName;
			EditText		productQuantity;
			TextView		productPrice;
			TextView		productId;
		}

	}

	public class SuspendedListAdapter extends ArrayAdapter<SuspendedBillsBO>
	{
		Context					context;
		List<SuspendedBillsBO>	suspendedList;

		public SuspendedListAdapter( Context context, int resource, List<SuspendedBillsBO> list )
		{

			super( context, resource, list );
			this.context = context;
			this.suspendedList = list;
		}

		@Override
		public View getView( final int position, View convertView, ViewGroup parent )
		{

			final ViewHolder holder;
			View view = convertView;
			if( view == null )
			{
				view = getLayoutInflater().inflate( R.layout.suspended_sales_list, parent, false );
			}
			holder = new ViewHolder();

			holder.suspendedSaleDate = ( TextView ) view.findViewById( R.id.suspended_sales_list_date_textView );
			holder.suspendedSaleItems = ( TextView ) view.findViewById( R.id.suspended_sales_list_items_textView );
			holder.suspendedSaleProductTax = ( TextView ) view.findViewById( R.id.suspended_sales_list_product_tax_textView );
			holder.suspendedSaleInvoiceTax = ( TextView ) view.findViewById( R.id.suspended_sales_list_invoice_tax_textView );
			holder.suspendedSaleDiscount = ( TextView ) view.findViewById( R.id.suspended_sales_list_discount_textView );
			holder.suspendedSaleTotal = ( TextView ) view.findViewById( R.id.suspended_sales_list_total_textView );
			holder.suspendedSaleAddbutton = ( Button ) view.findViewById( R.id.suspended_sales_list_button_add );
			holder.suspendedSaleDeleteButton = ( Button ) view.findViewById( R.id.suspended_sales_list_button_delete );

			holder.suspendedSaleDate.setText( suspendedList.get( position ).getDate() );
			holder.suspendedSaleItems.setText( String.valueOf( suspendedList.get( position ).getCount() ) );
			holder.suspendedSaleProductTax.setText( String.valueOf( suspendedList.get( position ).getTax1() ) );
			holder.suspendedSaleInvoiceTax.setText( String.valueOf( suspendedList.get( position ).getTax2() ) );
			holder.suspendedSaleDiscount.setText( suspendedList.get( position ).getDiscount().toPlainString() );
			holder.suspendedSaleTotal.setText( String.valueOf( suspendedList.get( position ).getTotal() ) );
			holder.suspendedSaleAddbutton.setOnClickListener( new OnClickListener()
			{
				@Override
				public void onClick( View v )
				{

					_saleListProductForListView.clear();
					int id = suspendedList.get( position ).getId();
					DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_SALES );
					List<SuspendedSalesBO> suspendedSalesBOList = db.getSuspendedSalesFromSuspendId( id );

					db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
					for( int i = 0 ; i < suspendedSalesBOList.size() ; i++ )
					{
						ProductBO productBO = db.getProduct( suspendedSalesBOList.get( i ).getProductId() );
						productBO.setPrice( suspendedSalesBOList.get( i ).getGrossTotal() );
						productBO.setqQuantity( suspendedSalesBOList.get( i ).getQuantity() );

						_saleListProductForListView.add( productBO );
					}

					_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, _saleListProductForListView );
					listProducts.setAdapter( _saleListAdapter );
					_saleListAdapter.notifyDataSetChanged();

					DecimalFormat df1 = new DecimalFormat( "######.##" );

					vat = Double.valueOf( Float.toString( suspendedList.get( position ).getTax2() ) );
					totalItem = suspendedList.get( position ).getCount();
					totalPayable = Double.valueOf( Float.toString( suspendedList.get( position ).getTotal() ) );
					total = Double.valueOf( suspendedList.get( position ).getInvTotal().toPlainString() );

					totalItemsTextView.setText( String.valueOf( totalItem ) );
					totalTextView.setText( String.valueOf( total ) );
					totalPayableTextView.setText( String.valueOf( df1.format( totalPayable ) ) );
					vatTextView.setText( String.valueOf( vat ) );
					if( suspendedList.get( position ).getStatus().equals( AppGlobal.SYNC ) )
					{
						DatabaseHandler db1 = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_BILLS );
						int effectedRow = db1.updateSuspendedBillsStatus( suspendedList.get( position ) );
					}
					else
					{

						db.deleteSuspendedBills( id );

						db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_SALES );
						db.deleteSuspendedSales( id );
					}
					suspendedList.remove( position );
					suspendedSalesDialog.dismiss();
				}
			} );
			holder.suspendedSaleDeleteButton.setOnClickListener( new OnClickListener()
			{
				@Override
				public void onClick( View v )
				{

					int id = suspendedList.get( position ).getId();
					DatabaseHandler db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_BILLS );
					// db.deleteSuspendedBills( id );

					if( suspendedList.get( position ).getStatus().equals( AppGlobal.SYNC ) )
					{
						DatabaseHandler db1 = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_BILLS );
						int effectedRow = db1.updateSuspendedBillsStatus( suspendedList.get( position ) );
						suspendedList.remove( position );
					}
					else
					{

						db.deleteSuspendedBills( id );

						db = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_SALES );
						db.deleteSuspendedSales( id );
						suspendedList.remove( position );
					}
					_suspendedListAdapter.notifyDataSetChanged();
				}
			} );
			view.setTag( holder );
			return view;
		}

		class ViewHolder
		{
			TextView	suspendedSaleDate;
			TextView	suspendedSaleItems;
			TextView	suspendedSaleProductTax;
			TextView	suspendedSaleInvoiceTax;
			TextView	suspendedSaleDiscount;
			TextView	suspendedSaleTotal;
			Button		suspendedSaleAddbutton;
			Button		suspendedSaleDeleteButton;
		}
	}

	public ProductBO getProductBO( int productId, List<ProductBO> prodList )
	{

		for( int i = 0 ; i < prodList.size() ; i++ )
		{
			if( prodList.get( i ).getId() == productId )
			{
				return prodList.get( i );
			}

		}
		return null;

	}

	@Override
	protected void onActivityResult( int requestCode, int resultCode, final Intent data )
	{

		Log.i( "payment processing", "onActivityResult" );
		if( resultCode != 0 )
		{
			super.onActivityResult( requestCode, resultCode, data );
			// handle response in ResultActivity
			Intent i = new Intent( SaleActivity.this, ResultActivity.class );
			if( data != null )
			{
				i.putExtra( "result", data.getExtras() );
			}
			i.putExtra( "request_code", requestCode );
			startActivity( i );
		}
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{

		switch ( item.getItemId() )
		{
			case R.id.action_suspended:
				vatTaxRate = Double.valueOf( Utils.taxRate ) / 100;
				if( vatTaxRate != 0.0 )
				{
					DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPENDED_BILLS );
					suspendedBillsListForSuspendedDialogListView = dbHandler.getAllSuspendedSalesForAdapter();
					dbHandler.close();

					if( suspendedBillsListForSuspendedDialogListView != null && suspendedBillsListForSuspendedDialogListView.size() > 0 )
					{
						_suspendedListAdapter = new SuspendedListAdapter( getApplicationContext(), -1, suspendedBillsListForSuspendedDialogListView );
						listSuspendedSales.setAdapter( _suspendedListAdapter );
						suspendedSalesDialog.show();
					}
					else
						Toast.makeText( getApplicationContext(), "No Suspended Sales", Toast.LENGTH_SHORT ).show();
				}
				else
				{
					Toast.makeText( getApplicationContext(), "Please Wait till Sync Completes.", Toast.LENGTH_SHORT ).show();
					vatTaxRate = Double.valueOf( Utils.taxRate ) / 100;
				}
				return true;
		}
		return super.onOptionsItemSelected( item );
	}

	@Override
	public boolean onPrepareOptionsMenu( Menu menu )
	{

		syncMenu.setEnabled( Utils.isSynchronizing );
		syncMenu.setVisible( Utils.isSynchronizing );

		return super.onPrepareOptionsMenu( menu );

	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{

		MenuInflater inflater = getMenuInflater();
		inflater.inflate( R.menu.main, menu );

		syncMenu = ( MenuItem ) menu.findItem( R.id.action_sync );
		return true;
	}

	@Override
	protected void onPause()
	{

		super.onPause();
		unregisterReceiver( cloudBroadcastReceiver );
	}

	@Override
	protected void onResume()
	{

		super.onResume();
		registerReceiver( cloudBroadcastReceiver, new IntentFilter( AppGlobal.BROADCAST_CLOUD ) );
	}

	private BroadcastReceiver	cloudBroadcastReceiver	= new BroadcastReceiver()
														{
															@Override
															public void onReceive( Context context, Intent intent )
															{

																invalidateOptionsMenu();
															}
														};

}

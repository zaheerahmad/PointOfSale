/**
 * 
 */
package com.fusepos.activity;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fusepos.datalayer.CategoryBO;
import com.fusepos.datalayer.DatabaseHandler;
import com.fusepos.datalayer.ProductBO;
import com.fusepos.service.DataSendService;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.SAutoBgButton;
import com.fusepos.utils.Utils;
import com.google.gson.Gson;

/**
 * @author Zaheer Ahmad
 * 
 */
public class SaleActivity extends Activity
{
	List<ProductBO>			_saleListProductForListView;
	List<ProductBO>			_saleListProductForGridView;
	List<CategoryBO>		_saleListCategoryForDisplay;
	SaleListAdapter			_saleListAdapter;
	SaleGridAdapter			_saleGridAdapter;

	GridView				gridProducts;
	ListView				listProducts;

	LinearLayout			parentLinearLayout;
	ProgressDialog			loadingDialog;

	TextView				totalPayableTextView;
	TextView				totalItemsTextView;
	TextView				taxTextView;
	TextView				discountTextView;
	TextView				totalTextView;
	TextView				vatTextView;

	// EditText quantityEditText;

	SAutoBgButton			categoryButton;
	SAutoBgButton			suspendButton;

	double					totalPayable				= 0.0;
	int						totalItem					= 0;
	double					tax							= 0.0;
	double					discount					= 0.0;
	double					total						= 0.0;
	double					vat							= 0.0;
	double					vatForEachProduct			= 0.0;
	String					catName						= null;
	int						catId						= -1;
	int						defaultQuantity				= 1;
	int						previousQuantity			= 0;
	int						updatedQuantity				= 0;
	private List<ProductBO>	globalListOfProductConst	= null;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		_saleListProductForListView = new ArrayList<ProductBO>();
		_saleListProductForGridView = new ArrayList<ProductBO>();
		_saleListCategoryForDisplay = new ArrayList<CategoryBO>();
		globalListOfProductConst = new ArrayList<ProductBO>();

		super.onCreate( savedInstanceState );
		setContentView( R.layout.sale_activity );

		gridProducts = ( GridView ) findViewById( R.id.sale_grid );
		listProducts = ( ListView ) findViewById( R.id.sale_listView );

		parentLinearLayout = ( LinearLayout ) findViewById( R.id.ll2_right );

		suspendButton = ( SAutoBgButton ) findViewById( R.id.sale_btnSuspend );

		totalPayableTextView = ( TextView ) findViewById( R.id.sale_totalPayableTextView );
		totalItemsTextView = ( TextView ) findViewById( R.id.sale_totalItemText );
		taxTextView = ( TextView ) findViewById( R.id.sale_taxText );
		discountTextView = ( TextView ) findViewById( R.id.sale_discountText );
		totalTextView = ( TextView ) findViewById( R.id.sale_totalText );
		vatTextView = ( TextView ) findViewById( R.id.sale_vatText );

		totalPayableTextView.setText( String.valueOf( totalPayable ) );
		totalItemsTextView.setText( String.valueOf( totalItem ) );
		taxTextView.setText( String.valueOf( tax ) );
		discountTextView.setText( String.valueOf( discount ) );
		totalTextView.setText( String.valueOf( total ) );
		vatTextView.setText( String.valueOf( vat ) );

		// quantityEditText = ( EditText ) findViewById(
		// R.id.sale_list_productQttyText );

		bindProducts();
		bindCategory();

		suspendButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick( View arg0 )
			{

				Gson gson = new Gson();
				String suspendProductJson = gson.toJson( _saleListProductForListView );

				SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy" );
				String suspendProductDate = sdf.format( new Date() );

				DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_SUSPEND_PRODUCT );
				dbHandler.addSuspendProduct( suspendProductJson, suspendProductDate );

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

				// Log.d( "getall", dbHandler.getAllSuspendProduct().toString()
				// );
				dbHandler.close();

			}
		} );

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

			categoryButton = new SAutoBgButton( this );
			catName = _saleListCategoryForDisplay.get( i ).getName().toString();
			catId = _saleListCategoryForDisplay.get( i ).getId();

			categoryButton.setText( catName );
			categoryButton.setTag( catId );
			categoryButton.setOnClickListener( new OnClickListener()
			{
				@Override
				public void onClick( View v )
				{

					int catId = Integer.parseInt( v.getTag().toString() );
					Log.d( "catID", String.valueOf( catId ) );
					DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
					_saleListProductForGridView = dbHandler.getProductswithCategoryId( catId );

					// needs to be fixed
					_saleGridAdapter = new SaleGridAdapter( getApplicationContext(), -1, _saleListProductForGridView );
					gridProducts.setAdapter( _saleGridAdapter );
					dbHandler.close();

					// _saleGridAdapter.notifyDataSetChanged();

				}
			} );

			LayoutParams lp = new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1 );

			childLinearLayout.addView( categoryButton, lp );
			count++;
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
			// TODO Auto-generated constructor stub
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
						int productId = Integer.parseInt( ( ( TextView ) ll.getChildAt( 2 ) ).getText().toString() ); // Getting
																														// product
																														// Id,
																														// as
																														// I
																														// know
																														// in
																														// layout
																														// I
																														// set
																														// 2nd
																														// child
																														// of
																														// Linear
																														// Layout
																														// as
																														// Product
																														// Id

						DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
						ProductBO productBO = dbHandler.getProduct( productId );
						dbHandler.close();
						if( productBO != null )
						{
							if( productBO.isListHasProduct( productBO, _saleListProductForListView ) )
							{
								if( AppGlobal.isDebugMode )
									Toast.makeText( getApplicationContext(), "Product already added in List!", Toast.LENGTH_SHORT ).show();
							}
							else
							{
								// Updated Calculations here..
								vatForEachProduct = ( defaultQuantity * productBO.getPrice().doubleValue() ) * 0.20;

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

			// previousQuantity = Integer.parseInt(
			// holder.productQuantity.getText().toString() );
			// VAT hardcoded, needs to be fixed
			// vatForEachProduct = (previousQuantity * Integer.parseInt(
			// holder.ProductPrice.getText().toString())) * 0.20 ;

			// vat += vatForEachProduct;
			// vatTextView.setText( String.valueOf( vat ) );

			holder.deleteBtn.setOnClickListener( new View.OnClickListener()
			{

				@Override
				public void onClick( View v )
				{

					// TODO Auto-generated method stub
					LinearLayout ll = ( LinearLayout ) v.getParent();
					LinearLayout ll1 = ( LinearLayout ) ll.getParent();
					TextView tvId = ( TextView ) ll1.getChildAt( 0 );
					TextView productPriceTextView = ( TextView ) ( ( LinearLayout ) ll1.getChildAt( 4 ) ).getChildAt( 0 );
					EditText currentQuantityTextView = ( EditText ) ( ( LinearLayout ) ll1.getChildAt( 3 ) ).getChildAt( 0 );

					String currentQuantity = currentQuantityTextView.getText().toString();
					String productPrice = productPriceTextView.getText().toString();

					ProductBO p = new ProductBO();

					// if( p.deleteProductFromList( Integer.parseInt(
					// tvId.getText().toString() ), productList ) )
					// {
					/*
					 * DatabaseHandler dbHandler = new DatabaseHandler(
					 * getApplicationContext(), AppGlobal.TABLE_PRODUCT );
					 * ProductBO productBO = dbHandler.getProduct(
					 * Integer.parseInt( tvId.getText().toString() ) );
					 * dbHandler.close();
					 */

					// int qQuantity = productList.get( position
					// ).getqQuantity();
					// BigDecimal productPricetoDelete = productList.get(
					// position ).getPrice();

					// we can also do productBO.getPrice()* qQuantity in
					// vatForEachProduct ;
					vatForEachProduct = Double.valueOf( productPrice ) * 0.20;
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
					// }
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
							// updated
							// quantity
							if( updatedQuantity != previousQuantity || !Utils.isNullOrEmpty( String.valueOf( updatedQuantity ) ) || updatedQuantity > 0 )
							{
								// to update in the main list
								productList.get( position ).setqQuantity( updatedQuantity );
								productList.get( position ).setPrice( new BigDecimal( updatedPrice ) );

								int totalIteml = 0;
								double totall = 0.0;
								double vatl = 0.0;

								for( int z = 0 ; z < productList.size() ; z++ )
								{
									totalIteml += productList.get( z ).getqQuantity();
									totall += productList.get( z ).getPrice().doubleValue();
									vatl += productList.get( z ).getPrice().doubleValue() * 0.20;
								}

								/*
								 * int updatedQuantityMultiply = updatedQuantity
								 * - previousQuantity;
								 * // int updatedQuantityMultiply =
								 * // --updatedQuantity;
								 * vatForEachProduct = ( updatedQuantityMultiply
								 * * productPrice.doubleValue() ) * 0.20;
								 */// String.format("%.2f", vatForEachProduct);

								DecimalFormat df = new DecimalFormat( "######.##" );
								/*
								 * vatForEachProduct =
								 * vat = Double.valueOf( df.format( vatl ) );
								 * DecimalFormat df1 = new DecimalFormat(
								 * "######.##" );
								 */
								vat = Double.valueOf( df.format( vatl ) );

								// productPrice = productPrice *
								// updatedQuantityMultiply;

								totalItem = totalIteml;// +
														// updatedQuantityMultiply;
								total = totall;// + ( updatedQuantityMultiply *
												// productPrice.doubleValue() );
								totalPayable = total + vat + tax;

								totalItemsTextView.setText( String.valueOf( totalItem ) );
								totalTextView.setText( String.valueOf( total ) );
								totalPayableTextView.setText( String.valueOf( totalPayable ) );

								vatTextView.setText( String.valueOf( vat ) );

								// to update in the listView
								// holder.productPrice.setText( String.valueOf(
								// productPrice ) );

								_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, productList );
								listProducts.setAdapter( _saleListAdapter );
								_saleListAdapter.notifyDataSetChanged();
							}
							else
							{

								/*
								 * if( updatedQuantity < previousQuantity )
								 * {
								 * // to update in the main list
								 * productList.get( position ).setqQuantity(
								 * updatedQuantity );
								 * productList.get( position ).setPrice( new
								 * BigDecimal( updatedPrice ) );
								 * // since VAT for first quantity is already
								 * // inserted
								 * int updatedQuantityMultiply =
								 * previousQuantity - updatedQuantity;
								 * // int updatedQuantityMultiply =
								 * // --updatedQuantity;
								 * vatForEachProduct = ( updatedQuantityMultiply
								 * * productPrice.doubleValue() ) * 0.20;
								 * // String.format("%.2f", vatForEachProduct);
								 * DecimalFormat df = new DecimalFormat(
								 * "######.##" );
								 * vatForEachProduct = Double.valueOf(
								 * df.format( vatForEachProduct ) );
								 * vat -= vatForEachProduct;
								 * DecimalFormat df1 = new DecimalFormat(
								 * "######.##" );
								 * vat = Double.valueOf( df1.format( vat ) );
								 * // productPrice = productPrice *
								 * // updatedQuantityMultiply;
								 * totalItem = totalItem -
								 * updatedQuantityMultiply;
								 * total = total - ( updatedQuantityMultiply *
								 * productPrice.doubleValue() );
								 * totalPayable = total + vat + tax;
								 * totalItemsTextView.setText( String.valueOf(
								 * totalItem ) );
								 * totalTextView.setText( String.valueOf( total
								 * ) );
								 * totalPayableTextView.setText( String.valueOf(
								 * totalPayable ) );
								 * vatTextView.setText( String.valueOf( vat ) );
								 * // to update in the listView
								 * // holder.productPrice.setText(
								 * // String.valueOf(
								 * // productPrice ) );
								 * _saleListAdapter = new SaleListAdapter(
								 * getApplicationContext(), -1, productList );
								 * listProducts.setAdapter( _saleListAdapter );
								 * _saleListAdapter.notifyDataSetChanged();
								 * }
								 */
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
}

/**
 * 
 */
package com.fusepos.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fusepos.datalayer.DatabaseHandler;
import com.fusepos.datalayer.ProductBO;
import com.fusepos.service.DataSendService;
import com.fusepos.utils.AppGlobal;
import com.fusepos.utils.SAutoBgButton;

/**
 * @author Zaheer Ahmad
 * 
 */
public class SaleActivity extends Activity
{
	List<ProductBO>	_saleListProductForListView;
	List<ProductBO>	_saleListProductForGridView;
	SaleListAdapter	_saleListAdapter;
	SaleGridAdapter	_saleGridAdapter;

	GridView		gridProducts;
	ListView		listProducts;

	ProgressDialog	loadingDialog;
	TextView		totalPayableTextView;
	TextView		totalItemsTextView;
	TextView		taxTextView;
	TextView		discountTextView;
	TextView		totalTextView;
	TextView		vatTextView;

	double			totalPayable	= 0.0;
	int				totalItem		= 0;
	double			tax				= 0.0;
	double			discount		= 0.0;
	double			total			= 0.0;
	double			vat				= 0.0;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		_saleListProductForListView = new ArrayList<ProductBO>();
		_saleListProductForGridView = new ArrayList<ProductBO>();

		super.onCreate( savedInstanceState );
		setContentView( R.layout.sale_activity );

		gridProducts = ( GridView ) findViewById( R.id.sale_grid );

		listProducts = ( ListView ) findViewById( R.id.sale_listView );

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
		
		bindProducts();
	}

	public void bindProducts()
	{

		DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
		_saleListProductForGridView = dbHandler.getAllProducts();
		dbHandler.close();

		if( _saleListProductForGridView != null && _saleListProductForGridView.size() > 0 )
		{
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
								totalItem++;
								total += productBO.getPrice().doubleValue();
								totalPayable = total + vat + tax;

								totalItemsTextView.setText( String.valueOf( totalItem ) );
								totalTextView.setText( String.valueOf( total ) );
								totalPayableTextView.setText( String.valueOf( totalPayable ) );

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
		public View getView( int position, View convertView, ViewGroup parent )
		{

			ViewHolder holder;
			View view = convertView;
			if( view == null )
			{
				view = getLayoutInflater().inflate( R.layout.sale_list_item, parent, false );
			}
			holder = new ViewHolder();

			holder.deleteBtn = ( SAutoBgButton ) view.findViewById( R.id.sale_list_deletebtn );
			holder.productName = ( TextView ) view.findViewById( R.id.sale_list_productText );
			holder.ProductPrice = ( TextView ) view.findViewById( R.id.sale_list_productPriceText );
			holder.productQuantity = ( EditText ) view.findViewById( R.id.sale_list_productQttyText );
			holder.productId = ( TextView ) view.findViewById( R.id.sale_list_hidden_productId );

			holder.productName.setText( this.productList.get( position ).getName().trim() );
			holder.ProductPrice.setText( String.valueOf( this.productList.get( position ).getPrice() ) );
			holder.productQuantity.setText( String.valueOf( 1 ) );
			holder.productId.setText( String.valueOf( this.productList.get( position ).getId() ) );
			holder.deleteBtn.setOnClickListener( new View.OnClickListener()
			{

				@Override
				public void onClick( View v )
				{

					// TODO Auto-generated method stub
					LinearLayout ll = ( LinearLayout ) v.getParent();
					LinearLayout ll1 = ( LinearLayout ) ll.getParent();
					TextView tvId = ( TextView ) ll1.getChildAt( 0 );
					ProductBO p = new ProductBO();

					if( p.deleteProductFromList( Integer.parseInt( tvId.getText().toString() ), productList ) )
					{
						DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
						ProductBO productBO = dbHandler.getProduct( Integer.parseInt( tvId.getText().toString() ) );

						dbHandler.close();
						totalItem--;
						total -= productBO.getPrice().doubleValue();
						totalPayable = total + vat + tax;

						totalItemsTextView.setText( String.valueOf( totalItem ) );
						totalTextView.setText( String.valueOf( total ) );
						totalPayableTextView.setText( String.valueOf( totalPayable ) );

						_saleListAdapter.notifyDataSetChanged();
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
			TextView		ProductPrice;
			TextView		productId;
		}

	}
}

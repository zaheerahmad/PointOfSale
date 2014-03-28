/**
 * 
 */
package com.fusepos.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fusepos.datalayer.DatabaseHandler;
import com.fusepos.datalayer.ProductBO;
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

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{

		_saleListProductForListView = new ArrayList<ProductBO>();
		_saleListProductForGridView = new ArrayList<ProductBO>();

		super.onCreate( savedInstanceState );
		setContentView( R.layout.sale_activity );

		DatabaseHandler dbHandler = new DatabaseHandler( getApplicationContext(), AppGlobal.TABLE_PRODUCT );
		_saleListProductForGridView = dbHandler.getAllProducts();

		gridProducts = ( GridView ) findViewById( R.id.sale_grid );

		if( _saleListProductForGridView != null && _saleListProductForGridView.size() > 0 )
		{
			_saleGridAdapter = new SaleGridAdapter( getApplicationContext(), -1, _saleListProductForGridView );
			gridProducts.setAdapter( _saleGridAdapter );
		}

		listProducts = ( ListView ) findViewById( R.id.sale_listView );

		if( _saleListProductForListView != null )
		{
			_saleListAdapter = new SaleListAdapter( getApplicationContext(), -1, _saleListProductForListView );
			listProducts.setAdapter( _saleListAdapter );
		}

	}

	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate( R.menu.main, menu );
		return true;
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
						if( productBO != null )
						{
							if( productBO.isListHasProduct( productBO, _saleListProductForListView ) )
							{
								if( AppGlobal.isDebugMode )
									Toast.makeText( getApplicationContext(), "Product already added in List!", Toast.LENGTH_SHORT ).show();
							}
							else
							{
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
			holder.productQuantity = ( TextView ) view.findViewById( R.id.sale_list_productQttyText );

			holder.productName.setText( this.productList.get( position ).getName().trim() );
			holder.ProductPrice.setText( String.valueOf( this.productList.get( position ).getPrice() ) );
			holder.productQuantity.setText( String.valueOf( 1 ) );

			view.setTag( holder );
			return view;
		}

		class ViewHolder
		{
			SAutoBgButton	deleteBtn;
			TextView		productName;
			TextView		productQuantity;
			TextView		ProductPrice;
		}

	}
}

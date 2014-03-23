/**
 * 
 */
package com.fusepos.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author khurram Farooq
 * 
 */
public class ServiceHandler
{

	/**
	 * @param url
	 * @param params
	 * @return
	 */
	public InputStream makeServiceCall( String url, List<NameValuePair> params )
	{

		String paramString = "";
		InputStream inputStream = null;
		try
		{
			if( params != null )
			{
				paramString = URLEncodedUtils.format( params, "utf-8" );
				url += "?" + paramString;
				java.net.URLEncoder.encode( url );
			}

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet( url );
			HttpResponse httpResponse = httpClient.execute( httpGet );
			inputStream = httpResponse.getEntity().getContent();

		}
		catch ( UnsupportedEncodingException e )
		{
			e.printStackTrace();
		}
		catch ( ClientProtocolException e )
		{
			e.printStackTrace();
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}

		return inputStream;
	}

}

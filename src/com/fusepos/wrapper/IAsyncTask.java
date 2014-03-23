package com.fusepos.wrapper;

/**
 * @author Zaheer Ahmad
 *
 */
public interface IAsyncTask
{

	public void fail( ResponseStatusWrapper response );

	public void success( ResponseStatusWrapper response );

	public void doWait();

}

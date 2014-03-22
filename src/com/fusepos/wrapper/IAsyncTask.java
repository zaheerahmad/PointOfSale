package com.fusepos.wrapper;

public interface IAsyncTask
{

	public void fail( ResponseStatusWrapper response );

	public void success( ResponseStatusWrapper response );

	public void doWait();

}

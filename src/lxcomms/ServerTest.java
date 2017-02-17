/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lxcomms;

/**
 *
 * @author william
 */
class ServerTest
		extends Thread
{
	private boolean running;

	@Override
	public void run()
	{
		System.out.println("Server running");
		this.running=true;
		while (this.running)
		{
			System.out.println("Server waiting");
			synchronized(this)
			{
				try
				{
					this.wait(1000);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}
		System.out.println("Server stopped");
	}

	synchronized void close()
	{
		this.running = false;
		this.notifyAll();
	}
	
}

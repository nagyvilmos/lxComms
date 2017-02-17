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
class ClientTest
	extends Thread
{

	@Override
	public void run()
	{
		System.out.println("Client running");
		for (int i =0; i<10; i++)
		{
			System.out.println("Client pausing");
			synchronized(this)
			{
				try
				{
					this.wait(250);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
			}
		}
		System.out.println("Client done");
	}

	
}

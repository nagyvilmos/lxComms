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
public class CommsTest
{
    @SuppressWarnings("CallToPrintStackTrace")
	public static void main(String ... args)
	{
		ServerTest st = new ServerTest();
		ClientTest ct = new ClientTest();
		
		st.start();
		ct.start();
		
		try
		{
			ct.join();
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
		st.close();
	}
}

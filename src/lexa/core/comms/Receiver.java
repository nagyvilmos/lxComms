/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Receiver.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *================================================================================
 */
package lexa.core.comms;

import java.io.*;
import java.net.Socket;
import lexa.core.data.DataSet;
import lexa.core.data.exception.DataException;
import lexa.core.data.io.DataInput;
import lexa.core.logging.Logger;

/**
 * Receive data through a {@link DataInput} from a {@link Socket}.
 *
 * @author William Norman-Walker
 * @since 2013-08
 */
class Receiver
        extends Thread {
    /** the writer for sending data */
	private final Logger logger;
    private final DataInput dataInput;
    private final Session session;
    private SessionListener sessionListener;
    private boolean running;


    Receiver(Session session, Socket socket)
            throws IOException {
		this.logger = new Logger(Receiver.class.getSimpleName(),
				socket.getLocalAddress() + ":" + socket.getLocalPort());
        this.session = session;
        this.dataInput = new DataInput(
				new DataInputStream(
						socket.getInputStream()));
    }

    synchronized void close() {
        this.running = false;
        this.notifyAll();
    }

    private synchronized void read()
			throws IOException, DataException {
		DataSet data = this.dataInput.read();
		this.logger.debug("read",data);
		this.sessionListener.message(this.session,data);
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                this.read();
            } catch (DataException | IOException ex) {
                this.logger.error("failed to read from stream", ex);
		        this.close();
            }
        }
        this.setSessionListener(null);
    }

    synchronized void setSessionListener(SessionListener sessionListener) {
        this.sessionListener = sessionListener;
        this.notifyAll();
   }

    synchronized void open() {
        this.running = true;
        super.start();
    }
}

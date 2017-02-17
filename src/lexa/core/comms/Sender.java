/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Sender.java
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
import lexa.core.data.io.DataOutput;
import lexa.core.logging.Logger;

/**
 * Send data through a {@link DataOutput} to a {@link Socket}.
 *
 * @author William Norman-Walker
 * @since 2013-08
 */

public class Sender {

    /** the writer for sending data */

	private final Logger logger;
    private final DataOutput dataOutput;

    public Sender(Socket socket)
            throws IOException {
		this.logger = new Logger(Sender.class.getSimpleName(),
			socket.getLocalAddress() + ":" + socket.getLocalPort());
        this.dataOutput = new DataOutput(
                        new DataOutputStream(
                            socket.getOutputStream()));
    }

    /**
     * Send data to the io socket
     * @param   data
     *          the data to send
     * @throws  IOException
     *          when an I/O exception of some sort has occurred
     */
    public void send(DataSet data)
            throws DataException, IOException
	{
		this.logger.debug("send",data);
        this.dataOutput.write(data);
    }

    void open() {
        // do nothing right now
    }

    void close() {
        // closed
    }
}

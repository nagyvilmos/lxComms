/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * Session.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *==============================================================================
 */
package lexa.core.comms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import static lexa.core.comms.Config.CFG_HOST;
import static lexa.core.comms.Config.CFG_PORT;
import lexa.core.data.config.ConfigDataSet;
import lexa.core.data.DataSet;
import lexa.core.data.exception.DataException;

/**
 * A communication session.
 * <p>This has a {@link Sender} and a {@link Receiver} for writing to and
 * and reading from the socket.
 *
 * @author William Norman-Walker
 * @since 2013-08
 */
public class Session {
    /** the receiver for inbound messages */
    private final Receiver receiver;
    /** the sender for outbound messages */
    private final Sender sender;

    /**
     * Open a new client session from configuration.
     * <p>Open a {@link Socket} where the configuration is as follows:
     * <pre>
     * host &lt;hostName&gt;|&lt;IpAddress&gt;
     * port % &lt;portNumber&gt;
     * </pre>
     * <p>Where:
     * <dl>
     * <dt>&lt;hostName&gt;</dt><dd>The name of the device running the server
     *              session.</dd>
     * <dt>&lt;IpAddress&gt;</dt><dd>The IP address of the device running the server
     *              session.</dd>
     * <dt>&lt;portNumber&gt;</dt><dd>The port on which the server session is listening.</dd>
     * </dl>
     *
     * @param   config
     *          the configuration data for the session
     *
     * @throws  IOException
     *          when an exception occurs in the communications
     * @throws  DataException
     *          when an exception occurs in the configuration
     */
    public Session(ConfigDataSet config)
            throws IOException,
                    DataException {
        this(new Socket(
                InetAddress.getByName(config.getString(CFG_HOST)),
                config.getInteger(CFG_PORT)));
        config.close();
    }

    /**
     * Open a new client session on a {@link Socket}.
     *
     * @param   socket
     *          the socket for the session
     * @throws  IOException
     *          when an exception occurs in the communications
     */
    public Session(Socket socket)
            throws IOException {
        this.receiver = new  Receiver(this, socket);
        this.sender = new  Sender(socket);
    }

    /**
     * Close the session.
     */
    public synchronized void close() {
        this.sender.close();
        this.receiver.close();
    }

    /**
     * Send a message through the communications socket.
     * @param message
     * @throws IOException
     */
    public synchronized void send(DataSet message)
            throws DataException, IOException {
        this.sender.send(message);
    }

    /**
     * Set the session listener.
     * <p>A session can have a single listener for handling the replies from
     * the {@link Receiver}.
     * @param   sessionListener
     *          The object that will listen for updates from the session.
     */
    public synchronized void setSessionListener(SessionListener sessionListener) {
        this.receiver.setSessionListener(sessionListener);
    }

    /**
     * Open the session.
     */
    public synchronized void open() {
        this.receiver.open();
        this.sender.open();
    }
}

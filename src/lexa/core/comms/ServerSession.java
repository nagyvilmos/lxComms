/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * ServerSession.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *==============================================================================
 */
package lexa.core.comms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import static lexa.core.comms.Config.*;
import lexa.core.data.config.ConfigDataSet;
import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.data.exception.DataException;
import lexa.core.logging.Logger;

/**
 * Server side starting point for communications.
 * <p>This listens for connections on a socket and creates a new session when a new
 * socket is opened.
 *
 * @author William Norman-Walker
 * @since 2013-08
 */
public class ServerSession
        extends Thread
        implements SessionListener {
    // only allow local connections.
    private final boolean localHostOnly;
    private final ServerSocket serverSocket;
    private boolean running;
    private final List<Session> sessions;
    private final SessionListener listener;
	private final Logger logger;

    public ServerSession(SessionListener listener, final ConfigDataSet config)
            throws DataException, IOException {
		this.logger = new Logger(
                ServerSession.class.getSimpleName(),
                config.getInteger(CFG_PORT).toString());
        this.listener = listener;
        this.localHostOnly = config.contains(CFG_LOCAL_HOST) ?
                config.getBoolean(CFG_LOCAL_HOST) :
                false;
        this.serverSocket = new ServerSocket(
                config.getInteger(CFG_PORT));
        config.close();
        this.sessions = new ArrayList();
		this.logger.info("Listening on " +
                this.serverSocket.toString());
		if (this.localHostOnly)
		{
			this.logger.info("Local connections only");
		}

    }

    public synchronized void close() {
        this.running = false;
        this.notifyAll();
    }

    public synchronized void open() {
        this.running = true;
        this.start();
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                this.getConnection();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private synchronized void getConnection()
            throws IOException {
        Socket socket = this.serverSocket.accept();
		this.logger.debug("Connection " + socket.toString());
        if (this.localHostOnly &&
                !socket.getInetAddress().equals(InetAddress.getLoopbackAddress())) {
			this.logger.info("Remote connection rejected " + socket.toString());
            socket.close();
            return;
        }
        Session session = new Session(socket);
        session.setSessionListener(this.listener);
        this.sessions.add(session);
        session.open();
        DataSet notify = new SimpleDataSet()
				.put(CTX_MESSAGE,VAL_MSG_NEW_SESSION)
				.put(CTX_SOCKET,socket.getRemoteSocketAddress());
        this.listener.message(session, notify);
    }

    @Override
    public void message(Session session, DataSet message) {
        //DataSet bounceBack = new DataSet(message);
        //bounceBack.put("return","Session has no listener");
        try {
            session.send(message);
        } catch (DataException | IOException ex) {
            this.logger.error("Cannot send message", message, ex);
        }
    }
}

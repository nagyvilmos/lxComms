/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * SessionListener.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *==============================================================================
 */
package lexa.core.comms;

import lexa.core.data.DataSet;

/**
 * Interface for listening to a session for messages
 * @author William Norman-Walker
 * @since 2013-08
 */
public interface SessionListener {

    /**
    * Handle an inbound message from a session.
    * <br>
    * When a {@link Session} receives a message this is called.
    * @param session the session that received the message
    * @param message the message that has been received
    */
    public void message(Session session, DataSet message);
}

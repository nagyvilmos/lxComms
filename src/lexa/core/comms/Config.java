/*
 * =============================================================================
 * Lexa - Property of William Norman-Walker
 * -----------------------------------------------------------------------------
 * Config.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: February 2017
 *==============================================================================
 */
package lexa.core.comms;

/**
 * Context constants for the comms module
 * @author william
 */
class Config {
    static final String CFG_HOST ="host";
    static final String CFG_LOCAL_HOST ="localHostOnly";
    static final String CFG_PORT ="port";

    static final String CTX_MESSAGE ="message";
    static final String CTX_SOCKET ="socket";

    static final String VAL_MSG_NEW_SESSION ="newSession";
    private Config(){} // cannot be instantiated
}

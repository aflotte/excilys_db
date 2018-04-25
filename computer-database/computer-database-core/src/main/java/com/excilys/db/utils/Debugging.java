package com.excilys.db.utils;

import java.text.MessageFormat;

public class Debugging {

    /**
     *
     */
    private Debugging() {

    }
    /**
     *
     * @param logger le logger
     * @param request la requête
     */
    public static void requestDebug(org.slf4j.Logger logger, String request) {
        if (logger.isDebugEnabled()) {
            String toDebug = MessageFormat.format("Requête : {0}", request);
            logger.debug(toDebug);
        }
    }

    /**
     *
     * @param logger le logger
     * @param message le messag
     * @param toPrint l'entier qui sera entré dans le message
     */
    public static void simpleDebugInt(org.slf4j.Logger logger, String message, int toPrint) {
        if (logger.isDebugEnabled()) {
            String toDebug = MessageFormat.format(message, toPrint);
            logger.debug(toDebug);
        }
    }
}

package com.excilys.db.utils;

import java.text.MessageFormat;

public class Debugging {
    
    private Debugging() {

    }
    
    public static void requestDebug(org.slf4j.Logger logger, String request) {
        if (logger.isDebugEnabled()) {
            String toDebug = MessageFormat.format("Requête : {0}", request);
            logger.debug(toDebug);
        }
    }
    
    public static void simpleDebugInt(org.slf4j.Logger logger, String message, int toPrint) {
        if (logger.isDebugEnabled()) {
            String toDebug = MessageFormat.format(message, toPrint);
            logger.debug(toDebug);
        }
    }
}

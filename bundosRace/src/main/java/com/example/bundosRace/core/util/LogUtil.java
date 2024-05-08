package com.example.bundosRace.core.util;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class LogUtil {

    // pretty log4j2 logger
    public static void logPretty(String message) {
        log.debug("########################################################################");
        log.debug(message);
        log.debug("########################################################################");
    }
}

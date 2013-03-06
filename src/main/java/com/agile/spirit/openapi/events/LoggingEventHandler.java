package com.agile.spirit.openapi.events;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.eventbus.Subscribe;

public class LoggingEventHandler {

    @Subscribe
    public void handleLoggingEvent(final LoggingEvent event) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:ss");
        System.out.println("[EVENT] " + formatter.print(event.getTimeOccurred()) + " " + event.getMessage());
    }

}

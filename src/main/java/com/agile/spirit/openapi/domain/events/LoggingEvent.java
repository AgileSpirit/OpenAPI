package com.agile.spirit.openapi.domain.events;

import org.joda.time.DateTime;

public class LoggingEvent {

    private final DateTime timeOccurred;
    private final String message;

    public LoggingEvent(String message) {
        this.message = message;
        this.timeOccurred = new DateTime();
    }

    public DateTime getTimeOccurred() {
        return timeOccurred;
    }

    public String getMessage() {
        return message;
    }

}

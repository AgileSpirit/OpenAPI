package com.agile.spirit.openapi.domain.events;

import com.google.common.eventbus.EventBus;

public class EventStore {

    private static final EventBus eventBus = new EventBus();

    public static EventBus getEventBus() {
        return eventBus;
    }

}

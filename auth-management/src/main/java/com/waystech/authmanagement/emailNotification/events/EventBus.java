package com.waystech.authmanagement.emailNotification.events;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Hashtable;
@Getter
@Setter
@Slf4j
public class EventBus {
    private String eventName;
    private static Hashtable<Event, EventDispatcher> eventDispatchers = new Hashtable<>();
    public static void raiseEvents(Event event){
        EventDispatcher eventDispatcher = eventDispatchers.get(event);
        if(eventDispatcher == null) {
            return;
        }
        eventDispatcher.dispatchEvent(event);
        log.info("Event Dispatched Successfully");
    }

    public static void registerHandler(Event event, EventHandler handler){
        EventDispatcher eventDispatcher = eventDispatchers.get(event);
        if(eventDispatcher == null){
            eventDispatcher = new EventDispatcher(event);
            eventDispatchers.put(event, eventDispatcher);
        }
        eventDispatcher.registerHandler(handler);
        log.info("Handler Registered Successfully");
    }
    public static void deregisterHandler(Event event, EventHandler handler){
        EventDispatcher eventDispatcher;
        if((eventDispatchers.get(event)) == null){
            eventDispatcher = new EventDispatcher(event);
        }else{
            eventDispatcher = eventDispatchers.get(event);
        }
        eventDispatcher.deregisterHandler(handler);
    }
}

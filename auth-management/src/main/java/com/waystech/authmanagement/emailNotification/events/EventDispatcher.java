package org.partypal.commonModule.events;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class EventDispatcher {
    private final String eventName;
    private List<EventHandler> eventHandlers = new ArrayList<>();

    EventDispatcher(Event event){
        this.eventName = event.getEventName();
    }

    public void registerHandler( EventHandler handler){
        if(eventHandlers.contains(handler)){
            eventHandlers.remove(handler);
            eventHandlers.add(handler);
        }else{
            eventHandlers.add(handler);
        }
    }

    public void deregisterHandler(EventHandler eventHandler){
        eventHandlers.remove(eventHandler);
    }

    public void dispatchEvent(Event event){

        if(eventHandlers.isEmpty()) {
            return;
        }
        for(EventHandler handler : eventHandlers){
            handler.handleEvent(event);
        }
    }
}

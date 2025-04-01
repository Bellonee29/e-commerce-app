package org.partypal.commonModule.events;

import org.springframework.stereotype.Service;

@Service
public interface EventHandler {
    void handleEvent(Event event);
}

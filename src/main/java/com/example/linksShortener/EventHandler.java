package com.example.linksShortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Link.class)
public class EventHandler {

    private final SimpMessagingTemplate websocket;

    private final EntityLinks entityLinks;

    @Autowired
    public EventHandler(SimpMessagingTemplate websocket, EntityLinks entityLinks) {
        this.websocket = websocket;
        this.entityLinks = entityLinks;
    }

    @HandleAfterCreate
    public void newLink(Link link) {
        this.websocket.convertAndSend(
                WebSocketConfiguration.MESSAGE_PREFIX + "/newLink", getPath(link));
    }

    @HandleAfterDelete
    public void deleteLink(Link link) {
        this.websocket.convertAndSend(
                WebSocketConfiguration.MESSAGE_PREFIX + "/deleteLink", getPath(link));
    }

    @HandleAfterSave
    public void updateLink(Link link) {
        this.websocket.convertAndSend(
                WebSocketConfiguration.MESSAGE_PREFIX + "/updateLink", getPath(link));
    }

    /**
     * Take an {@link Link} and get the URI using Spring Data REST's {@link EntityLinks}.
     *
     * @param link
     */
    private String getPath(Link link) {
        return this.entityLinks.linkForSingleResource(link.getClass(),
                link.getLongUrl()).toUri().getPath();
    }

}

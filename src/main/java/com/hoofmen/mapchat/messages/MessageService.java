package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;

import java.util.List;

/**
 * Created by osman on 1/3/17.
 */
public interface MessageService {
    List<MapMessage> getMapMessages(MapMessageRequest locationMessagesRequest) throws NoMessagesFoundException;
}

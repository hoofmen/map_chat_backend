package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;

import java.util.List;

/**
 * Created by osman on 1/3/17.
 */
public interface MessageService {
    List<MapMessage> getMapMessages(double lat, double lon, double rad, int max_messages) throws NoMessagesFoundException;

    List<MapMessage> getAllMapMessages() throws NoMessagesFoundException;

    void saveMapMessage(MapMessage mapMessage);
}

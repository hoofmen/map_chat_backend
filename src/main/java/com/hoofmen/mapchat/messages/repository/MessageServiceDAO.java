package com.hoofmen.mapchat.messages.repository;

import com.hoofmen.mapchat.messages.model.MapMessage;
import com.hoofmen.mapchat.messages.model.MapMessageRequest;

import java.util.List;

/**
 * Created by osman on 1/3/17.
 */
public interface MessageServiceDAO {

    List<MapMessage> getAllMapMessages();

    List<MapMessage> getMapMessages(MapMessageRequest mapMessageRequest);

    void saveMapMessage(MapMessage mapMessage);
}
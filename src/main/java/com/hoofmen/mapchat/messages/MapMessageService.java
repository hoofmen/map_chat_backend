package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;
import com.hoofmen.mapchat.shared.AppConstants;
import com.hoofmen.mapchat.utils.LogUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MapMessageService implements MessageService {
    static final Logger logger = LogUtils.buildLogClient(MapMessageService.class);

    @Autowired
    public MapMessageDAO messageDAO;

    @Override
    public List<MapMessage> getMapMessages(double lat, double lon, double rad, int max_messages) throws NoMessagesFoundException {
        MapMessageRequest mapMessageRequest = this.buildMapMessageRequest(lat, lon, rad, max_messages);
        List<MapMessage> messageList = messageDAO.getMapMessages(mapMessageRequest);
        if (CollectionUtils.isEmpty(messageList)){
            logger.warn("No messages found near the requested point!");
            throw new NoMessagesFoundException(AppConstants.WARN_NO_MESSAGES_FOUND);
        }
        return messageList;
    }

    @Override
    public List<MapMessage> getAllMapMessages() throws NoMessagesFoundException {
        List<MapMessage> messageList = messageDAO.getAllMapMessages();
        if (CollectionUtils.isEmpty(messageList)){
            logger.warn("No messages found at all !");
            throw new NoMessagesFoundException(AppConstants.WARN_NO_MESSAGES_FOUND);
        }
        return messageList;
    }

    @Override
    public void saveMapMessage(MapMessage mapMessage){
        messageDAO.saveMapMessage(mapMessage);
    }

    private MapMessageRequest buildMapMessageRequest(double lat, double lon, double rad, int max_messages){
        MapMessageRequest mapMessageRequest = new MapMessageRequest();
        Location location = new Location();
        double[] coordinates = {lon, lat};
        location.setType("Point");
        location.setCoordinates(coordinates);
        mapMessageRequest.setLocation(location);
        mapMessageRequest.setRadius(rad);
        mapMessageRequest.setMaxMessages(max_messages);
        return mapMessageRequest;
    }
}

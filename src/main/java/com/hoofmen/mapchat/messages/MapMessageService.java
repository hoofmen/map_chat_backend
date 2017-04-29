package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.messages.exceptions.CouldNotConnectToDataBaseException;
import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;
import com.hoofmen.mapchat.shared.AppConstants;
import com.hoofmen.mapchat.utils.LogUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MapMessageService implements MessageService {
    static final Logger logger = LogUtils.buildLogClient(MapMessageService.class);

    @Autowired
    public MapMessageServiceDAO messageDAO;

    @Override
    public List<MapMessage> getMapMessages(double lat, double lng, double rad, int max_messages) throws NoMessagesFoundException, CouldNotConnectToDataBaseException {
        MapMessageRequest mapMessageRequest = this.buildMapMessageRequest(lat, lng, rad, max_messages);
        List<MapMessage> messageList;
        try {
            messageList = messageDAO.getMapMessages(mapMessageRequest);
        }catch(DataAccessResourceFailureException ex){
            logger.warn("Could not connect to db !");
            throw new CouldNotConnectToDataBaseException(AppConstants.ERROR_COULD_NOT_CONNECT_TO_DB);
        }
        if (CollectionUtils.isEmpty(messageList)){
            logger.warn("No messages found near the requested point!");
            throw new NoMessagesFoundException(AppConstants.WARN_NO_MESSAGES_FOUND);
        }
        return messageList;
    }

    @Override
    public List<MapMessage> getAllMapMessages() throws NoMessagesFoundException, CouldNotConnectToDataBaseException {
        List<MapMessage> messageList;
        try {
            messageList = messageDAO.getAllMapMessages();
        } catch (DataAccessResourceFailureException ex){
            logger.warn("Could not connect to db !");
            throw new CouldNotConnectToDataBaseException(AppConstants.ERROR_COULD_NOT_CONNECT_TO_DB);
        }
        if (CollectionUtils.isEmpty(messageList)){
            logger.warn("No messages found at all !");
            throw new NoMessagesFoundException(AppConstants.WARN_NO_MESSAGES_FOUND);
        }
        return messageList;
    }

    @Override
    public void saveMapMessage(MapMessage mapMessage) throws CouldNotConnectToDataBaseException{
        try {
            messageDAO.saveMapMessage(mapMessage);
        }catch(DataAccessResourceFailureException ex){
            logger.warn("Could not connect to db !");
            throw new CouldNotConnectToDataBaseException(AppConstants.ERROR_COULD_NOT_CONNECT_TO_DB);
        }
    }

    private MapMessageRequest buildMapMessageRequest(double lat, double lng, double rad, int max_messages){
        MapMessageRequest mapMessageRequest = new MapMessageRequest();
        Location location = new Location();
        location.setLat(lat);
        location.setLng(lng);
        mapMessageRequest.setLocation(location);
        mapMessageRequest.setRadius(rad);
        mapMessageRequest.setMaxMessages(max_messages);
        return mapMessageRequest;
    }
}

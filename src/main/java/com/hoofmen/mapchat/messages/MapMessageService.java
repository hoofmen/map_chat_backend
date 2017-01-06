package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;
import com.hoofmen.mapchat.shared.AppConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MapMessageService implements MessageService {

    @Autowired
    public MapMessageRepository mapMessageRepository;

    @Override
    public List<MapMessage> getMapMessages(MapMessageRequest locationMessagesRequest) throws NoMessagesFoundException {
        //new Distance(km, Metrics.KILOMETERS)
        Point point = new Point(locationMessagesRequest.getLocation().getLat(),locationMessagesRequest.getLocation().getLon());
        Distance distance = new Distance(locationMessagesRequest.getRadius(), Metrics.KILOMETERS);
        List<MapMessage> messageList = mapMessageRepository.findByPositionNear(point,distance);
        if (CollectionUtils.isEmpty(messageList)){
            throw new NoMessagesFoundException(AppConstants.WARN_NO_MESSAGES_FOUND);
        }
        return messageList;
    }
}

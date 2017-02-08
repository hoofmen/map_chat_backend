package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MapMessageDAO implements MessageDAO{

    static final Logger logger = LogUtils.buildLogClient(MessageDAO.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<MapMessage> getAllMapMessages(){
        logger.debug("Trying to get all map messages...");
        return mongoTemplate.findAll(MapMessage.class);
    }

    @Override
    public List<MapMessage> getMapMessages(MapMessageRequest mapMessageRequest){
        logger.debug("Trying to get the map messages near this point [lon,lat] = [" + mapMessageRequest.getLocation().getCoordinates()[0] + "," + mapMessageRequest.getLocation().getCoordinates()[1] + "] ...");
        Point point = new Point(mapMessageRequest.getLocation().getCoordinates()[0],mapMessageRequest.getLocation().getCoordinates()[1]);
        Distance distance = new Distance(mapMessageRequest.getRadius(), Metrics.KILOMETERS);
        NearQuery nearQuery = NearQuery.near(point).maxDistance(distance);
        GeoResults<MapMessage> results = mongoTemplate.geoNear(nearQuery,MapMessage.class);
        List<MapMessage> messageList = new ArrayList<>();
        for (GeoResult<MapMessage> result : results){
            messageList.add(result.getContent());
        }
        logger.debug(messageList.size() + " messages found!");
        return messageList;
    }

    @Override
    public void saveMapMessage(MapMessage mapMessage){
        logger.debug("Trying to post a new map message...");
        mongoTemplate.save(mapMessage);
    }
}
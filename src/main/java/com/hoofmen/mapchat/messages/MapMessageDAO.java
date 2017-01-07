package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MapMessageDAO implements MessageDAO{

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public List<MapMessage> getAllMapMessages(MapMessageRequest mapMessageRequest){
        return mongoTemplate.findAll(MapMessage.class);
    }

    @Override
    public List<MapMessage> getMapMessages(MapMessageRequest mapMessageRequest){
        Point point = new Point(mapMessageRequest.getLocation().getCoordinates()[0],mapMessageRequest.getLocation().getCoordinates()[1]);
        Distance distance = new Distance(mapMessageRequest.getRadius(), Metrics.KILOMETERS);
        NearQuery nearQuery = NearQuery.near(point).maxDistance(distance);
        GeoResults<MapMessage> results = mongoTemplate.geoNear(nearQuery,MapMessage.class);
        List<MapMessage> messageList = new ArrayList<>();
        for (GeoResult<MapMessage> result : results){
            messageList.add(result.getContent());
        }
        return messageList;
    }

    @Override
    public void saveMapMessage(MapMessage mapMessage){
        mongoTemplate.save(mapMessage);
    }
}
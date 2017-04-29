package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.messages.beans.dao.LocationDAO;
import com.hoofmen.mapchat.messages.beans.dao.MapMessageDAO;
import com.hoofmen.mapchat.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MapMessageServiceDAO implements MessageServiceDAO {

    static final Logger logger = LogUtils.buildLogClient(MessageServiceDAO.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<MapMessage> getAllMapMessages(){
        logger.debug("Trying to get all map messages...");
        return convertToMapMessageList(mongoTemplate.findAll(MapMessageDAO.class));
    }

    @Override
    public List<MapMessage> getMapMessages(MapMessageRequest mapMessageRequest){
        logger.debug("Trying to get the map messages near this point [lat,lng] = [" + mapMessageRequest.getLocation().getLat() + "," + mapMessageRequest.getLocation().getLng() + "] ...");
        Point point = new Point(mapMessageRequest.getLocation().getLng(),mapMessageRequest.getLocation().getLat());
        Distance distance = new Distance(mapMessageRequest.getRadius(), Metrics.KILOMETERS);
        NearQuery nearQuery = NearQuery.near(point).maxDistance(distance);
        GeoResults<com.hoofmen.mapchat.messages.beans.dao.MapMessageDAO> results = mongoTemplate.geoNear(nearQuery, com.hoofmen.mapchat.messages.beans.dao.MapMessageDAO.class);
        List<com.hoofmen.mapchat.messages.beans.dao.MapMessageDAO> messageList = new ArrayList<>();
        for (GeoResult<com.hoofmen.mapchat.messages.beans.dao.MapMessageDAO> result : results){
            messageList.add(result.getContent());
        }
        logger.debug(messageList.size() + " messages found!");
        return convertToMapMessageList(messageList);
    }

    @Override
    public void saveMapMessage(MapMessage mapMessage){
        logger.debug("Trying to post a new map message...");
        mongoTemplate.save(convertToMapMessageDAO(mapMessage));
    }

    private List<MapMessage> convertToMapMessageList(List<MapMessageDAO> results){
        List<MapMessage> mapMessageList = new ArrayList<>();

        results.forEach((msg) ->{
            Location location = new Location();
            location.setLat(msg.getLocation().getCoordinates()[1]);
            location.setLng(msg.getLocation().getCoordinates()[0]);

            MapMessage mapMsg = new MapMessage();
            mapMsg.setLocation(location);
            mapMsg.setDuration(msg.getDuration());
            mapMsg.setId(msg.getId());
            mapMsg.setMessage(msg.getMessage());
            mapMessageList.add(mapMsg);
        });
        return mapMessageList;
    }

    private MapMessageDAO convertToMapMessageDAO(MapMessage mapMessage){
        MapMessageDAO mapMessageDAO = new MapMessageDAO();
        mapMessageDAO.setDuration(mapMessage.getDuration());
        if (mapMessage.getId()==null) {
            mapMessageDAO.setId(UUID.randomUUID());
        }else {
            mapMessageDAO.setId(mapMessage.getId());
        }
        mapMessageDAO.setMessage(mapMessage.getMessage());

        LocationDAO locationDAO = new LocationDAO();
        double [] coordinates = {mapMessage.getLocation().getLng(), mapMessage.getLocation().getLat()};
        locationDAO.setCoordinates(coordinates);
        locationDAO.setType("Point");
        mapMessageDAO.setLocation(locationDAO);

        return mapMessageDAO;
    }
}
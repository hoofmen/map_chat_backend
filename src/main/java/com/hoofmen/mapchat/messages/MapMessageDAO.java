package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.utils.LogUtils;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoTypeMapper;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
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

    static final Logger logger = LogUtils.buildLogClient(MessageDAO.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    private void initMongoTemplate(){
        if (mongoTemplate == null) {
            logger.debug("Trying to initialize mongoTemplate...");
            MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://mapchat:mapchat123@ds159348.mlab.com:59348/mapchat"));
            SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(mongo, "mapchat");
            MongoTypeMapper typeMapper = new DefaultMongoTypeMapper(null);
            MappingMongoConverter converter = new MappingMongoConverter(simpleMongoDbFactory, new MongoMappingContext());
            converter.setTypeMapper(typeMapper);

            mongoTemplate = new MongoTemplate(simpleMongoDbFactory, converter);
            mongoTemplate.setWriteConcern(WriteConcern.SAFE);
        }
    }

    @Override
    public List<MapMessage> getAllMapMessages(){
//        initMongoTemplate();
        logger.debug("Trying to get all map messages...");
        return mongoTemplate.findAll(MapMessage.class);
    }

    @Override
    public List<MapMessage> getMapMessages(MapMessageRequest mapMessageRequest){
//        initMongoTemplate();
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
//        initMongoTemplate();
        logger.debug("Trying to post a new map message...");
        mongoTemplate.save(mapMessage);
    }
}
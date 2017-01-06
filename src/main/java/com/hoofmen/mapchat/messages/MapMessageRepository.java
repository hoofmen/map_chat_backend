package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.MapMessage;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by osman on 1/6/17.
 */
public interface MapMessageRepository extends MongoRepository<MapMessage, String> {
    List<MapMessage> findByPositionNear(Point p, Distance d);
}


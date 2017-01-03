package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.model.MapMessage;
import com.hoofmen.mapchat.model.ViewArea;
import com.hoofmen.mapchat.shared.MessagesNotFoundException;
import com.hoofmen.mapchat.utils.AppConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MessageService {

    @Autowired
    public MessageDAO messageDAO;

    public List<MapMessage> getMapMessagesGivenViewArea(ViewArea viewArea) throws MessagesNotFoundException{
        List<MapMessage> messageList = messageDAO.getMapMessagesGivenViewArea(viewArea);
        if (CollectionUtils.isEmpty(messageList)){
            throw new MessagesNotFoundException(AppConstants.WARN_MESSAGES_NOT_FOUND);
        }
        return messageList;
    }
}

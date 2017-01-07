package com.hoofmen.mapchat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoofmen.mapchat.messages.MessageService;
import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.shared.AppConstants;
import com.hoofmen.mapchat.shared.AppMessage;
import com.hoofmen.mapchat.shared.AppMessageFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by osman on 1/4/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/test/resources/mvc-dispatcher-servlet-test.xml"})
@WebAppConfiguration
public class MapChatTest {

    private MockMvc mockMvc;
    private MockHttpServletRequest request;

    @Autowired
    private WebApplicationContext ctx;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MapChat mapChat;

    @Autowired
    AppMessageFactory appMessageFactory;

    @Mock
    private MessageService messageServiceMock;

    @Before
    public void setUp() {

        try {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
            request = new MockHttpServletRequest();
            MockitoAnnotations.initMocks(this);
            ReflectionTestUtils.setField(mapChat, "messageService", messageServiceMock);
            ReflectionTestUtils.setField(mapChat, "appMessageFactory", appMessageFactory);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Test
    public void testGetMessages() throws Exception {
        List<MapMessage> mapMessageList = buildMapMessageList(4);
        when(messageServiceMock.getMapMessages(anyDouble(),anyDouble(),anyDouble(),anyInt())).thenReturn(mapMessageList);
        List<MapMessage> responseMapMessageList = mapChat.getAreaMessages(37.76582,-121.90761,1000,4);
        assertTrue(responseMapMessageList.size()==4);

        ResultActions perform = mockMvc.perform(get("/messages?lat=37.76582&lon=-121.90761&rad=10&max_messages=6").requestAttr(AppConstants.HEADER_TOKEN, AppConstants.TOKEN)).andExpect(status().isOk());
        assertTrue(contentType.toString().equalsIgnoreCase(perform.andReturn().getResponse().getContentType()));

    }

    @Test
    public void testPostMessage() throws Exception {
        MapMessage mapMessage = buildMapMessage();
        AppMessage appMessage = mapChat.postMessage(request, mapMessage);
        assertTrue(appMessage.getCode().equalsIgnoreCase(AppConstants.OK_MESSAGE_SAVED));
        assertTrue(appMessage.getMessage().equalsIgnoreCase("Message posted correctly"));

        ResultActions perform = mockMvc.perform(post("/messages").content(convertObjectToJsonBytes(mapMessage)).requestAttr(AppConstants.HEADER_TOKEN, AppConstants.TOKEN).contentType(contentType)).andExpect(status().isOk());
        assertTrue(contentType.toString().equalsIgnoreCase(perform.andReturn().getResponse().getContentType()));
    }

    private MapMessageRequest buildMapMessageRequest(double lat, double lon, double rad, int max_messages){
        MapMessageRequest mapMessageRequest = new MapMessageRequest();
        Location location = new Location();
        double[] coordinates = {lon, lat};
        location.setCoordinates(coordinates);
        location.setType("Point");
        mapMessageRequest.setLocation(location);
        mapMessageRequest.setRadius(rad);
        mapMessageRequest.setMaxMessages(max_messages);
        return mapMessageRequest;
    }

    private List<MapMessage> buildMapMessageList(int max_messages){
        List<MapMessage> mapMessageList = new ArrayList<>();
        MapMessage mapMessage;
        for (int i=0; i<max_messages; i++){
            mapMessage = new MapMessage();
            mapMessage.setDuration(0);
            mapMessage.setMessage("this is message " +i);
            mapMessage.setLocation(new Location());
            mapMessageList.add(mapMessage);
        }
        return mapMessageList;
    }

    private MapMessage buildMapMessage(){
        MapMessage mapMessage = new MapMessage();
        mapMessage.setDuration(0);
        mapMessage.setMessage("this is message ");
        mapMessage.setLocation(new Location());
        return mapMessage;
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}

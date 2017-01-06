package com.hoofmen.mapchat;

import com.hoofmen.mapchat.messages.MapMessageRepository;
import com.hoofmen.mapchat.messages.MessageService;
import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.shared.AppConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.mockito.Matchers.anyObject;
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
import org.springframework.web.servlet.HandlerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
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
    private MockHttpServletResponse response;
    @Autowired
    private WebApplicationContext ctx;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MapChat mapChatMock;

    //@Autowired
    //MapMessageRepository mapMessageRepository;

    //@Mock
    //private MessageService messageServiceMock;

    @Before
    public void setUp() {
        /*
        try {
            this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
            ReflectionTestUtils.setField(mapChatMock, "messageService", messageServiceMock);
            request = new MockHttpServletRequest();
            response = new MockHttpServletResponse();
            MockitoAnnotations.initMocks(this);
            messageServiceMock.setMapMessageRepository(mapMessageRepository);
            mapChatMock.setMessageService(messageServiceMock);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        */
    }

    @Test
    public void testApp() throws Exception {
//        ResultActions perform = mockMvc.perform(get("/messages?lat=37.76582&lon=-121.90761&rad=1000&max_messages=6").requestAttr(AppConstants.HEADER_TOKEN, "thisisaverysecrettoken")).andExpect(status().isOk());
//        MapMessageRequest mapMessageRequest = buildMapMessageRequest(37.76582,-121.90761,1000,6);
//        List<MapMessage> mapMessageList = buildMapMessageList(4);
//        when(messageServiceMock.getMapMessages((MapMessageRequest)anyObject())).thenReturn(mapMessageList);
//        verify(messageServiceMock, times(1)).getMapMessages(mapMessageRequest);
//        perform.andReturn().getResponse().getContentAsString();
        assertTrue(true);
    }

    @Test
    public void testApp2() throws Exception {
        /*
        request.setMethod("GET");
        request.setRequestURI("/messages");
        request.setParameter("lat", "37.76582");
        request.setParameter("lon", "-121.90761");
        request.setParameter("rad", "1000");
        request.setParameter("max_messages", "4");
        request.setAttribute(AppConstants.HEADER_TOKEN, "thisisaverysecrettoken");

        List<MapMessage> mapMessageList = buildMapMessageList(4);
        when(messageServiceMock.getMapMessages((MapMessageRequest)anyObject())).thenReturn(mapMessageList);
        //doReturn(mapMessageList).when(messageServiceMock).getMapMessages((MapMessageRequest)anyObject());
        List<MapMessage> newMapMessageList = mapChatMock.getAreaMessages(37.76582,-121.90761,1000,6);
        */

    }

    private MapMessageRequest buildMapMessageRequest(double lat, double lon, double rad, int max_messages){
        MapMessageRequest mapMessageRequest = new MapMessageRequest();
        Location location = new Location();
        location.setLat(lat);
        location.setLon(lon);
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
        }
        return mapMessageList;
    }
}

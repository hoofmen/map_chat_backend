package com.hoofmen.mapchat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoofmen.mapchat.messages.MessageService;
import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;
import com.hoofmen.mapchat.shared.AppConstants;
import com.hoofmen.mapchat.shared.AppExceptionHandler;
import com.hoofmen.mapchat.shared.AppMessage;
import com.hoofmen.mapchat.shared.AppMessageFactory;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext ctx;
    @Autowired
    private MapChat mapChat;
    @Autowired
    private AppExceptionHandler appExceptionHandler;
    @Autowired
    private AppMessageFactory appMessageFactory;
    @Autowired
    private MessageService messageServiceMock;

    @Before
    public void setUp() {
        try {
            mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
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
    public void testGetMessagesNoMessagesFound() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(mapChat).setControllerAdvice(appExceptionHandler).build();
        when(messageServiceMock.getMapMessages(anyDouble(),anyDouble(),anyDouble(),anyInt())).thenThrow(new NoMessagesFoundException(AppConstants.WARN_NO_MESSAGES_FOUND));
        ResultActions perform = mockMvc.perform(get("/messages?lat=37.76582&lon=-121.90761&rad=10&max_messages=6").requestAttr(AppConstants.HEADER_TOKEN, AppConstants.TOKEN)).andExpect(status().isOk());
        assertTrue(contentType.toString().equalsIgnoreCase(perform.andReturn().getResponse().getContentType()));
        JSONObject json = new JSONObject(perform.andReturn().getResponse().getContentAsString());
        assertTrue(json.get("code").toString().equalsIgnoreCase(AppConstants.WARN_NO_MESSAGES_FOUND));
        assertTrue(json.get("message").toString().equalsIgnoreCase("No messages found in the current location"));
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

    private ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(AppExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(new AppExceptionHandler(), method);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}

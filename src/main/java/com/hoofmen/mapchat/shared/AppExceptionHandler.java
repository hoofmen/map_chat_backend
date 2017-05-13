package com.hoofmen.mapchat.shared;

import com.hoofmen.mapchat.messages.exception.CouldNotConnectToDataBaseException;
import com.hoofmen.mapchat.messages.exception.NoMessagesFoundException;
import com.hoofmen.mapchat.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by osman on 1/2/17.
 */
@EnableWebMvc
@ControllerAdvice
@RestController
public class AppExceptionHandler {
    //TODO: This needs some refactoring
    static final Logger logger = LogUtils.buildLogClient(AppExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(value= HttpStatus.OK)
    @ExceptionHandler(NoMessagesFoundException.class)
    public @ResponseBody AppMessage messagesNotFound(HttpServletRequest req, NoMessagesFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(ex.getCode(), null, locale);
        String errorCode = ex.getCode();
        AppMessage appMessage = new AppMessage();
        appMessage.setCode(errorCode);
        appMessage.setMessage(errorMessage);
	    logger.warn("Messages not found:" + ex.getCode());

        return appMessage;
    }

    @ResponseStatus(value= HttpStatus.OK)
    @ExceptionHandler(CouldNotConnectToDataBaseException.class)
    public @ResponseBody AppMessage couldNotConnectToDataBase(HttpServletRequest req, CouldNotConnectToDataBaseException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(ex.getCode(), null, locale);
        String errorCode = ex.getCode();
        AppMessage appMessage = new AppMessage();
        appMessage.setCode(errorCode);
        appMessage.setMessage(errorMessage);
        logger.warn("Could not connect to the database:" + ex.getCode());

        return appMessage;
    }
}

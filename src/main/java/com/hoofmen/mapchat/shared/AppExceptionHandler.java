package com.hoofmen.mapchat.shared;

import com.hoofmen.mapchat.messages.exceptions.CouldNotConnectToDataBaseException;
import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;
import com.hoofmen.mapchat.utils.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by osman on 1/2/17.
 */
@ControllerAdvice
public class AppExceptionHandler {
    //TODO: This needs some refactoring
    static final Logger logger = LogUtils.buildLogClient(AppExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(NoMessagesFoundException.class)
    @ResponseStatus(value= HttpStatus.OK)
    @ResponseBody
    public AppMessage messagesNotFound(HttpServletRequest req, NoMessagesFoundException ex) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(ex.getCode(), null, locale);
        String errorCode = ex.getCode();
        AppMessage appMessage = new AppMessage();
        appMessage.setCode(errorCode);
        appMessage.setMessage(errorMessage);
	    logger.warn("Messages not found:" + ex.getCode());

        return appMessage;
    }

    @ExceptionHandler(CouldNotConnectToDataBaseException.class)
    @ResponseStatus(value= HttpStatus.OK)
    @ResponseBody
    public AppMessage couldNotConnectToDataBase(HttpServletRequest req, CouldNotConnectToDataBaseException ex) {
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

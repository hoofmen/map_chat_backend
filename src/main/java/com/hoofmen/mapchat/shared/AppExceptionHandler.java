package com.hoofmen.mapchat.shared;

import com.hoofmen.mapchat.messages.exceptions.NoMessagesFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by osman on 1/2/17.
 */
@ControllerAdvice
public class AppExceptionHandler {
    //TODO: This needs some refactoring

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

        return appMessage;
    }
}

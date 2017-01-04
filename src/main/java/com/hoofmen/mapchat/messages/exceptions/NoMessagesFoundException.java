package com.hoofmen.mapchat.messages.exceptions;

/**
 * Created by osman on 1/2/17.
 */
public class NoMessagesFoundException extends MapMessageException {
    public NoMessagesFoundException(String code){
        this.code = code;
    }
}

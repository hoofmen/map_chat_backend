package com.hoofmen.mapchat.messages.exception;

/**
 * Created by Osman H. on 1/17/17.
 */
public class CouldNotConnectToDataBaseException extends MapMessageException {
    public CouldNotConnectToDataBaseException(String code) {
        this.code = code;
    }
}
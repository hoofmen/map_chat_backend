package com.hoofmen.mapchat.messages.exceptions;

/**
 * Created by osman on 1/3/17.
 */
public class MapMessageException extends Exception {
    protected String code;
    protected String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

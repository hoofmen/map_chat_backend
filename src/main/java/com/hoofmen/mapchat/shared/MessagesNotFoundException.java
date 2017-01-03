package com.hoofmen.mapchat.shared;

/**
 * Created by osman on 1/2/17.
 */
public class MessagesNotFoundException extends Exception {
    private String code;
    private String message;

    public MessagesNotFoundException(String code){
        this.code = code;
    }

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

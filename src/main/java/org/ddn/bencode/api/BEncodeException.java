package org.ddn.bencode.api;

/**
 * Created by Denis on 15.11.2015.
 */
public class BEncodeException extends Exception {

    public BEncodeException(String message) {
        super(message);
    }

    public BEncodeException(String message, Throwable cause) {
        super(message, cause);
    }
}

package org.ddn.bencode.api;

/**
 * The root exception that encapsulates any error related to B-Encode process
 */
public class BEncodeException extends Exception {

    /**
     * Constructs a new instance from a message
     * @param message error description
     */
    public BEncodeException(String message) {
        super(message);
    }

    /**
     * Constructs a new instance from a message and a reason exception
     * @param message description
     * @param cause reason
     */
    public BEncodeException(String message, Throwable cause) {
        super(message, cause);
    }
}

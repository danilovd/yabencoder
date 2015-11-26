package org.ddn.bencode.api.entries.reader;

import org.ddn.bencode.api.BEncodeException;

/**
 * Created by Denis on 22.11.2015.
 */
public class BEncodeParsingException extends BEncodeException {

    public BEncodeParsingException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return new StringBuilder("Incorrect B-Encode format: ").append(super.getMessage()).toString();
    }
}

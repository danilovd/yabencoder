package org.ddn.bencode.api.entries.reader;

import org.ddn.bencode.api.BEncodeException;

/**
 * Encapsulates B-Encode incorrect format error
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

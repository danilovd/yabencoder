package org.ddn.bencode.api.entries;

import org.ddn.bencode.api.BEncodeContext;

import java.io.IOException;
import java.io.OutputStream;

/**
 * The root interface that represents B-Encode entity
 */
public interface Entry<T> extends ValueHolder<T> {

    /**
     * method is used for serialization entity to B-Encode format
     * @param ctx serialization context holding parameters and temporary data
     * @param out output stream where bytes are written
     * @throws IOException when failed to write entry to the output stream
     */
    void writeTo(BEncodeContext ctx, OutputStream out) throws IOException;
}

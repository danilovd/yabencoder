package org.ddn.bencode.api;

import org.ddn.bencode.api.entries.Entry;

import java.io.InputStream;
import java.util.Collection;

/**
 * This interface is used for decoding of B-Encoded data to entry objects
 * {@link org.ddn.bencode.api.BEncoder} for format description
 */
public interface BDecoder {

    /**
     * Method reads data from the input stream and creates entries from it.
     * Entries are stored in a given collection
     * @param in stream data
     * @param entries collection where to store the results
     * @throws BEncodeException if failed to parse data due to incorrect B-Encode data format
     */
    void decode(InputStream in, Collection<? super Entry> entries) throws BEncodeException;
}

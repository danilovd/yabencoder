package org.ddn.bencode.api.entries.reader;

import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.entries.Entry;

/**
 * The interface for sequential iteration over entries from input data.
 * Works similar to {@link java.util.Iterator}
 */
public interface EntryReader {

    /**
     * Method returns next entry if it is available
     * @return next entry or <code>null</code> if no entries are available to read
     * @throws BEncodeException if reading entry is failed due to incorrect B-Encode format 
     */
    Entry readEntry() throws BEncodeException;
}

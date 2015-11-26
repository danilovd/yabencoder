package org.ddn.bencode.api.entries.reader;

import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.entries.Entry;

/**
 * The class is used to parse entry
 */
public interface EntryReader {

    Entry readEntry() throws BEncodeException;
}

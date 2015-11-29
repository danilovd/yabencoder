package org.ddn.bencode.api.entries.reader;

import java.io.InputStream;

/**
 * Interface for EntryReader creation
 * {@link org.ddn.bencode.api.entries.reader.EntryReader}
 */
public interface EntryReaderFactory {

    /**
     * Creates a new EntryReader that reads given InputStream
     * @param in input stream to be read by EntryReader
     * @return EntryReader instance
     */
    EntryReader create(InputStream in);
}

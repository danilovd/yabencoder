package org.ddn.bencode.impl.entries;

import org.ddn.bencode.api.entries.EntryFactory;
import org.ddn.bencode.api.entries.reader.EntryReader;
import org.ddn.bencode.api.entries.reader.EntryReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Factory produces entry reader implementation
 * @see org.ddn.bencode.api.entries.reader.EntryReader
 */
public class EntryReaderFactoryImpl implements EntryReaderFactory {

    private final EntryFactory entryFactory;

    /**
     * Creates a new instance
     * @param entryFactory factory to create entities
     */
    public EntryReaderFactoryImpl(EntryFactory entryFactory) {
        this.entryFactory = entryFactory;
    }

    @Override
    public EntryReader create(InputStream in) {
        return new EntryReaderImpl(entryFactory, in);
    }
}

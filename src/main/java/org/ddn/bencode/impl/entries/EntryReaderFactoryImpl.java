package org.ddn.bencode.impl.entries;

import org.ddn.bencode.api.entries.EntryFactory;
import org.ddn.bencode.api.entries.reader.EntryReader;
import org.ddn.bencode.api.entries.reader.EntryReaderFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Denis on 21.11.2015.
 */
public class EntryReaderFactoryImpl implements EntryReaderFactory {

    private final EntryFactory entryFactory;

    public EntryReaderFactoryImpl(EntryFactory entryFactory) {
        this.entryFactory = entryFactory;
    }

    public EntryReader create(InputStream in) {
        return new EntryReaderImpl(entryFactory, in);
    }
}

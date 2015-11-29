package org.ddn.bencode.impl;

import org.ddn.bencode.api.BDecoder;
import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.reader.EntryReader;
import org.ddn.bencode.api.entries.reader.EntryReaderFactory;
import org.ddn.bencode.impl.entries.EntryFactoryImpl;
import org.ddn.bencode.impl.entries.EntryReaderFactoryImpl;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.Collection;

/**
 * Default B-Encode deserializer.
 * The main functionality of the class is to read data from input stream and construct entries from it
 * @see org.ddn.bencode.api.entries.Entry
 */
public class BDecoderImpl implements BDecoder {

    @NotNull
    private final EntryReaderFactory entryReaderFactory;

    /**
     * Default constructor
     */
    public BDecoderImpl() {
        this.entryReaderFactory = new EntryReaderFactoryImpl(new EntryFactoryImpl());
    }

    /**
     * Constructs a new instance with a given EntryReaderFactory
     * @param entryReaderFactory custom entryReaderFactory
     * @see org.ddn.bencode.api.entries.reader.EntryReaderFactory
     */
    public BDecoderImpl(EntryReaderFactory entryReaderFactory) {
        this.entryReaderFactory = entryReaderFactory;
    }

    @Override
    public void decode(InputStream in, Collection<? super Entry> entries) throws BEncodeException {
        EntryReader reader = entryReaderFactory.create(in);
        Entry e;
        while ((e = reader.readEntry()) != null) {
            entries.add(e);
        }
    }
}

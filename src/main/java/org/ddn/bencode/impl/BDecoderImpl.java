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
 * Created by Denis on 21.11.2015.
 */
public class BDecoderImpl implements BDecoder {

    @NotNull
    private final EntryReaderFactory entryReaderFactory;

    public BDecoderImpl() {
        this.entryReaderFactory = new EntryReaderFactoryImpl(new EntryFactoryImpl());
    }

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

package org.ddn.bencode.api.entries.reader;

import java.io.InputStream;

/**
 * Created by Denis on 20.11.2015.
 */
public interface EntryReaderFactory {

    EntryReader create(InputStream in);
}

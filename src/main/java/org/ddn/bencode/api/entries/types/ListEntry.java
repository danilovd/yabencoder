package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.CompositeEntry;
import org.ddn.bencode.api.entries.Entry;

import java.util.List;

/**
 * Created by Denis on 07.11.2015.
 */
public interface ListEntry extends CompositeEntry<List<?>> {

    List<Entry> getEntries();
    List<Object> getValue();
}

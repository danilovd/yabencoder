package org.ddn.bencode.api.entries;

import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.types.IntegerEntry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.api.entries.types.StringEntry;

import java.util.List;
import java.util.Map;

/**
 * Created by Denis on 04.11.2015.
 */
public interface EntryFactory {

    StringEntry createStringEntry(String value);
    IntegerEntry createIntegerEntry(long value);
    ListEntry createListEntry(List<? extends Entry> entry);
    ListEntry createListEntry(Entry... entries);
    DictionaryEntry createDictionaryEntry(Map<StringEntry, Entry> entryMap);
}

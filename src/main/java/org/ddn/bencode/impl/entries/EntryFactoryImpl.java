package org.ddn.bencode.impl.entries;

import org.ddn.bencode.api.entries.*;
import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.types.IntegerEntry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.api.entries.types.StringEntry;
import org.ddn.bencode.impl.entries.types.DictionaryEntryImpl;
import org.ddn.bencode.impl.entries.types.IntegerEntryImpl;
import org.ddn.bencode.impl.entries.types.ListEntryImpl;
import org.ddn.bencode.impl.entries.types.StringEntryImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Denis on 04.11.2015.
 */
public class EntryFactoryImpl implements EntryFactory {

    public StringEntry createStringEntry(String value) {
        if(value == null){
            throw new IllegalArgumentException("Value is null");
        }
        return new StringEntryImpl(value);
    }

    public IntegerEntry createIntegerEntry(Long value) {
        return new IntegerEntryImpl(value);
    }

    public ListEntry createListEntry(List<? extends Entry> entryList) {
        if(entryList == null){
            throw new IllegalArgumentException("List is null");
        }
        return new ListEntryImpl(entryList);
    }

    public ListEntry createListEntry(Entry... entries) {
        return createListEntry(Arrays.asList(entries));
    }

    public DictionaryEntry createDictionaryEntry(Map<StringEntry, Entry> entryMap) {
        if(entryMap == null){
            throw new IllegalArgumentException("Map is null");
        }
        return new DictionaryEntryImpl(entryMap);
    }
}

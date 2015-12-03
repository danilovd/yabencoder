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
 * Factory produces implementations of B-Encode Entries
 * @see org.ddn.bencode.api.entries.Entry
 */
public class EntryFactoryImpl implements EntryFactory {

    @Override
    public StringEntry createStringEntry(String value) {
        if(value == null){
            throw new IllegalArgumentException("Value is null");
        }
        return new StringEntryImpl(value);
    }

    @Override
    public IntegerEntry createIntegerEntry(Long value) {
        if(value == null){
            throw new IllegalArgumentException("Value is null");
        }
        return new IntegerEntryImpl(value);
    }

    @Override
    public IntegerEntry createIntegerEntry(Integer value) {
        if(value == null){
            throw new IllegalArgumentException("Value is null");
        }
        return new IntegerEntryImpl(value);
    }

    @Override
    public ListEntry createListEntry(List<? extends Entry> entryList) {
        if(entryList == null){
            throw new IllegalArgumentException("List is null");
        }
        return new ListEntryImpl(entryList);
    }

    @Override
    public ListEntry createListEntry(Entry... entries) {
        return createListEntry(Arrays.asList(entries));
    }

    @Override
    public DictionaryEntry createDictionaryEntry(Map<StringEntry, Entry> entryMap) {
        if(entryMap == null){
            throw new IllegalArgumentException("Map is null");
        }
        return new DictionaryEntryImpl(entryMap);
    }
}

package org.ddn.bencode.impl.entries.utils;

import org.ddn.bencode.api.entries.CompositeEntry;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.EntryFactory;
import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.api.entries.types.StringEntry;
import org.ddn.bencode.impl.entries.EntryFactoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class is intended for building list and dictionary entries
 * <p>
 * Example:
 * <pre>
 *     {@code
 *     DictionaryEntry e = dictionary()
 *           .entry("name", "Arthur")
 *           .entry("number", 42L)
 *           .entry("picture", "")
 *           .entry("planets", list("Earth", "Somewhere else", "Old Earth"))
 *           .create();
 *     }
 * </pre>
 */
public class CompositeEntryBuilder<T extends CompositeEntry> {

    private final EntryFactory entryFactory = new EntryFactoryImpl();
    private Map<StringEntry,Entry> dictionary;
    private List<Entry> list;

    public CompositeEntryBuilder(Map<StringEntry, Entry> dictionary) {
        this.dictionary = dictionary;
    }

    public CompositeEntryBuilder(List<Entry> list) {
        this.list = list;
    }

    public static CompositeEntryBuilder<DictionaryEntry> dictionary(){
        return new CompositeEntryBuilder(new HashMap());
    }

    public static CompositeEntryBuilder<ListEntry> list(){
        return new CompositeEntryBuilder(new ArrayList());
    }

    public static CompositeEntryBuilder<ListEntry> list(Object...args){
        CompositeEntryBuilder builder = list();
        for(Object o:args){
            builder.entry(o);
        }
        return builder;
    }

    public T create(){
        if(dictionary != null){
            return (T) entryFactory.createDictionaryEntry(dictionary);
        } else {
            return (T) entryFactory.createListEntry(list);
        }
    }

    public CompositeEntryBuilder<T> entry(String key, Object value){
        if(dictionary == null){
            throw new IllegalArgumentException("Not a dictionary builder");
        }
        Entry valueEntry = evaluate(value);
        dictionary.put(entryFactory.createStringEntry(key), valueEntry);
        return this;
    }

    private Entry evaluate(Object value){
        Entry valueEntry = null;
        if(value instanceof String){
            valueEntry = entryFactory.createStringEntry(value.toString());
        } else if(value instanceof Long){
            valueEntry = entryFactory.createIntegerEntry(Long.parseLong(value.toString()));
        } else if(value instanceof CompositeEntryBuilder){
            valueEntry = ((CompositeEntryBuilder) value).create();
        }
        return valueEntry;
    }

    public CompositeEntryBuilder<T> entry(Object value){
        if(list == null){
            throw new IllegalArgumentException("Not a list builder");
        }
        list.add(evaluate(value));
        return this;
    }
}

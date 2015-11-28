package org.ddn.bencode.api.entries;

import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.types.IntegerEntry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.api.entries.types.StringEntry;

import java.util.List;
import java.util.Map;

/**
 * Factory produces B-Encode entries
 */
public interface EntryFactory {

    /**
     * returns a new entry of string type with a given value
     * @param value string to be wrapped by entry
     * @return string entry
     */
    StringEntry createStringEntry(String value);

    /**
     * returns a new entry of integer type with a given value
     * @param value integer to be wrapped by entry
     * @return integer entry
     */
    IntegerEntry createIntegerEntry(Long value);

    /**
     * creates a new list entry containing given entries
     * @param entries entries to be added to the list
     * @return list entry
     */
    ListEntry createListEntry(List<? extends Entry> entries);

    /**
     * creates a new list entry containing given entries
     * @param entries entries to be added to the list
     * @return list entry
     */
    ListEntry createListEntry(Entry... entries);

    /**
     * creates a new dictionary entry from a given map of entries
     * @param entryMap map of entries, keys are string entries, value can be any entry
     * @return dictionary entry
     */
    DictionaryEntry createDictionaryEntry(Map<StringEntry, Entry> entryMap);
}

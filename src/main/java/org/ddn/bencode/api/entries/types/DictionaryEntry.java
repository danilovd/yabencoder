package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.CompositeEntry;
import org.ddn.bencode.api.entries.Entry;

import java.util.Map;

/**
 * Created by Denis on 07.11.2015.
 */
public interface DictionaryEntry extends CompositeEntry<Map<String, ?>> {

    Map<String, Object> getValue();

    Entry get(StringEntry keyEntry);
    Entry put(StringEntry key, Entry value);
    Entry remove(StringEntry key);
}

package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.CompositeEntry;
import org.ddn.bencode.api.entries.Entry;

import java.util.Map;

/**
 * Represents dictionary entry
 * @see java.util.Map
 */
public interface DictionaryEntry extends CompositeEntry<Map<String, ?>> {

    /**
     * Method returns {@link java.util.Map} dictionary filled with generic values for each key and value entries pair.
     * @return map with generic values
     */
    Map<String, Object> getValue();

    /**
     * returns the entry that is associated with the given keyEntry
     * @param keyEntry key entry
     * @return entry associated with the key entry
     */
    Entry get(StringEntry keyEntry);

    /**
     * method associates the given entry with key entry
     * @param key string entry
     * @param value entry
     * @return previous entry associated with the key entry
     */
    Entry put(StringEntry key, Entry value);

    /**
     * removes key and value association from the dictionary
     * @param key key entry
     * @return entry that was associated with the key entry
     */
    Entry remove(StringEntry key);
}

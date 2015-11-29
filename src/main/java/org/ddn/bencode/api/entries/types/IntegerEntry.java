package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.Entry;

/**
 * The interface represents integer entry.
 * Value associated with entry has {@link java.lang.Long} type
 */
public interface IntegerEntry extends Entry<Long> {

    /**
     * returns value associated with the entry
     * @return value
     */
    Long getValue();
}

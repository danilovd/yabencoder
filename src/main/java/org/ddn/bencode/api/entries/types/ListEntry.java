package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.CompositeEntry;
import org.ddn.bencode.api.entries.Entry;

import java.util.List;

/**
 * List entry interface.
 * Lists are encoded as follows: l<bencoded values>e
 * The initial l and trailing e are beginning and ending delimiters.
 * Lists may contain any bencoded type, including integers, strings, dictionaries, and even lists within other lists.
 * Example: l4:spam4:eggse represents the list of two strings: [ "spam", "eggs" ]
 * Example: le represents an empty list: []
 */
public interface ListEntry extends CompositeEntry<List<?>> {

    List<Entry> getEntries();
    List<Object> getValue();
}

package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.Entry;

/**
 * Bencoded strings are encoded as follows:
 * {@literal <}string length encoded in base ten ASCII{@literal >}:{@literal <}string data{@literal >}, or key:value
 * <p>
 * Note that there is no constant beginning delimiter, and no ending delimiter.
 * <p>
 * Example: 4:spam represents the string "spam"
 * Example: 0: represents the empty string ""
 */
public interface StringEntry extends Entry<String> {

    String getValue();
}

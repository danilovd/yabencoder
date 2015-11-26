package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.Entry;

/**
 * Created by Denis on 04.11.2015.
 */
public interface StringEntry extends Entry<String> {

    String getValue();
}

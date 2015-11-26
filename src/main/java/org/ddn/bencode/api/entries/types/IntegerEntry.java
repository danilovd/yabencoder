package org.ddn.bencode.api.entries.types;

import org.ddn.bencode.api.entries.Entry;

/**
 * Created by Denis on 07.11.2015.
 */
public interface IntegerEntry extends Entry<Long> {

    Long getValue();
}

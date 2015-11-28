package org.ddn.bencode.api.entries;

/**
 * Primitive interface for holding values
 */
public interface ValueHolder<T> {

    /**
     * method returns value that current object holds
     * @return value
     */
    T getValue();
}

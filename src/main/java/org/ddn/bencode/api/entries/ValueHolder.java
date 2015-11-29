package org.ddn.bencode.api.entries;

/**
 * Primitive interface for holding values
 */
public interface ValueHolder<T> {

    /**
     * method returns value that the current object holds
     * @return current value
     */
    T getValue();
}

package org.ddn.bencode.api.entries;

/**
 * marker interface for B-Encode entries which hold additional values such as dictionary and list
 * @param <T> type of value that entry holds
 */
public interface CompositeEntry<T> extends Entry<T> {

}

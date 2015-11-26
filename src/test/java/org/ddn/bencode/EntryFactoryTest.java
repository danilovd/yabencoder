package org.ddn.bencode;

import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.EntryFactory;
import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.types.IntegerEntry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.api.entries.types.StringEntry;
import org.ddn.bencode.impl.entries.EntryFactoryImpl;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class EntryFactoryTest {

    private EntryFactory entryFactory = new EntryFactoryImpl();

    @Test
    public void testEntryFactory_createStringEntry(){
        final String expectedValue = "sampleValue";
        Entry entry = entryFactory.createStringEntry(expectedValue);
        assertTrue("Entry must be of string type", entry instanceof StringEntry);
        assertEquals("Entry value must be original string", expectedValue, entry.getValue());
    }

    @Test
    public void testEntryFactory_createIntegerEntry(){
        final long expectedValue = 42;
        Entry entry = entryFactory.createIntegerEntry(expectedValue);
        assertTrue("Entry must be of int type", entry instanceof IntegerEntry);
        assertEquals("Entry value must be original int", expectedValue, entry.getValue());
    }

    @Test
    public void testEntryFactory_createListEntry(){
        final Entry item1 = entryFactory.createIntegerEntry(42);
        final Entry item2 = entryFactory.createStringEntry("sampleValue");
        final List<? extends Entry> list = Arrays.asList(item1, item2);

        Entry entry = entryFactory.createListEntry(list);
        assertTrue("Entry must be of list type", entry instanceof ListEntry);
        ListEntry listEntry = (ListEntry) entry;

        assertThat("Entry list must contain items", listEntry.getEntries(), contains(item1, item2));
    }

    @Test
    public void testEntryFactory_createDictionaryEntry(){
        Map<StringEntry, Entry> items = new HashMap<>();
        final StringEntry key = entryFactory.createStringEntry("foo");
        final Entry value = entryFactory.createIntegerEntry(42);
        items.put(key, value);

        Entry entry = entryFactory.createDictionaryEntry(items);
        assertTrue("Entry must be of dictionary type", entry instanceof DictionaryEntry);

        DictionaryEntry dictionaryEntry = (DictionaryEntry) entry;
        assertEquals("Unexpected value is returned by key", dictionaryEntry.get(key), value);
    }
}

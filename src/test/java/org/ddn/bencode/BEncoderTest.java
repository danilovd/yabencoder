package org.ddn.bencode;

import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.BEncoder;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.EntryFactory;
import org.ddn.bencode.api.entries.types.StringEntry;
import org.ddn.bencode.impl.BEncoderImpl;
import org.ddn.bencode.impl.entries.EntryFactoryImpl;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.ddn.bencode.api.BEncodeFormat.CHARSET;
import static org.ddn.bencode.BEncodeMatchers.*;

public class BEncoderTest {

    private BEncoder bEncoder = new BEncoderImpl();
    private EntryFactory entryFactory = new EntryFactoryImpl();

    @Test
    public void testBEncoder_encodeNegativeInteger() throws BEncodeException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, entryFactory.createIntegerEntry(-42));
        assertTrue("Incorrect encoding of integer entry", new String(bout.toByteArray(), CHARSET).contains("i-42e"));
    }

    @Test
    public void testBEncoder_encodeStringEntry() throws BEncodeException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, entryFactory.createStringEntry("hello : world!"));
        assertTrue("Incorrect encoding of string entry", new String(bout.toByteArray(), CHARSET).contains("14:hello : world!"));
    }

    @Test
    public void testBEncoder_encodeListEntry() throws BEncodeException {
        Entry list = entryFactory.createListEntry(entryFactory.createStringEntry("spam"), entryFactory.createIntegerEntry(42));
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, list);

        assertThat("Incorrect encoding of list entry: must start with l", bout, beginsWith("l"));
        assertThat("Incorrect encoding of list entry: must end with e", bout, endsWith("e"));
        assertThat("Incorrect encoding of list entry: missing integer entry", bout, outputStreamContainsEntry("i42e"));
        assertThat("Incorrect encoding of list entry: missing string entry", bout, outputStreamContainsEntry("4:spam"));
    }

    @Test
    public void testBEncoder_encodeDictionary() throws BEncodeException {

        Map<StringEntry, Entry> dict = new HashMap<StringEntry, Entry>();
        dict.put(entryFactory.createStringEntry("bar"), entryFactory.createStringEntry("spam"));
        dict.put(entryFactory.createStringEntry("foo"), entryFactory.createIntegerEntry(42));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, entryFactory.createDictionaryEntry(dict));

        assertThat("Incorrect encoding of dictionary entry: missing entry", bout, outputStreamContainsEntryInDictionary("3:bar", "4:spam"));
        assertThat("Incorrect encoding of dictionary entry: missing entry", bout, outputStreamContainsEntryInDictionary("3:foo", "i42e"));
    }

    @Test
    public void testBEncoder_encodeNestedListEntry() throws BEncodeException {
        Entry item1 = entryFactory.createIntegerEntry(42);
        Entry item2 = entryFactory.createStringEntry("hello world!");
        Entry list = entryFactory.createListEntry(entryFactory.createListEntry(item1, item2));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, list);

        assertThat("Incorrect encoding of nested list", bout, outputStreamContainsEntryInNestedList("i42e"));
        assertThat("Incorrect encoding of nested list", bout, outputStreamContainsEntryInNestedList("12:hello world!"));
    }

    @Test
    public void testBEncoder_encodeNestedDictionaryEntry() throws BEncodeException {
        StringEntry key = entryFactory.createStringEntry("foo");
        StringEntry parentKey = entryFactory.createStringEntry("bar");
        Entry value = entryFactory.createIntegerEntry(42);

        Map<StringEntry, Entry> items = new HashMap<>();
        items.put(key, value);

        Entry dictionary = entryFactory.createDictionaryEntry(items);

        items = new HashMap<>();
        items.put(parentKey, dictionary);

        Entry parentDictionary = entryFactory.createDictionaryEntry(items);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, parentDictionary);

        assertThat("Incorrect encoding of nested dictionary",bout, outputStreamContainsNestedDictionaryEntry("3:bar", "3:foo", "i42e"));
    }

    @Test
    public void testBEncoder_encodeCollectionOfEntries() throws BEncodeException {

        Collection<Entry> entrySet = new ArrayList<>();
        entrySet.add(entryFactory.createStringEntry("sample"));
        entrySet.add(entryFactory.createIntegerEntry(42L));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, entrySet);

        assertThat("Missing string entry", bout, outputStreamContainsEntry("6:sample"));
        assertThat("Missing int entry", bout, outputStreamContainsEntry("i42e"));
    }
}


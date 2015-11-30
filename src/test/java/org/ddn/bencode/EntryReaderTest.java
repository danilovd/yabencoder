package org.ddn.bencode;

import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.entries.reader.BEncodeParsingException;
import org.ddn.bencode.api.entries.reader.EntryReader;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.impl.entries.EntryReaderFactoryImpl;
import org.ddn.bencode.impl.entries.EntryFactoryImpl;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;
import static org.ddn.bencode.BEncodeMatchers.*;
import static org.ddn.bencode.api.BEncodeFormat.CHARSET;

public class EntryReaderTest {

    private static EntryReader createReader(byte[] binary){
        return new EntryReaderFactoryImpl(new EntryFactoryImpl()).create(new ByteArrayInputStream(binary));
    }

    @Test
    public void testEntryReader_readString() throws BEncodeException {

        byte[] binary = "5:abcde".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of entry", e, isStringValueEntry("abcde"));
    }

    @Test
    public void testEntryReader_readStringHavingExcessChars() throws BEncodeException {
        byte[] binary = "5:abcde_excess".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of entry", e, isStringValueEntry("abcde"));
    }

    @Test
    public void testEntryReader_readEmptyString() throws BEncodeException {
        byte[] binary = "   0:    ".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of empty string", e, isStringValueEntry(""));
    }

    @Test
    public void testEntryReader_readStringContainingColon() throws BEncodeException {
        byte[] binary = "7:abc2:de".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of entry", e, isStringValueEntry("abc2:de"));
    }

    @Test
    public void testEntryReader_readInteger() throws BEncodeException {
        byte[] binary = "i42e".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of integer entry", e, isIntegerValueEntry(42L));
    }

    @Test
    public void testEntryReader_readNegativeInteger() throws BEncodeException {
        byte[] binary = "i-42e".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of integer entry", e, isIntegerValueEntry(-42L));
    }

    @Test
    public void testEntryReader_readZero() throws BEncodeException {
        byte[] binary = "i0e".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of integer entry", e, isIntegerValueEntry(0L));
    }

    @Test
    public void testEntryReader_readBigInteger() throws BEncodeException {
        byte[] binary = "i-912359385587674874e".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of integer entry", e, isIntegerValueEntry(-912359385587674874L));
    }

    @Test
    public void testEntryReader_readList() throws BEncodeException {
        byte[] binary = "li42e5:helloe".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of list entry", e, isListValueEntry(42L, "hello"));
    }

    @Test
    public void testEntryReader_readEmptyList() throws BEncodeException {
        byte[] binary = "le".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        assertThat("Invalid parsing of list entry", e, isEmptyList());
    }

    @Test
    public void testEntryReader_readNestedList() throws BEncodeException {
        byte[] binary = "lli42e5:helloee".getBytes(CHARSET);
        Entry e = createReader(binary).readEntry();
        ListEntry listEntry = (ListEntry) e;
        listEntry = (ListEntry) listEntry.getEntries().get(0);
        assertThat("Invalid parsing of list entry", listEntry, isListValueEntry(42L, "hello"));
    }

    @Test
    public void testEntryReader_readDictionary() throws BEncodeException {
        byte[] bytes = "d3:foo4:spam3:bari42ee".getBytes(CHARSET);
        Entry e = createReader(bytes).readEntry();
        assertThat("Invalid parsing of dictionary entry", e, dictionaryContainsEntry("foo", "spam"));
        assertThat("Invalid parsing of dictionary entry", e, dictionaryContainsEntry("bar", 42L));
    }

    @Test
    public void testEntryReader_readEmptyDictionary() throws BEncodeException {
        byte[] bytes = "de".getBytes(CHARSET);
        Entry e = createReader(bytes).readEntry();
        assertThat("Invalid parsing of dictionary entry", e, isEmptyDictionary());
    }

    @Test
    public void testEntryReader_readMultipleEntries() throws BEncodeException {
        byte[] bytes = "5:hello  i42e".getBytes(CHARSET);
        EntryReader reader = createReader(bytes);
        Entry e = reader.readEntry();
        assertThat("Invalid parsing of string entry", e, isStringValueEntry("hello"));

        e = reader.readEntry();
        assertThat("Invalid parsing of integer entry", e, isIntegerValueEntry(42L));
    }

    @Test(expected = BEncodeParsingException.class)
    public void testEntryReader_readInvalidInteger_fail() throws BEncodeException {
        byte[] bytes = "iabcde".getBytes(CHARSET);
        EntryReader reader = createReader(bytes);
        Entry e = reader.readEntry();
        assertNotNull(e);
    }

    @Test(expected = BEncodeParsingException.class)
    public void testEntryReader_readShortString_fail() throws BEncodeException {
        byte[] bytes = "5:abc".getBytes(CHARSET);
        EntryReader reader = createReader(bytes);
        Entry e = reader.readEntry();
        assertNotNull(e);
    }

    @Test(expected = BEncodeParsingException.class)
    public void testEntryReader_readInvalidList_fail() throws BEncodeException {
        byte[] bytes = "li42e5:hello".getBytes(CHARSET); // missing end suffix
        EntryReader reader = createReader(bytes);
        Entry e = reader.readEntry();
        assertNotNull(e);
    }

    @Test(expected = BEncodeParsingException.class)
    public void testEntryReader_readInvalidDictionary_fail() throws BEncodeException {
        byte[] bytes = "d 3:foo l i42e 3:bar e".getBytes(CHARSET); // missing end suffix
        EntryReader reader = createReader(bytes);
        Entry e = reader.readEntry();
        assertNotNull(e);
    }

    @Test(expected = BEncodeParsingException.class)
    public void testEntryReader_readInvalidDictionary_invalidKeyType_fail() throws BEncodeException {
        byte[] bytes = "d i42e 3:foo e".getBytes(CHARSET);
        EntryReader reader = createReader(bytes);
        Entry e = reader.readEntry();
        assertNotNull(e);
    }

    @Test(expected = BEncodeParsingException.class)
    public void testEntryReader_readInvalidDictionary_missingValue_fail() throws BEncodeException {
        byte[] bytes = "d 3:foo e".getBytes(CHARSET);
        EntryReader reader = createReader(bytes);
        Entry e = reader.readEntry();
        assertNotNull(e);
    }
}

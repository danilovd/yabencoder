package org.ddn.bencode;

import org.ddn.bencode.api.entries.*;
import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.types.IntegerEntry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.api.entries.types.StringEntry;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.ddn.bencode.api.BEncodeFormat.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Denis on 04.11.2015.
 */
public class BEncodeMatchers {

    public static Matcher<ByteArrayOutputStream> beginsWith(final String prefix){

        return new BaseMatcher<ByteArrayOutputStream>() {
            public boolean matches(Object o) {
                byte[] bytes = ((ByteArrayOutputStream) o).toByteArray();
                String s = new String(bytes, CHARSET);
                return s.trim().startsWith(prefix);
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<ByteArrayOutputStream> endsWith(final String suffix){

        return new BaseMatcher<ByteArrayOutputStream>() {
            public boolean matches(Object o) {
                byte[] bytes = ((ByteArrayOutputStream) o).toByteArray();
                String s = new String(bytes, CHARSET);
                return s.trim().endsWith(suffix);
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<ByteArrayOutputStream> outputStreamContainsEntry(final String entry){

        return new BaseMatcher<ByteArrayOutputStream>() {
            public boolean matches(Object o) {
                ByteArrayOutputStream out = (ByteArrayOutputStream) o;
                String str = new String(out.toByteArray(), CHARSET);
                return str.contains(entry);
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<ByteArrayOutputStream> outputStreamContainsEntryInDictionary(final String key, final String value){

        return new BaseMatcher<ByteArrayOutputStream>() {
            public boolean matches(Object o) {
                ByteArrayOutputStream out = (ByteArrayOutputStream) o;
                String s = new String(out.toByteArray(), CHARSET);

                Pattern p = Pattern.compile("d.*" + key + "[\\s\\n\\t]*" + value + ".*e", Pattern.DOTALL);
                return p.matcher(s).find();
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<ByteArrayOutputStream> outputStreamContainsEntryInList(final String entry){

        return new BaseMatcher<ByteArrayOutputStream>() {
            public boolean matches(Object o) {
                ByteArrayOutputStream out = (ByteArrayOutputStream) o;
                String s = new String(out.toByteArray(), CHARSET);
                Pattern p = Pattern.compile("l.*"+ entry + ".*e", Pattern.DOTALL);
                return p.matcher(s).find();
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<ByteArrayOutputStream> outputStreamContainsNestedDictionaryEntry(final String parentKey,
                                                                                           final String key, final String value){

        return new BaseMatcher<ByteArrayOutputStream>() {
            public boolean matches(Object o) {
                ByteArrayOutputStream out = (ByteArrayOutputStream) o;
                String s = new String(out.toByteArray(), CHARSET);
                Pattern p = Pattern.compile("d.*" + parentKey + "[\\s\\t\\n]*d.*"+ key + "[\\s\\n\\t]*" + value + ".*e.*e", Pattern.DOTALL);
                return p.matcher(s).find();
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<ByteArrayOutputStream> outputStreamContainsEntryInNestedList(final String entry){

        return new BaseMatcher<ByteArrayOutputStream>() {
            public boolean matches(Object o) {
                ByteArrayOutputStream out = (ByteArrayOutputStream) o;
                String s = new String(out.toByteArray(), CHARSET);
                Pattern p = Pattern.compile("l.*l.*"+ entry + ".*e.*e", Pattern.DOTALL);
                return p.matcher(s).find();
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<Entry> isStringValueEntry(final String expectedValue){

        return new BaseMatcher<Entry>() {
            public boolean matches(Object o) {
                Entry entry = (Entry) o;
                assertTrue("Value must be string entry", entry instanceof StringEntry);
                StringEntry strEntry = (StringEntry) entry;
                assertEquals("Unexpected value", expectedValue, strEntry.getValue());
                return true;
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<Entry> isIntegerValueEntry(final Long expectedValue){

        return new BaseMatcher<Entry>() {
            public boolean matches(Object o) {
                Entry entry = (Entry) o;
                assertTrue("Value must be integer entry", entry instanceof IntegerEntry);
                IntegerEntry intEntry = (IntegerEntry) entry;
                assertEquals("Unexpected value", expectedValue, intEntry.getValue());
                return true;
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<Entry> isListValueEntry(final Object...args){

        return new BaseMatcher<Entry>() {
            public boolean matches(Object o) {
                Entry entry = (Entry) o;
                assertTrue("Value must be list entry", entry instanceof ListEntry);
                ListEntry listEntry = (ListEntry) entry;
                for(int i=0;i<args.length;i++) {
                    Object expectedValue = args[i];
                    assertEquals("Unexpected value", expectedValue, listEntry.getValue().get(i));
                }
                return true;
            }
            public void describeTo(Description description) {
            }
        };
    }




    public static Matcher<Entry> dictionaryContainsEntry(final String key, final Object value){

        return new BaseMatcher() {
            public boolean matches(Object o) {
                DictionaryEntry entry = (DictionaryEntry) o;

                Map<String,Object> dictionary = entry.getValue();
                assertTrue("Dictionary does not contain expected key", dictionary.containsKey(key));
                assertEquals("Dictionary does not contain expected value", value, dictionary.get(key));
                return true;
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<Entry> listContainsValues(final Object... args){

        return new BaseMatcher() {
            public boolean matches(Object o) {
                ListEntry entry = (ListEntry) o;

                List<Object> values = entry.getValue();
                for(Object arg:args) {
                    assertTrue("List does not contain expected value " + arg, values.contains(arg));
                }
                return true;
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<Entry> isEmptyDictionary(){

        return new BaseMatcher() {
            public boolean matches(Object o) {
                DictionaryEntry entry = (DictionaryEntry) o;

                Map<String,Object> dictionary = entry.getValue();
                assertTrue("Dictionary is not empty", dictionary.isEmpty());
                return true;
            }

            public void describeTo(Description description) {
            }
        };
    }

    public static Matcher<Entry> isEmptyList(){

        return new BaseMatcher() {
            public boolean matches(Object o) {
                ListEntry entry = (ListEntry) o;

                assertTrue("List is not empty", entry.getValue().isEmpty());
                return true;
            }

            public void describeTo(Description description) {
            }
        };
    }

}

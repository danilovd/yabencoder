package org.ddn.bencode;

import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.BEncodeFormat;
import org.ddn.bencode.api.BEncoder;
import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.impl.BEncoderImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import static org.junit.Assert.*;
import static org.ddn.bencode.api.BEncodeFormat.CHARSET;
import static org.ddn.bencode.impl.entries.utils.CompositeEntryBuilder.*;

public class BEncoderPrettyPrintingTest {

    private BEncoder bEncoder;

    @Before
    public void before(){
        Properties props = new Properties();
        props.put(BEncodeFormat.PROPERTY_PRETTY_PRINTING_ENABLED, true);
        bEncoder = new BEncoderImpl(props);
    }

    @Test
    public void testBEncoder_prettyPrinting() throws BEncodeException {

        DictionaryEntry e = dictionary()
                .entry("name", "Arthur")
                .entry("number", 42L)
                .entry("picture", "")
                .entry("planets", list("Earth", "Somewhere else", "Old Earth"))
                .create();
        
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        bEncoder.encode(bout, e);

        String result = new String(bout.toByteArray(), CHARSET);
        assertTrue("Formatted output should contain new lines", result.contains("\n"));
        assertTrue("Formatted output should contain tabs", result.contains("\t"));

    }
}

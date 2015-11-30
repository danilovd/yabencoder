package org.ddn.bencode;

import org.ddn.bencode.api.BDecoder;
import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.types.ListEntry;
import org.ddn.bencode.impl.BDecoderImpl;
import org.ddn.bencode.impl.entries.types.StringEntryImpl;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.ddn.bencode.api.BEncodeFormat.CHARSET;
import static org.ddn.bencode.BEncodeMatchers.*;

public class BDecoderTest {

    private BDecoder decoder = new BDecoderImpl();

    @Test
    public void testBDecoder_decodeCompositeEntries() throws BEncodeException {

        byte[] bytes =
                ("d\n" +
                "  4:name\n" +
                "  11:Arthur Dent\n" +
                "\n" +
                "  6:number\n" +
                "  i42e\n" +
                "\n" +
                "  7:picture\n" +
                "  0:\n" +
                "\n" +
                "  7:planets\n" +
                "  l\n" +
                "    5:Earth\n" +
                "    14:Somewhere else\n" +
                "    9:Old Earth\n" +
                "  e\n" +
                "e").getBytes(CHARSET);

        Collection<? super Entry> resultSet = new ArrayList<>();
        decoder.decode(new ByteArrayInputStream(bytes), resultSet);

        assertSame("ResultSet size must be equal to 1", 1, resultSet.size());
        DictionaryEntry d = (DictionaryEntry) resultSet.iterator().next();

        assertThat("Missing entry", d, dictionaryContainsEntry("name", "Arthur Dent"));
        assertThat("Missing entry", d, dictionaryContainsEntry("number", 42L));
        assertThat("Missing entry", d, dictionaryContainsEntry("picture", ""));

        assertThat("Missing entry", d.get(StringEntryImpl.valueOf("planets")), listContainsValues("Earth", "Somewhere else", "Old Earth"));
    }
}

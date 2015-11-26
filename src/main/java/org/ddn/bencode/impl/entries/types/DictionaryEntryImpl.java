package org.ddn.bencode.impl.entries.types;

import org.ddn.bencode.api.BEncodeContext;
import org.ddn.bencode.api.entries.types.DictionaryEntry;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.types.StringEntry;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static org.ddn.bencode.api.BEncodeFormat.*;

/**
 * Created by Denis on 04.11.2015.
 */
public class DictionaryEntryImpl implements DictionaryEntry {

    @NotNull
    private final Map<StringEntry, Entry> dictionary;

    public DictionaryEntryImpl(Map<StringEntry, Entry> dictionary) {
        this.dictionary = dictionary;
    }

    public void writeTo(BEncodeContext ctx, OutputStream out) throws IOException {

        if(ctx.isPrettyPrintingEnabled()){
            writeFormattedValue(ctx, out);
        } else {
            writeValue(ctx, out);
        }
    }

    private void writeValue(BEncodeContext ctx, OutputStream out) throws IOException {
        out.write(DICTIONARY_PREFIX);
        for(Map.Entry<StringEntry, Entry> e : dictionary.entrySet()){

            StringEntry key = e.getKey();
            Entry value = e.getValue();

            key.writeTo(ctx, out);
            value.writeTo(ctx, out);
        }
        out.write(END_SUFFIX);
    }

    private void writeFormattedValue(BEncodeContext ctx, OutputStream out) throws IOException {

        int offset = ctx.getPrintingOffset();
        byte[] offsetBytes = new byte[offset];
        Arrays.fill(offsetBytes, (byte) '\t');

        out.write(offsetBytes);

        out.write(DICTIONARY_PREFIX);
        out.write('\n');

        ctx.incrementPrintingOffset();
        for(Map.Entry<StringEntry, Entry> e : dictionary.entrySet()){

            StringEntry key = e.getKey();
            Entry value = e.getValue();

            key.writeTo(ctx, out);
            value.writeTo(ctx, out);
            out.write('\n');
        }
        ctx.decrementPrintingOffset();

        out.write(offsetBytes);
        out.write(END_SUFFIX);
        out.write('\n');
    }

    public Map<String, Object> getValue() {

        Map<String,Object> result = new HashMap<String, Object>();
        for(Map.Entry<StringEntry, Entry> e : dictionary.entrySet()){

            StringEntry key = e.getKey();
            Entry value = e.getValue();

            result.put(key.getValue(), value.getValue());
        }
        return result;
    }

    public Entry get(String key) {
        return get(StringEntryImpl.valueOf(key));
    }

    public Entry get(StringEntry keyEntry) {
        return dictionary.get(keyEntry);
    }

//    @Override
//    public Object getValue() {
//        return getDictionary();
//    }
}

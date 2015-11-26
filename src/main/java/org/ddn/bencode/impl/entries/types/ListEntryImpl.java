package org.ddn.bencode.impl.entries.types;

import org.ddn.bencode.api.BEncodeContext;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.types.ListEntry;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.ddn.bencode.api.BEncodeFormat.*;

/**
 * Created by Denis on 04.11.2015.
 */
public class ListEntryImpl implements ListEntry {

    @NotNull
    private final List<? extends Entry> entries;

    public ListEntryImpl(Entry... entries){
        this(Arrays.asList(entries));
    }

    public ListEntryImpl(List<? extends Entry> entries) {
        this.entries = entries;
    }

    public void writeTo(BEncodeContext ctx, OutputStream out) throws IOException {

        if(ctx.isPrettyPrintingEnabled()){

            writeFormattedValue(ctx, out);
        } else {
            writeValue(ctx, out);
        }

    }

    private void writeValue(BEncodeContext ctx, OutputStream out) throws IOException {
        out.write(LIST_PREFIX);
        for(Entry entry:entries){
            entry.writeTo(ctx, out);
        }
        out.write(END_SUFFIX);
    }

    private void writeFormattedValue(BEncodeContext ctx, OutputStream out) throws IOException {
        int offset = ctx.getPrintingOffset();
        byte[] offsetBytes = new byte[offset];
        Arrays.fill(offsetBytes, (byte) '\t');

        out.write(offsetBytes);

        out.write(LIST_PREFIX);
        out.write('\n');

        ctx.incrementPrintingOffset();

        for(Entry entry:entries){
            entry.writeTo(ctx, out);
        }
        ctx.decrementPrintingOffset();

        out.write(offsetBytes);
        out.write(END_SUFFIX);
        out.write('\n');
    }

    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public List<Object> getValue() {
        List<Object> result = new ArrayList<>(entries.size());
        for(Entry e:entries){
            result.add(e.getValue());
        }
        return result;
    }

}

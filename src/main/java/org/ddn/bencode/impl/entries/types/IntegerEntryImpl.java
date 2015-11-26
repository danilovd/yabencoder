package org.ddn.bencode.impl.entries.types;

import org.ddn.bencode.api.BEncodeContext;
import org.ddn.bencode.api.entries.types.IntegerEntry;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import static org.ddn.bencode.api.BEncodeFormat.*;

/**
 * Created by Denis on 04.11.2015.
 */
public class IntegerEntryImpl implements IntegerEntry {

    private final long value;

    public IntegerEntryImpl(long value) {
        this.value = value;
    }

    public void writeTo(BEncodeContext ctx, OutputStream out) throws IOException {
        if(ctx.isPrettyPrintingEnabled()){
            writeFormattedValue(ctx.getPrintingOffset(), out);
        } else {
            writeValue(out);
        }
    }

    private void writeValue(OutputStream out) throws IOException {
        out.write(INTEGER_PREFIX);
        out.write(String.valueOf(value).getBytes(CHARSET));
        out.write(END_SUFFIX);
    }

    private void writeFormattedValue(int offset, OutputStream out) throws IOException {
        for(int i=0;i<offset;i++) {
            out.write('\t');
        }
        writeValue(out);
        out.write('\n');
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "IntegerEntryImpl{" +
                "value=" + value +
                '}';
    }
}

package org.ddn.bencode.impl.entries.types;

import org.ddn.bencode.api.BEncodeContext;
import org.ddn.bencode.api.entries.types.StringEntry;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;

import static org.ddn.bencode.api.BEncodeFormat.*;

/**
 * Created by Denis on 04.11.2015.
 */
public class StringEntryImpl implements StringEntry {

    @NotNull
    private final String value;

    public StringEntryImpl(String value){
        this.value = value;
    }

    public void writeTo(BEncodeContext ctx, OutputStream out) throws IOException {

        if(ctx.isPrettyPrintingEnabled()){
            writeFormattedValue(ctx.getPrintingOffset(), out);
        } else {
            writeValue(out);
        }
    }

    private void writeFormattedValue(int offset, OutputStream out) throws IOException {
        for(int i=0;i<offset;i++){
            out.write('\t');
        }
        writeValue(out);
        out.write('\n');
    }

    private void writeValue(OutputStream out) throws IOException {
        int size = value.length();
        out.write(String.valueOf(size).getBytes(CHARSET));
        out.write(STRING_SEPARATOR);
        out.write(value.getBytes(CHARSET));
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringEntryImpl that = (StringEntryImpl) o;

        return value.equals(that.value);

    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "StringEntryImpl{" +
                "value='" + value + '\'' +
                '}';
    }

    public static StringEntry valueOf(String value){
        return new StringEntryImpl(value);
    }
}

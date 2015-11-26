package org.ddn.bencode.impl.entries;

import org.ddn.bencode.api.BEncodeContext;
import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.entries.reader.BEncodeParsingException;
import org.ddn.bencode.api.entries.reader.EntryReader;
import org.ddn.bencode.api.entries.Entry;
import org.ddn.bencode.api.entries.EntryFactory;
import org.ddn.bencode.api.entries.types.StringEntry;
import org.ddn.bencode.impl.BEncodeContextImpl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ddn.bencode.api.BEncodeFormat.*;

/**
 * Created by Denis on 16.11.2015.
 */
public class EntryReaderImpl implements EntryReader {

    public static final int STRING_BUFFER_SIZE = 1024;
    public static final int TEMP_BUFFER_SIZE = 20;

    private final InputStream in;
    private final EntryFactory entryFactory;
    private int currentPosition = 0;

    public EntryReaderImpl(EntryFactory entryFactory, InputStream in) {
        this.entryFactory = entryFactory;
        this.in = in;
    }

    @Override
    public Entry readEntry() throws BEncodeException {
        return readEntry(new BEncodeContextImpl());
    }

    public Entry readEntry(BEncodeContext ctx) throws BEncodeException {
        try {

            ByteBuffer buffer = null; // buffer to hold temp data

            char byteRead;

            int tmp;
            while ( (tmp = in.read()) != -1) {

                byteRead = (char) tmp;
                currentPosition++;
                switch (byteRead) {
                    case DICTIONARY_PREFIX:

                        ctx = new BEncodeContextImpl();
                        ctx.startDictionary();

                        Map<StringEntry, Entry> table = new HashMap<>();
                        StringEntry keyEntry;
                        Entry valueEntry;

                        while (true) {
                            Entry e = readEntry(ctx);
                            if (e == null) {
                                break;
                            }
                            if (!(e instanceof StringEntry)) {
                                throw new BEncodeParsingException("String entry is expected as a key of the dictionary at position "
                                        + currentPosition);
                            }
                            keyEntry = (StringEntry) e;
                            valueEntry = readEntry(ctx);
                            if (valueEntry == null) {
                                throw new BEncodeParsingException("Entry is missing as a value of the dictionary at position "
                                    + currentPosition);
                            }
                            table.put(keyEntry, valueEntry);
                        }
                        if(ctx.isDictionaryStarted()){
                            throw new BEncodeParsingException("Unexpected end of dictionary. Missing end suffix at position "
                                    + currentPosition);
                        }
                        return entryFactory.createDictionaryEntry(table);
                    case LIST_PREFIX:

                        ctx = new BEncodeContextImpl();
                        ctx.startList();

                        List<Entry> entryList = new ArrayList<>();
                        Entry e;
                        while ((e = readEntry(ctx)) != null) {
                            entryList.add(e);
                        }
                        if(ctx.isListStarted()){
                            throw new BEncodeParsingException("Unexpected end of list. Missing end suffix at position "
                                    + currentPosition);
                        }
                        return entryFactory.createListEntry(entryList);
                    case INTEGER_PREFIX:

                        ctx.startInt();
                        if(buffer == null) {
                            buffer = ByteBuffer.allocate(TEMP_BUFFER_SIZE);
                        }
                        break;
                    case END_SUFFIX:
                        if (ctx.isIntegerStarted()) {
                            // int ended
                            long value = readNumberFromBuffer(buffer);
                            buffer.flip();

                            ctx.endInt();
                            return entryFactory.createIntegerEntry(value);
                        } else if(ctx.isDictionaryStarted()){
                            ctx.endDictionary();
                        } else if(ctx.isListStarted()){
                            ctx.endList();
                        }
                        return null;

                    default:

                        if(byteRead == STRING_SEPARATOR){

                            if(!ctx.isStringStarted()){
                                throw new BEncodeParsingException("Unexpected character " +
                                        STRING_SEPARATOR + " at position " + currentPosition);
                            }

                            int length = (int) readNumberFromBuffer(buffer);
                            String value = readString(length);
                            ctx.endString();

                            currentPosition+=length;

                            return entryFactory.createStringEntry(value);
                        } else if (Character.isDigit(byteRead)) { // string started

                            if(!ctx.isIntegerStarted() && !ctx.isStringStarted()){

                                // start string
                                ctx.startString();
                                buffer = ByteBuffer.allocate(TEMP_BUFFER_SIZE); //buffer for str length
                            }
                            buffer.put((byte) byteRead);
                            break;

                        } else if(byteRead == MINUS_SIGN){
                            if(!ctx.isIntegerStarted()) {
                                throw new BEncodeParsingException("Unexpected character " + MINUS_SIGN +
                                        " at position " + currentPosition);
                            }
                            buffer.put((byte) byteRead);
                            break;

                        } else if (!Character.isSpaceChar(byteRead) && byteRead != '\n') {
                            throw new BEncodeParsingException("Unexpected character '" + byteRead + "' at position "
                                    + currentPosition);
                        }
                }
            }

        } catch (IOException e){
            throw new BEncodeException("IOException while reading entry from input stream", e);
        }

        return null;
    }

    private long readNumberFromBuffer(ByteBuffer buffer) throws BEncodeParsingException {
        long value = 0;
        long p = 1;
        boolean negative = false;
        for (int i = buffer.position()-1; i >= 0; i--) {

            if(i==0 && buffer.get(i) == '-'){
                negative = true;
                break;
            }
            int digit = Character.digit(buffer.get(i), 10);
            if (digit == -1) {
                throw new BEncodeParsingException("Unexpected character. Digit is expected at buffer pos " + i);
            }
            value += digit * p;
            p *= 10;
        }
        return negative ? -value : value;
    }

    private String readString(int length) throws IOException, BEncodeParsingException {
        byte[] buf = new byte[length > STRING_BUFFER_SIZE ? STRING_BUFFER_SIZE : length];
        int l;
        int bytesRead = 0;
        StringBuilder sb = new StringBuilder();
        int lengthToRead = buf.length;
        while( (l=in.read(buf,0, lengthToRead)) != -1 && bytesRead!=length){
            bytesRead+=l;
            sb.append(new String(buf, 0, l));
            lengthToRead = buf.length > length - bytesRead ? length - bytesRead : buf.length;
        }
        if(bytesRead < length){
            throw new BEncodeParsingException("Unexpected end of string entry");
        }
        return sb.toString();
    }
}

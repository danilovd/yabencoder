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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ddn.bencode.api.BEncodeFormat.*;

/**
 * Default EntryReader implementation.
 * The main functionality of the class is to read input stream, parse data, and create entries using EntryFactory.
 * @see org.ddn.bencode.api.entries.reader.EntryReader
 * @see org.ddn.bencode.api.entries.EntryFactory
 */
public class EntryReaderImpl implements EntryReader {

    /**
     * default size of buffer that is used for reading string entries
     */
    public static final int STRING_BUFFER_SIZE = 1024;

    private final InputStream in;
    private final EntryFactory entryFactory;
    private int currentPosition = 0;

    /**
     * creates a new EntryReader instance
     * @param entryFactory factory that creates entities
     * @param in input stream to read data from
     */
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

                        long value = readNumber(END_SUFFIX);
                        ctx.endInt();
                        return entryFactory.createIntegerEntry(value);
                        //break;
                    case END_SUFFIX:

                        if(ctx.isDictionaryStarted()){
                            ctx.endDictionary();
                        } else if(ctx.isListStarted()){
                            ctx.endList();
                        }
                        return null;

                    default:

                         if(Character.isDigit(byteRead)){
                             // it has to be string
                             ctx.startString();

                             // first character of string length we have already read
                             long strLength = readNumber(byteRead, STRING_SEPARATOR);
                             String strValue = readString((int)strLength);
                             ctx.endString();
                             return entryFactory.createStringEntry(strValue);
                         } else if(!Character.isSpaceChar(byteRead) && byteRead != '\n') {
                             throw new BEncodeParsingException("Illegal character " + byteRead + " at position "
                                     + currentPosition);
                         }
                }
            }

        } catch (IOException e){
            throw new BEncodeException("IOException while reading entry from input stream", e);
        }

        return null;
    }

    /**
     * method reads characters from input stream and calculates a number from it
     * @param firstCharacter the first digit of the number in form of character
     * @param endCharacter the end of the number character
     * @return calculated number
     * @throws IOException if failed to read data from the input stream
     * @throws BEncodeParsingException if invalid character has been read from the stream
     */
    private Long readNumber(char firstCharacter, char endCharacter) throws IOException, BEncodeParsingException {
        long value = Character.digit(firstCharacter, 10);
        boolean negative = false;

        int byteRead;
        int positionInNumber = 0;
        while((byteRead = in.read()) != -1){

            positionInNumber++;
            if(positionInNumber == 1 && byteRead == MINUS_SIGN){
                negative = true;
                continue;
            }
            if(byteRead == endCharacter){
                break;
            }
            int digit = Character.digit((char) byteRead, 10);
            if(digit == -1){
                throw new BEncodeParsingException("Unexpected character. Digit is expected at position "
                        + (currentPosition += positionInNumber));
            }
            value = value*10 + digit;
        }
        currentPosition += positionInNumber;
        return negative ?  -value : value;
    }

    /**
     * method does the same as {@link EntryReaderImpl#readNumber(char, char)} but if we have not read any digit from the number
     * @param endCharacter the end of the number character
     * @return calculated number
     * @throws IOException if failed to read from the stream
     * @throws BEncodeException if incorrect character was read from the stream
     */
    private Long readNumber(char endCharacter) throws IOException, BEncodeException {
        return readNumber('0', endCharacter);
    }

    /**
     * method reads a string from the input stream having specified length
     * @param length the expected length of the string
     * @return string
     * @throws IOException if failed to read from the stream
     * @throws BEncodeParsingException if failed to read data of sufficient length
     */
    private String readString(int length) throws IOException, BEncodeParsingException {
        // if length is too long we use buffer of fixed size
        byte[] buf = new byte[length > STRING_BUFFER_SIZE ? STRING_BUFFER_SIZE : length];
        StringBuilder sb = new StringBuilder();

        int l;
        int bytesRead = 0;
        int lengthToRead = buf.length; // shows how many bytes we should read. it is important for the last iteration
        while( (l=in.read(buf,0, lengthToRead)) != -1 && bytesRead!=length){
            bytesRead+=l;
            sb.append(new String(buf, 0, l));

            if(buf.length > length - bytesRead){
                lengthToRead = length - bytesRead;
            }
        }

        if(bytesRead < length){
            throw new BEncodeParsingException("Unexpected end of string entry. Expected length " + length);
        }
        currentPosition+=length;
        return sb.toString();
    }
}

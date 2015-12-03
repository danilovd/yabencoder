package org.ddn.bencode.api;

import org.ddn.bencode.api.entries.Entry;

import java.io.OutputStream;
import java.util.Collection;

/**
 * The root interface for encoding entities to B-Encode format
 * <p>B-Encode format description:</p>
 *   <ul>
 *      <li>
 *      Strings are length-prefixed base ten followed by a colon and the string.
 *      For example 4:spam corresponds to 'spam'.
 *      </li>
 *      <li>
 *      Integers are represented by an 'i' followed by the number in base 10 followed by an 'e'.
 *      For example i3e corresponds to 3 and i-3e corresponds to -3. Integers have no size limitation. i-0e is invalid.
 *      All encodings with a leading zero, such as i03e, are invalid, other than i0e, which of course corresponds to 0.
 *      </li>
 *      <li>
 *      Lists are encoded as an 'l' followed by their elements (also bencoded) followed by an 'e'. For example l4:spam4:eggse corresponds to ['spam', 'eggs'].
 *      </li>
 *      <li>Dictionaries are encoded as a 'd' followed by a list of alternating keys and their corresponding values followed by an 'e'.
 *      For example, d3:cow3:moo4:spam4:eggse corresponds to {'cow': 'moo', 'spam': 'eggs'} and d4:spaml1:a1:bee corresponds to {'spam': ['a', 'b']}.
 *      Keys must be strings and appear in sorted order (sorted as raw strings, not alphanumerics)
 *      </li>
 *  </ul>
 *  @see org.ddn.bencode.api.BDecoder
 *  @see org.ddn.bencode.api.entries.Entry
 */
public interface BEncoder {

    /**
     * Method converts B-Encode entry to bytes and write them to output stream
     * @param out stream where entries are written
     * @param e entry to be encoded
     * @throws BEncodeException when failed to write data to the stream
     * @see org.ddn.bencode.api.entries.Entry
     */
    void encode(OutputStream out, Entry e) throws BEncodeException;

    /**
     * Method converts B-Encode entries from collection to bytes and writes them to output stream
     * @param out stream where entries are written
     * @param entries entries to be encoded
     * @throws BEncodeException when failed to write data to the stream
     * @see org.ddn.bencode.api.entries.Entry
     */
    void encode(OutputStream out, Collection<? extends Entry> entries) throws BEncodeException;
}

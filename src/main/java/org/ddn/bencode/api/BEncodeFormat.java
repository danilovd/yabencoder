package org.ddn.bencode.api;

import java.nio.charset.Charset;

/**
 * This class holds constants used in B-Encode specification
 */
public final class BEncodeFormat {

    /**
     * B-Encode spec: Integers are prefixed with 'i'
     */
    public static final byte INTEGER_PREFIX = 'i';

    /**
     * B-Encode spec: dictionaries are prefixed with 'd'
     */
    public static final byte DICTIONARY_PREFIX = 'd';

    /**
     * B-Encode spec: lists are prefixed with 'l'
     */
    public static final byte LIST_PREFIX = 'l';

    /**
     * B-Encode spec: integers, dictionaries and lists are ended with 'e'
     */
    public static final byte END_SUFFIX = 'e';

    /**
     * B-Encode spec: String have the following format <length 10base ASCII>:<content>
     */
    public static final byte STRING_SEPARATOR = ':';

    /**
     * Minus to denote negative integers
     */
    public static final byte MINUS_SIGN = '-';

    /**
     * B-Encode charset
     */
    public static final Charset CHARSET = Charset.forName("US-ASCII");

    /**
     * Property that is used by default BEncoder implementation.
     * Possible values are: <code>true</code> enables formatted output
     * <code>false</code> default. disables formatted output
     */
    public static final String PROPERTY_PRETTY_PRINTING_ENABLED = "bencoder.format.printing.pretty";

    /**
     * Internal property that is used by default implementation to keep current offset for printing.
     * It is used only if pretty printing is enabled
     */
    public static final String PROPERTY_PRINTING_OFFSET = "bencoder.format.printing.offset";

}

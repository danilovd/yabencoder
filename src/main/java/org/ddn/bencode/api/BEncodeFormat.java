package org.ddn.bencode.api;

import java.nio.charset.Charset;

/**
 * This class holds constants used in B-Encode specification
 */
public final class BEncodeFormat {
    public static final byte INTEGER_PREFIX = 'i';
    public static final byte DICTIONARY_PREFIX = 'd';
    public static final byte LIST_PREFIX = 'l';
    public static final byte END_SUFFIX = 'e';
    public static final byte STRING_SEPARATOR = ':';
    public static final byte MINUS_SIGN = '-';

    public static final Charset CHARSET = Charset.forName("US-ASCII");

    public static final String PROPERTY_PRETTY_PRINTING_ENABLED = "bencoder.format.printing.pretty";
    public static final String PROPERTY_PRINTING_OFFSET = "bencoder.format.printing.offset";

}

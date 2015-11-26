package org.ddn.bencode.api;

/**
 * The class is created during serialization/deserialization process for storing parameters and temporary data
 * 
 */
public interface BEncodeContext {

    /**
     * method returns custom property value by name
     * @param propertyName name
     * @return property value
     */
    Object getProperty(String propertyName);

    /**
     * method sets custom property
     * @param propertyName name
     * @param value value
     */
    void setProperty(String propertyName, Object value);

    /**
     * method returns <code>true</code> if the property {@link BEncodeFormat#PROPERTY_PRETTY_PRINTING_ENABLED} is set to true.
     * The property is used for serialization process and enables formatted output
     * @return <code>true</code> if pretty printing is enabled
     */
    boolean isPrettyPrintingEnabled();

    /**
     * method returns current offset if formatted printing is enabled
     * @see org.ddn.bencode.api.BEncodeContext#isPrettyPrintingEnabled()
     * @return offset
     */
    int getPrintingOffset();

    /**
     * method increases current offset
     * @see org.ddn.bencode.api.BEncodeContext#isPrettyPrintingEnabled()
     */
    void incrementPrintingOffset();

    /**
     * method decreases current offset
     * @see org.ddn.bencode.api.BEncodeContext#isPrettyPrintingEnabled()
     */
    void decrementPrintingOffset();

    /**
     * method shows that {@link org.ddn.bencode.api.BEncodeFormat#LIST_PREFIX}
     * has appeared but end suffix {@link org.ddn.bencode.api.BEncodeFormat#END_SUFFIX} has not appeared
     * during the deserialization process
     * @return <code>true</code> if list entry is being parsed
     */
    boolean isListStarted();

    /**
     * method shows that {@link org.ddn.bencode.api.BEncodeFormat#DICTIONARY_PREFIX}
     * has appeared but end suffix {@link org.ddn.bencode.api.BEncodeFormat#END_SUFFIX} has not appeared
     * during the deserialization process
     * @return <code>true</code> if dictionary entry is being parsed
     */
    boolean isDictionaryStarted();

    /**
     * method shows that {@link org.ddn.bencode.api.BEncodeFormat#INTEGER_PREFIX}
     * has appeared but end suffix {@link org.ddn.bencode.api.BEncodeFormat#END_SUFFIX} has not appeared
     * during the deserialization process
     * @return <code>true</code> if integer entry is being parsed
     */
    boolean isIntegerStarted();

    /**
     * method shows that string entry is currently being read
     * @return <code>true</code> if string entry is being parsed
     */
    boolean isStringStarted();


    /**
     * method notifies the context that parsing of integer entry has started
     */
    void startInt();

    /**
     * method notifies the context that parsing of integer entry has ended
     */
    void endInt();

    /**
     * method notifies the context that parsing of list entry has started
     */
    void startList();

    /**
     * method notifies the context that parsing of list entry has ended
     */
    void endList();

    /**
     * method notifies the context that parsing of string entry has started
     */
    void startString();

    /**
     * method notifies the context that parsing of string entry has ended
     */
    void endString();

    /**
     * method notifies the context that parsing of dictionary entry has started
     */
    void startDictionary();

    /**
     * method notifies the context that parsing of dictionary entry has ended
     */
    void endDictionary();
}

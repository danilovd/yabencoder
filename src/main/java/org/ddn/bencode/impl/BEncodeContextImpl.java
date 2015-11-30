package org.ddn.bencode.impl;

import org.ddn.bencode.api.BEncodeContext;

import java.util.HashMap;
import java.util.Map;

import static org.ddn.bencode.api.BEncodeFormat.*;

/**
 * The class holds temporary data and process properties.
 * <b>Not thread safe</b>
 */
public class BEncodeContextImpl implements BEncodeContext {


    private final Map<String, Object> props = new HashMap<>();

    private boolean listStarted;
    private boolean dictionaryStarted;
    private boolean intStarted;
    private boolean strStarted;

    @Override
    public Object getProperty(String propertyName) {
        return props.get(propertyName);
    }

    @Override
    public void setProperty(String propertyName, Object value) {
        props.put(propertyName, value);
    }

    @Override
    public boolean isPrettyPrintingEnabled() {
        Object val =  props.get(PROPERTY_PRETTY_PRINTING_ENABLED);
        return val != null ? Boolean.parseBoolean(val.toString()) : false;
    }

    @Override
    public int getPrintingOffset() {
        Object val = props.get(PROPERTY_PRINTING_OFFSET);
        return val != null ? (Integer) val : 0;
    }

    @Override
    public void decrementPrintingOffset() {
        int offset = getPrintingOffset();
        if(offset <= 0){
            throw new IllegalStateException("Offset cannot be less than 0");
        }
        props.put(PROPERTY_PRINTING_OFFSET, --offset);
    }

    @Override
    public void incrementPrintingOffset() {
        int offset = getPrintingOffset();
        props.put(PROPERTY_PRINTING_OFFSET, ++offset);
    }

    @Override
    public boolean isListStarted() {
        return listStarted;
    }

    @Override
    public boolean isDictionaryStarted() {
        return dictionaryStarted;
    }

    @Override
    public boolean isIntegerStarted() {
        return intStarted;
    }

    @Override
    public boolean isStringStarted() {
        return strStarted;
    }

    @Override
    public void endDictionary() {
        dictionaryStarted = false;
    }

    @Override
    public void startInt() {
        intStarted = true;
    }

    @Override
    public void endInt() {
        intStarted = false;
    }

    @Override
    public void startList() {
        listStarted = true;
    }

    @Override
    public void endList() {
        listStarted = false;
    }

    @Override
    public void startString() {
        strStarted = true;
    }

    @Override
    public void endString() {
        strStarted = false;
    }

    @Override
    public void startDictionary() {
        dictionaryStarted = true;
    }
}

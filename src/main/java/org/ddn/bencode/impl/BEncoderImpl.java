package org.ddn.bencode.impl;

import org.ddn.bencode.api.BEncodeContext;
import org.ddn.bencode.api.BEncodeException;
import org.ddn.bencode.api.BEncoder;
import org.ddn.bencode.api.entries.Entry;


import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

/**
 * Created by Denis on 04.11.2015.
 */
public class BEncoderImpl implements BEncoder {

    public static final String BENCODER_DEFAULT_PROPERTIES = "/bencoder_default.properties";
    private static final Logger LOGGER = LoggerFactory.getLogger(BEncoderImpl.class);

    @NotNull
    private final Properties props;

    public BEncoderImpl(){
        this.props = new Properties();
        loadProperties();
    }

    public BEncoderImpl(Properties props){
        this.props = props;
    }

    private void loadProperties(){
        try {
            this.props.load(getClass().getResourceAsStream(BENCODER_DEFAULT_PROPERTIES));
        } catch (IOException e){
            LOGGER.warn("Failed to read properties for b-encoder", e);
        }
    }

    private void initializeContext(BEncodeContext ctx){
        for(Enumeration e = props.keys(); e.hasMoreElements(); ){
            String propName = (String) e.nextElement();
            ctx.setProperty(propName, props.get(propName));
        }
    }

    public void encode(OutputStream out, Collection<? extends Entry> entries) throws BEncodeException {

        BEncodeContext ctx = new BEncodeContextImpl();
        initializeContext(ctx);

        for (Entry e : entries) {
            encode(ctx, out, e);
        }
    }

    @Override
    public void encode(OutputStream out, Entry e) throws BEncodeException {
        BEncodeContext ctx = new BEncodeContextImpl();
        initializeContext(ctx);

        encode(ctx, out, e);
    }

    private void encode(BEncodeContext ctx, OutputStream out, Entry e) throws BEncodeException {

        LOGGER.debug("Start writing entry {} to stream", e);
        try {
            e.writeTo(ctx, out);
        } catch (IOException ex) {
            throw new BEncodeException("Failed to encode entry to output stream", ex);
        }
    }
}

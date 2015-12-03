# YaBEncoder
## Description
Yet another library for encoding and decoding data in b-encode format. The format is described [here](https://wiki.theory.org/BitTorrentSpecification#Bencoding)

## Quick start
### How to create entries
Entries are defined by interface **org.ddn.bencode.api.entries.Entry** and represent b-encode format entity.
Entries can be created using **org.ddn.bencode.api.entries.EntryFactory** interface.
```java
EntryFactory entryFactory = new EntryFactoryImpl()

StringEntry strEntry = entryFactory.createStringEntry("sampleStringEntry");
IntegerEntry intEntry = entryFactory.createIntegerEntry(42);
ListEntry listEntry = entryFactory.createListEntry(strEntry, intEntry);
    
// create map first
Map<StringEntry,Entry> map = new HashMap<>();
map.put(strEntry, intEntry);
DictionaryEntry dictionary = entryFactory.createDictionaryEntry(map);
```
or using **org.ddn.bencode.impl.entries.utils.CompositeEntryBuilder** for dictionaries and lists
```java
DictionaryEntry e = dictionary()
                .entry("name", "Arthur")
                .entry("number", 42L)
                .entry("picture", "")
                .entry("planets", list("Earth", "Somewhere else", "Old Earth"))
                .create();
```
### How to encode data
Encoding is done using **org.ddn.bencode.api.BEncoder** interface. It takes output stream and collection of entries to be encoded. 
```java
BEncoder bencoder = new BEncoderImpl();
bencoder.encode(outStream, entriesToEncode);
```
Entries will be written do output stream.


### How to decode data
Decoding is done using **org.ddn.bencode.api.BDecoder**. BDecoder reads data from input stream, creates entries from it, and puts them into a given collection.
```java
BDecoder bdecoder = new BDecoderImpl();
bdecoder.decode(inStream, decodedEntries);
```


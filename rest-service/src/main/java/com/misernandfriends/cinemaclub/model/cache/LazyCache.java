package com.misernandfriends.cinemaclub.model.cache;

import com.misernandfriends.cinemaclub.model.dictionary.DictionaryDTO;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryItemDTO;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class LazyCache {

    public final static HashMap<CacheValue, DictionaryDTO> dictionaries = new HashMap<>();
    public static final String TRUE = "1";

    public static void add(CacheValue cacheValue, DictionaryDTO dictionary) {
        if (dictionaries.containsKey(cacheValue)) {
            return;
        }
        dictionaries.put(cacheValue, dictionary);
    }

    public static void clear() {
        dictionaries.clear();
    }

    public static DictionaryDTO get(CacheValue cacheValue) {
        if (!dictionaries.containsKey(cacheValue)) {
            throw new NoSuchElementException();
        }

        return dictionaries.get(cacheValue);
    }

    public static String getValue(EnumCache cacheRegion) {
        return getItem(cacheRegion).getValue();
    }

    public static DictionaryItemDTO getItem(EnumCache cacheItem) {
        CacheValue region = CacheValue.getRegion(cacheItem);
        return get(region).get(cacheItem);
    }
}

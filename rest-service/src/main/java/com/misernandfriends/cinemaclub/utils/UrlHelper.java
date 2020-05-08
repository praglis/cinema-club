package com.misernandfriends.cinemaclub.utils;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.cache.EnumCache;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;

public class UrlHelper {

    private String result;

    private CacheValue cacheRegion;

    public UrlHelper(EnumCache cacheValue) {
        cacheRegion = CacheValue.getRegion(cacheValue);
        result = LazyCache.getValue(cacheValue);
    }

    public UrlHelper(String url) {
        result = url;
    }

    public UrlHelper setQuery(String query) {
        replace("{{QUERY}}", query);
        return this;
    }

    public void replace(String target, String value) {
        this.result = result.replace(target, value);
    }

    public String build() {
        completeQuery();
        return result;
    }

    private void completeQuery() {
        int index = 0;
        do {
            if (index > 0) {
                index += 2;
                String valueToReplace = result.substring(index, result.indexOf("}}"));
                String value = getValueFor(valueToReplace);
                result = result.replace("{{" + valueToReplace + "}}", value);
            }
            index = result.indexOf("{{");
        } while (index != -1);
    }

    private String getValueFor(String valueToReplace) {
        String[] parts = valueToReplace.split("\\.");
        if (parts.length == 1) {
            return cacheRegion.getValue(valueToReplace);
        }
        CacheValue cacheRegion = CacheValue.valueOf(parts[0]);
        return cacheRegion.getValue(parts[1]);
    }

    public UrlHelper addQuery(String name, String value) {
        if (!result.contains("?")) {
            result += "?";
        } else if (!result.endsWith("&")) {
            result += "&";
        }
        result += String.format("%s=%s", name, value);
        return this;
    }
}

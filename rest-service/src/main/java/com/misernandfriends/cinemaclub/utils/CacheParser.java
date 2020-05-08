package com.misernandfriends.cinemaclub.utils;

import com.misernandfriends.cinemaclub.model.cache.InitializableCache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheParser {

    private final Map<String, String> values = new HashMap<>();

    private boolean needInitialization;

    private Class<? extends InitializableCache> initializationClass;

    public CacheParser(String value) {
        convertValue(value);
    }

    private void convertValue(String value) {
        if (value.length() < 5) {
            return;
        }

        String content = value.substring(2, value.length() - 2);
        String[] parts = content.split(";");
        String[] masterPart = parts[0].split("=");
        if (masterPart[0].equals("CACHE")) {
            needInitialization = true;
        } else {
            return;
        }

        if (masterPart.length == 1) {
            throw new RuntimeException(formatException(value, "Master cache value doesnt have assigned class!"));
        }

        Class<?> potentialClass = null;
        try {
            potentialClass = Class.forName(masterPart[1]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (potentialClass == null) {
            throw new RuntimeException(formatException(value, String.format("Can't find class %s", masterPart[1])));
        }

        if (Arrays.stream(potentialClass.getInterfaces()).noneMatch(aClass -> aClass == InitializableCache.class)) {
            throw new RuntimeException(formatException(value, String.format("Class %s should implements %s", potentialClass, InitializableCache.class)));
        }

        initializationClass = (Class<? extends InitializableCache>) potentialClass;
        for (int i = 1; i < parts.length; i++) {
            String[] valuePart = parts[i].split("=");
            if (valuePart.length == 1) {
                throw new RuntimeException(formatException(value, String.format("%d has wrong format (KEY=VALUE)", i + 1)));
            }
            values.put(valuePart[0], valuePart[1]);
        }
    }

    private String formatException(String value, String message) {
        return String.format("%s - %s", value, message);
    }

    public boolean needInitialization() {
        return needInitialization;
    }

    public Class<? extends InitializableCache> getInitializationClass() {
        return initializationClass;
    }

    public Map<String, String> getValues() {
        return values;
    }
}

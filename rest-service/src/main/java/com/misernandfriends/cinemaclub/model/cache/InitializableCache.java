package com.misernandfriends.cinemaclub.model.cache;

import java.util.Map;

public interface InitializableCache {
    void initialize(Map<String, String> value);
}

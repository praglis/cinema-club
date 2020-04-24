package com.misernandfriends.cinemaclub.model.cache;

public enum CacheValue {

    API_URLS(_API_URLS.class),
    EMAIL_CONFIGURATION(_EMAIL_CONFIGURATION.class),
    USER_REVIEW(_USER_REVIEW.class),
    QUERY_PARAM(_QUERY_PARAM.class),
    GENRES(_GENRES.class);


    private Class<? extends Enum> value;

    CacheValue(Class<? extends Enum> value) {
        this.value = value;
    }

    public String getValue(String enumName) {
         return LazyCache.getValue((EnumCache) Enum.valueOf(value, enumName));
    }

    public static CacheValue getRegion(EnumCache cacheValue) {
        return CacheValue.valueOf(cacheValue.getClass().getSimpleName().substring(1));
    }

    /**
     *  API configuration
     */
    public enum _API_URLS implements EnumCache{
        /** Movies  */
        MOVIES_API_URL,
        MOVIES_API_URL_QUERY,
        MOVIES_API_KEY,
        MOVIES_API_LANGUAGE,
        MOVIES_API_URL_TOP_RATED,
        MOVIES_API_URL_MOST_POPULAR,
        MOVIES_API_URL_DISCOVER,
        MOVIES_CREW_API_URL,
        MOVIES_DISCOVER_API_URL,

        MOVIES_API_GENRES_URL,
        /** New York Times Reviews Configuration */
        NYT_API_URL,
        NYT_API_URL_QUERY,
        NYT_API_KEY,

        /** Guardian Reviews Configuration */
        GUARDIAN_API_URL,
        GUARDIAN_API_URL_QUERY,
        GUARDIAN_API_KEY
    }

    /**
     * Email configuration
     */
    public enum _EMAIL_CONFIGURATION implements EnumCache {
        /** Account Activation  */
        ACTIVATE_TITLE,
        ACTIVATE_MESSAGE,

        /** Password reseting */
        PWD_RESET_TITLE,
        PWD_RESET_MESSAGE,
        ;
    }

    public enum _USER_REVIEW implements EnumCache {
        CAN_LIKE_SELF_COMMENT
    }

    public enum _QUERY_PARAM implements EnumCache {
        WITH_GENRES,
        WITH_CAST,
        WITH_CREW
    }

    public enum _GENRES implements EnumCache {
        GENRES
    }
}

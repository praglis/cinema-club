package com.misernandfriends.cinemaclub.config;

import com.misernandfriends.cinemaclub.serviceInterface.config.DictionaryServiceLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitializationBean implements InitializingBean {

    private final DictionaryServiceLocal dictionaryServiceLocal;

    public InitializationBean(DictionaryServiceLocal dictionaryServiceLocal) {
        this.dictionaryServiceLocal = dictionaryServiceLocal;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Loading dictionaries");
        dictionaryServiceLocal.reloadDictionaries();
        log.info("Dictionaries loaded");
    }
}

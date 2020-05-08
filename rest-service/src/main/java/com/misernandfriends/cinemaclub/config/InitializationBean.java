package com.misernandfriends.cinemaclub.config;

import com.misernandfriends.cinemaclub.serviceInterface.config.DictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitializationBean implements InitializingBean {

    private final DictionaryService dictionaryService;

    public InitializationBean(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Loading dictionaries");
        dictionaryService.reloadDictionaries();
        log.info("Dictionaries loaded");
    }
}

package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.serviceInterface.DictionaryServiceLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitializationBean implements InitializingBean {

    @Autowired
    private DictionaryServiceLocal dictionaryServiceLocal;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Loading dictionaries");
        dictionaryServiceLocal.reloadDictionaries();
        log.info("Dictionaries loaded");
    }
}

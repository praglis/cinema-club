package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.model.cache.CacheValue;
import com.misernandfriends.cinemaclub.model.cache.LazyCache;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryDTO;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryItemDTO;
import com.misernandfriends.cinemaclub.repository.dictionary.DictionaryRepository;
import com.misernandfriends.cinemaclub.serviceInterface.DictionaryServiceLocal;
import com.misernandfriends.cinemaclub.utils.CacheParser;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryServiceLocal {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Override
    @Transactional
    public void reloadDictionaries() {
        LazyCache.clear();
        List<DictionaryDTO> dictionaryDTOList = dictionaryRepository.getAll();
        for (DictionaryDTO dictionary : dictionaryDTOList) {
            Hibernate.initialize(dictionary.getItems());
            LazyCache.add(CacheValue.valueOf(dictionary.getDomain()), dictionary);
            for (DictionaryItemDTO item : dictionary.getItems()) {
                initializeDictionaryItem(item);
            }
            log.info("Loading value for domain {}", dictionary.getDomain());
        }
    }

    private void initializeDictionaryItem(DictionaryItemDTO item) {
        String value = item.getValue();
        CacheParser parser = new CacheParser(value);
        if (parser.needInitialization()) {
            try {
                parser.getInitializationClass().newInstance().initialize(parser.getValues());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

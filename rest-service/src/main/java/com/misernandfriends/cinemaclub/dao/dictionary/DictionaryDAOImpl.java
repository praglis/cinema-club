package com.misernandfriends.cinemaclub.dao.dictionary;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryDTO;
import com.misernandfriends.cinemaclub.repository.dictionary.DictionaryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DictionaryDAOImpl extends AbstractDAOImpl<DictionaryDTO> implements DictionaryRepository {

    @Override
    protected Class<DictionaryDTO> getEntityClazz() {
        return DictionaryDTO.class;
    }
}

package com.misernandfriends.cinemaclub.repository.dictionary;

import com.misernandfriends.cinemaclub.model.dictionary.DictionaryDTO;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryItemDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;

public interface DictionaryRepository extends AbstractRepository<DictionaryDTO> {

    public DictionaryDTO getByDomain(String domain);

    public List<DictionaryItemDTO> getDomainItems(String domain);

}

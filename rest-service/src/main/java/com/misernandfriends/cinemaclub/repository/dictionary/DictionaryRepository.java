package com.misernandfriends.cinemaclub.repository.dictionary;

import com.misernandfriends.cinemaclub.model.dictionary.DictionaryDTO;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryItemDTO;
import com.misernandfriends.cinemaclub.repository.AbstractRepository;

import java.util.List;
import java.util.Optional;

public interface DictionaryRepository extends AbstractRepository<DictionaryDTO> {

    Optional<DictionaryDTO> getByDomain(String domain);

    List<DictionaryItemDTO> getDomainItems(String domain);

}

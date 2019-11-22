package com.misernandfriends.cinemaclub.DAO.dictionary;

import com.misernandfriends.cinemaclub.DAO.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryDTO;
import com.misernandfriends.cinemaclub.model.dictionary.DictionaryItemDTO;
import com.misernandfriends.cinemaclub.repository.dictionary.DictionaryRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class DictionaryDAOImpl extends AbstractDAOImpl<DictionaryDTO> implements DictionaryRepository {

    @Override
    protected Class<DictionaryDTO> getEntityClazz() {
        return DictionaryDTO.class;
    }

    @Override
    public Optional<DictionaryDTO> getByDomain(String domain) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.domain = :domain";
        TypedQuery<DictionaryDTO> query = em.createQuery(queryTxt, DictionaryDTO.class)
                .setParameter("domain", domain);

        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<DictionaryItemDTO> getDomainItems(String domain) {
        String queryTxt = "SELECT data FROM DictionaryItemDTO data " +
                "WHERE data.dictionary.domain = :domain AND data.infoRD IS NULL";
        TypedQuery<DictionaryItemDTO> query = em.createQuery(queryTxt, DictionaryItemDTO.class)
                .setParameter("domain", domain);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}

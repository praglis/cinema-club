package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.RoleDTO;
import com.misernandfriends.cinemaclub.repository.user.RoleRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class RoleDAOImpl extends AbstractDAOImpl<RoleDTO> implements RoleRepository {

    @Override
    protected Class<RoleDTO> getEntityClazz() {
        return RoleDTO.class;
    }

    @Override
    public Optional<RoleDTO> findByName(String name) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.name = :name";
        TypedQuery<RoleDTO> query = em.createQuery(queryTxt, RoleDTO.class)
                .setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

}

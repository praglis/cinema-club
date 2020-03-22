package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.UserDTO;
import com.misernandfriends.cinemaclub.repository.user.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends AbstractDAOImpl<UserDTO> implements UserRepository {

    @Override
    protected Class<UserDTO> getEntityClazz() {
        return UserDTO.class;
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.username = :username";
        TypedQuery<UserDTO> query = em.createQuery(queryTxt, UserDTO.class)
                .setParameter("username", username);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }


    public Optional<UserDTO> findByEmail(String email) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.email = :email";
        TypedQuery<UserDTO> query = em.createQuery(queryTxt, UserDTO.class)
                .setParameter("email", email);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserDTO> findAll() {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data ";
        TypedQuery<UserDTO> query = em.createQuery(queryTxt, UserDTO.class);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.EMPTY_LIST;
        }
    }
}

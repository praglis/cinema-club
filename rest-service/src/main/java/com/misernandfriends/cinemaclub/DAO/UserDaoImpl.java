package com.misernandfriends.cinemaclub.DAO;

import com.misernandfriends.cinemaclub.model.UserDTO;
import com.misernandfriends.cinemaclub.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class UserDaoImpl extends AbstractDAOImpl<UserDTO> implements UserRepository {

    @Override
    protected Class<UserDTO> getEntityClazz() {
        return UserDTO.class;
    }

    @Override
    public UserDTO findByUsername(String username) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.username = :username";
        TypedQuery<UserDTO> query = em.createQuery(queryTxt, UserDTO.class)
                .setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

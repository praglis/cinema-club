package com.misernandfriends.cinemaclub.DAO;

import com.misernandfriends.cinemaclub.model.User;
import com.misernandfriends.cinemaclub.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

@Repository
public class UserDaoImpl extends AbstractDAOImpl<User> implements UserRepository {

    @Override
    protected Class<User> getEntityClazz() {
        return User.class;
    }

    @Override
    public User findByUsername(String username) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.username = :username";
        TypedQuery<User> query = em.createQuery(queryTxt, User.class)
                .setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

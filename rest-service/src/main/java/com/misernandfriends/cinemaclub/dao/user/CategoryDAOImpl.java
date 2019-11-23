package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.CategoryDTO;
import com.misernandfriends.cinemaclub.repository.user.CategoryRepository;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CategoryDAOImpl extends AbstractDAOImpl<CategoryDTO> implements CategoryRepository {

    @Override
    protected Class<CategoryDTO> getEntityClazz() {
        return CategoryDTO.class;
    }

    @Override
    public List<CategoryDTO> getByUserId(Long userId) {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data " +
                "WHERE data.user.id = :userId OR data.user IS NULL";
        TypedQuery<CategoryDTO> query = em.createQuery(queryTxt, CategoryDTO.class)
                .setParameter("userId", userId);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Lists.emptyList();
        }
    }
}

package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.CategoryDTO;
import com.misernandfriends.cinemaclub.repository.user.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAOImpl extends AbstractDAOImpl<CategoryDTO> implements CategoryRepository {
    @Override
    protected Class<CategoryDTO> getEntityClazz() {
        return CategoryDTO.class;
    }
}

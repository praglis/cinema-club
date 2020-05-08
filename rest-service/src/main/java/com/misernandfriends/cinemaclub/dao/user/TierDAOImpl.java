package com.misernandfriends.cinemaclub.dao.user;

import com.misernandfriends.cinemaclub.dao.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.TierDTO;
import com.misernandfriends.cinemaclub.repository.user.TierRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TierDAOImpl extends AbstractDAOImpl<TierDTO> implements TierRepository {
    @Override
    protected Class<TierDTO> getEntityClazz() {
        return TierDTO.class;
    }
}

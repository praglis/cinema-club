package com.misernandfriends.cinemaclub.DAO.user;

import com.misernandfriends.cinemaclub.DAO.AbstractDAOImpl;
import com.misernandfriends.cinemaclub.model.user.TierDTO;
import com.misernandfriends.cinemaclub.repository.user.TireRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TireDAOImpl extends AbstractDAOImpl<TierDTO> implements TireRepository {
    @Override
    protected Class<TierDTO> getEntityClazz() {
        return TierDTO.class;
    }
}

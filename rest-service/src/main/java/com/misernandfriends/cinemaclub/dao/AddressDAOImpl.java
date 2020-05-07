package com.misernandfriends.cinemaclub.dao;

import com.misernandfriends.cinemaclub.model.AddressDTO;
import com.misernandfriends.cinemaclub.repository.AddressRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDAOImpl extends AbstractDAOImpl<AddressDTO> implements AddressRepository {
    @Override
    protected Class<AddressDTO> getEntityClazz() {
        return AddressDTO.class;
    }
}

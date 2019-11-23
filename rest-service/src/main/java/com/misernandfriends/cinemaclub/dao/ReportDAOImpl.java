package com.misernandfriends.cinemaclub.dao;

import com.misernandfriends.cinemaclub.model.ReportDTO;
import com.misernandfriends.cinemaclub.repository.ReportRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

@Repository
public class ReportDAOImpl extends AbstractDAOImpl<ReportDTO> implements ReportRepository {
    @Override
    protected Class<ReportDTO> getEntityClazz() {
        return ReportDTO.class;
    }

    @Override
    public List<ReportDTO> getUnassignedReports() {
        String queryTxt = "SELECT data FROM " + getEntityName() + " data WHERE " +
                "data.assignedUser IS NULL AND data.infoRD IS NULL";
        TypedQuery<ReportDTO> query = em.createQuery(queryTxt, ReportDTO.class);
        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}

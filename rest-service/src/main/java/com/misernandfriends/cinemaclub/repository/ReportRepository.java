package com.misernandfriends.cinemaclub.repository;

import com.misernandfriends.cinemaclub.model.ReportDTO;

import java.util.List;

public interface ReportRepository extends AbstractRepository<ReportDTO> {

    List<ReportDTO> getUnassignedReports();

}

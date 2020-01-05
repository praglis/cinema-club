package com.misernandfriends.cinemaclub.service;

import com.misernandfriends.cinemaclub.fetcher.HeliosPremiersFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PremiersFetcherService {

    @Autowired
    private HeliosPremiersFetcher heliosPremiersFetcher;

    @Scheduled(fixedRate = 14 * 3600 * 1000, initialDelay = 0)
    public void fetchHeliosPremiers() {
        heliosPremiersFetcher.fetch();
    }

}

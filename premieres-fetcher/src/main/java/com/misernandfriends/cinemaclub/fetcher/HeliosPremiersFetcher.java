package com.misernandfriends.cinemaclub.fetcher;

import com.misernandfriends.cinemaclub.entity.MovieHelper;
import com.misernandfriends.cinemaclub.model.cinema.CinemaDTO;
import com.misernandfriends.cinemaclub.model.movie.MovieDTO;
import com.misernandfriends.cinemaclub.model.movie.PremiereDTO;
import com.misernandfriends.cinemaclub.repository.cinema.CinemaRepository;
import com.misernandfriends.cinemaclub.repository.movie.PremiereRepository;
import com.misernandfriends.cinemaclub.serviceInterface.movie.MovieServiceLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.list.SetUniqueList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

@Component
@Slf4j
public class HeliosPremiersFetcher {

    private final static String HELIOS_URL = "https://www.helios.pl";
    private final static String HELIOS_BIALYSTOK_ALFA = "https://www.helios.pl/26,Bialystok/StronaGlowna/";
    private final static String HELIOS_BIALYSTOK_BIALA = "https://www.helios.pl/6,Bialystok/StronaGlowna/";
    private final static String HELIOS_BIALYSTOK_JURAWIECKA = "https://www.helios.pl/46,Bialystok/StronaGlowna/";

    private final static String CINEMA_BIALYSTOK_ALFA = "Białystok Helios Alfa";
    private final static String CINEMA_BIALYSTOK_BIALA = "Białystok Helios Biała";
    private final static String CINEMA_BIALYSTOK_JURAWIECKA = "Białystok Helios Jurawiecka";


    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private PremiereRepository premiereRepository;

    @Autowired
    private MovieServiceLocal movieService;

    public void fetch() {
        fetch(HELIOS_BIALYSTOK_ALFA, cinemaRepository.getByName(CINEMA_BIALYSTOK_ALFA));
        fetch(HELIOS_BIALYSTOK_BIALA, cinemaRepository.getByName(CINEMA_BIALYSTOK_BIALA));
        fetch(HELIOS_BIALYSTOK_JURAWIECKA, cinemaRepository.getByName(CINEMA_BIALYSTOK_JURAWIECKA));
    }

    public void fetch(String url, Optional<CinemaDTO> cinemaOptional) {
        if (!cinemaOptional.isPresent()) {
            log.error("Cinema not found for url: {}", url);
            return;
        }
        CinemaDTO cinema = cinemaOptional.get();
        log.info("Fetching premieres for cinema: {}", cinema.getName());
        Document page;
        try {
            page = Jsoup.connect(url).get();
        } catch (IOException e) {
            log.error("Error while processing cinema: {} with url: {}", cinema.getName(), url);
            e.printStackTrace();
            return;
        }
        Elements movies = page.getElementsByClass("movie-link");
        Elements pages = page.getElementsByClass("day-link");
        if (pages.size() == 0) {
            pages = page.getElementsByClass("slide-link");
        }
        String dayMonth = getDayMonth(page);
        if (dayMonth == null) {
            log.warn("Can't get date for {}, skipping", url);
            return;
        }

        SetUniqueList<MovieHelper> uniq = SetUniqueList.setUniqueList(new ArrayList<>());
        uniq.addAll(getMoviesFromElements(movies, dayMonth));

        for (Element movie : pages) {
            String pageUrl = HELIOS_URL + movie.attributes().get("href");

            try {
                uniq.addAll(getPremieresFromUrl(pageUrl));
            } catch (IOException e) {
                log.error("Error while parsing url: {}", pageUrl);
                e.printStackTrace();
            }
        }
        addPremieresToCinema(uniq, cinema);
        log.info("Premiers fetch for {} ended", cinema.getName());
    }

    private String getDayMonth(Document page) {
        String dayMonth = page.getElementsByClass("view-emphasis").html().trim();
        if (dayMonth.length() == 0) {
            try {
                dayMonth = page.getElementsByClass("page-section set-repertoire cnt-repertoire-home").get(0)
                        .getElementsByClass("section-emphasis").html().trim();
            } catch (Exception e) {
                return null;
            }
        }
        if (dayMonth.length() == 0) {
            return null;
        }
        dayMonth = dayMonth.substring(dayMonth.length() - 5).replace(".", "/");
        if (dayMonth.length() != 5) {
            return null;
        }
        return dayMonth;
    }

    private void addPremieresToCinema(SetUniqueList<MovieHelper> uniq, CinemaDTO cinema) {
        int savedPremiersInc = 0;
        for (MovieHelper s : uniq) {
            PremiereDTO premiere = new PremiereDTO();
            premiere.setCinema(cinema);
            premiere.setDate(s.getPremiereDate());
            MovieDTO movie = movieService.getMovieData(s.getMovieTitle());
            if (movie == null) {
                log.warn("Can't find movie by title: {}", s.getMovieTitle());
                continue;
            }
            premiere.setMovie(movie);
            if (premiereRepository.isPremierePresent(premiere)) {
                continue;
            }
            premiereRepository.create(premiere);
            savedPremiersInc++;
        }
        log.info("Fetched {} new premiers for cinema: {}", savedPremiersInc, cinema.getName());
    }

    private SetUniqueList<MovieHelper> getPremieresFromUrl(String url) throws IOException {
        Document page = Jsoup.connect(url).get();
        Elements movies = page.getElementsByClass("movie-link");
        String dayMonth = getDayMonth(page);
        if (dayMonth == null) {
            return SetUniqueList.setUniqueList(new ArrayList<>());
        }
        if (dayMonth.length() == 0) {
            return SetUniqueList.setUniqueList(new ArrayList<>());
        }

        return getMoviesFromElements(movies, dayMonth);
    }

    private SetUniqueList<MovieHelper> getMoviesFromElements(Elements elements, String dayMonth) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String year = "/" + Calendar.getInstance().get(Calendar.YEAR);

        SetUniqueList<MovieHelper> moviesList = SetUniqueList.setUniqueList(new ArrayList<>());
        for (Element movie : elements) {
            try {
                moviesList.add(new MovieHelper(
                        movie.childNodes().get(0).outerHtml().trim(),
                        sdf.parse(dayMonth + year)
                ));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return moviesList;
    }
}

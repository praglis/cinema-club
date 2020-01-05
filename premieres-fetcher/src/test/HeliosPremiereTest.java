import com.misernandfriends.cinemaclub.entity.MovieHelper;
import org.assertj.core.util.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

@RunWith(SpringRunner.class)
public class HeliosPremiereTest {

    @Test
    public void getPremiere() throws IOException {
        Document page = Jsoup.connect("https://www.helios.pl/26,Bialystok/Repertuar").get();
        Elements movies = page.getElementsByClass("movie-link");
        Elements pages = page.getElementsByClass("day-link");

        ArrayList<MovieHelper> uniq = new ArrayList();

        Assert.assertNotEquals(0, pages.size());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        String year = "/" + Calendar.getInstance().get(Calendar.YEAR);

        String dayMonth = page.getElementsByClass("view-emphasis").html().trim();
        dayMonth = dayMonth.substring(dayMonth.length() - 4).replace(".", "/");

        for (Element movie : movies) {
            try {
                uniq.add(new MovieHelper(
                        movie.childNodes().get(0).outerHtml().trim(),
                        sdf.parse(dayMonth + year)
                ));
            } catch (ParseException e) {
                System.out.println("Cant parse " + e.getMessage());
                e.printStackTrace();
            }
        }

        for (Element movie : pages) {
            String url = "https://www.helios.pl" + movie.attributes().get("href");
            uniq.addAll(getListFor(url));
        }

        for (MovieHelper movie : uniq) {
            System.out.println(movie);
        }
    }

    private Collection<? extends MovieHelper> getListFor(String url) throws IOException {
        List<MovieHelper> moviesList = new ArrayList<>();
        Document page = Jsoup.connect(url).get();
        Elements movies = page.getElementsByClass("movie-link");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        String year = "/" + Calendar.getInstance().get(Calendar.YEAR);

        String dayMonth = page.getElementsByClass("view-emphasis").html().trim();
        if(dayMonth.length() == 0) {
            // cant find date so ignore
            return Lists.emptyList();
        }
        dayMonth = dayMonth.substring(dayMonth.length() - 4).replace(".", "/");

        for (Element movie : movies) {
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

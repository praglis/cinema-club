package com.misernandfriends.cinemaclub.model.cache;

import com.misernandfriends.cinemaclub.utils.UrlHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;

public class GenreCache implements InitializableCache {

    private static final String PREFIX = "GET_FROM_URL";

    private static HashMap<Integer, String> genresMap;

    public void initialize(Map<String, String> map) {
        if (!map.containsKey(PREFIX)) {
            throw new RuntimeException(String.format("Cant initialize genre cache! %s not found in values", PREFIX));
        }
        String result = new RestTemplate().getForObject(new UrlHelper(map.get(PREFIX)).build(), String.class);
        JSONArray genres = new JSONObject(result).getJSONArray("genres");
        genresMap = new HashMap<>();
        for (int i = 0; i < genres.length(); i++) {
            JSONObject genre = genres.getJSONObject(i);
            genresMap.put(genre.getInt("id"), genre.getString("name"));
        }
    }

    public static String get(Integer id) {
        return genresMap.get(id);
    }

    public static Integer getId(String name) {
        for (Map.Entry<Integer, String> integerStringEntry : genresMap.entrySet()) {
            if (integerStringEntry.getValue().equals(name)) {
                return integerStringEntry.getKey();
            }
        }
        throw new NoResultException(String.format("Genre with name %s does not exist", name));
    }

}

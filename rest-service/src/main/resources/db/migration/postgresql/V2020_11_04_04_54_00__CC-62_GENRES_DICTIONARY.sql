INSERT INTO SYS_DICTIONARIES(dic_id, dic_domain) VALUES (nextval('seq_sys_dic_id'), 'GENRES');


INSERT INTO SYS_DIC_ITEMS(dit_id, dit_info_cd, dit_name, dit_value, dit_dic_id) VALUES (nextval('seq_sys_dit_id'), CURRENT_TIMESTAMP, 'GENRES', '{{CACHE=com.misernandfriends.cinemaclub.model.cache.GenreCache;GET_FROM_URL=https://api.themoviedb.org/3/genre/movie/list?{{API_URLS.MOVIES_API_KEY}}&{{API_URLS.MOVIES_API_LANGUAGE}}}}', (SELECT DIC_ID FROM sys_dictionaries WHERE dic_domain = 'QUERY_PARAM'));

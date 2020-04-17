UPDATE sys_dic_items set dit_value = 'https://api.themoviedb.org/3/search/movie?query={{QUERY}}&{{MOVIES_API_KEY}}&{{MOVIES_API_LANGUAGE}}' where dit_name = 'MOVIES_API_URL_QUERY';

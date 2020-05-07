ALTER TABLE mov_movies
    ADD COLUMN mov_avg_rating NUMERIC(5, 2) default 0;
ALTER TABLE mov_movies
    ADD COLUMN mov_count_rating NUMERIC(10) default 0;
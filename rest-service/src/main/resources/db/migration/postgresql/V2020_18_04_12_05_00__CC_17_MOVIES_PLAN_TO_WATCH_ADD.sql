CREATE TABLE USR_PLAN_TO_WATCH (
    PTW_ID int primary key,
    PTW_USR_ID int not null,
    PTW_MOV_ID int not null,
    PTW_INFO_CD date not null,
    PTW_INFO_RD date,
    FOREIGN KEY (PTW_USR_ID) REFERENCES USR_USERS (USR_ID),
    FOREIGN KEY (PTW_MOV_ID) REFERENCES MOV_MOVIES (MOV_ID)
);

CREATE SEQUENCE seq_usr_ptw_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

DROP TABLE usr_fav_movies;
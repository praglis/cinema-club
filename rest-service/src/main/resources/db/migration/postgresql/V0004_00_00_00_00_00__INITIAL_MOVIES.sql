CREATE TABLE MOV_ACTORS (
    ACT_ID int primary key,
    ACT_INFO_CD timestamp not null,
    ACT_INFO_RD timestamp not null,
    ACT_API_URL varchar(255) not null
);

CREATE TABLE MOV_MOVIES (
    MOV_ID int primary key,
    MOV_INFO_CD timestamp not null,
    MOV_INFO_RD timestamp not null,
    MOV_API_URL varchar(255) not null,
    MOV_TITLE varchar(255) not null
);

CREATE TABLE MOV_PREMIERES (
    PRM_ID int primary key,
    PRM_CINEMA_ID int not null,
    PRM_MOVIE_ID int not null,
    PRM_DATE timestamp not null,
    FOREIGN KEY (PRM_CINEMA_ID) REFERENCES MOV_CINEMAS(CIN_ID),
    FOREIGN KEY (PRM_MOVIE_ID) REFERENCES MOV_MOVIES(MOV_ID)
);

CREATE TABLE USR_MOV_RATING (
    URT_ID int primary key,
    URT_USR_ID int not null,
    URT_MOV_ID int not null,
    URT_RATE int not null,
    FOREIGN KEY (URT_USR_ID) REFERENCES USR_USERS(USR_ID),
    FOREIGN KEY (URT_MOV_ID) REFERENCES MOV_MOVIES(MOV_ID)
);

CREATE SEQUENCE seq_mov_act_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

CREATE SEQUENCE seq_mov_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

CREATE SEQUENCE seq_mov_prm_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

CREATE SEQUENCE seq_usr_urt_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

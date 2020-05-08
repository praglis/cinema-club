CREATE TABLE USR_RECOMMEND (
    URM_ID int primary key,
    URM_USR_ID int not null references USR_USERS(USR_ID),
    URM_TYPE varchar(1) not null,
    URM_VALUE varchar(200) not null,
    URM_FIT_LEVEL numeric(3,2) default 0 not null
);

CREATE SEQUENCE seq_usr_urm_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;
CREATE TABLE USR_MOV_SIMILAR (
    MUS_ID int primary key,
    MUS_INFO_CD timestamp not null default current_timestamp,
    MUS_USR_ID int not null,
    MUS_MOV_ID int not null,
    FOREIGN KEY (MUS_USR_ID) REFERENCES USR_USERS(USR_ID),
    FOREIGN KEY (MUS_MOV_ID) REFERENCES MOV_MOVIES(MOV_ID)
);

CREATE SEQUENCE seq_mus_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;
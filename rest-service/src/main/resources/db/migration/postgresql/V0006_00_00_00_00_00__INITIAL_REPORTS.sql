CREATE TABLE USR_REPORTS (
    REP_ID int primary key,
    REP_INFO_CD timestamp not null,
    REP_INFO_CU int not null,
    REP_INFO_RD timestamp,
    REP_INFO_RU int not null,
    REP_STATUS varchar(1),
    REP_DESCRIPTION text,
    REP_URL varchar(100) not null,
    REP_TYPE varchar(1) not null,
    REP_ASSIGNED_USER_ID int not null,
    REP_ASSIGNED_MSG varchar(45),
    FOREIGN KEY (REP_INFO_CU) REFERENCES USR_USERS(USR_ID),
    FOREIGN KEY (REP_INFO_RU) REFERENCES USR_USERS(USR_ID),
    FOREIGN KEY (REP_ASSIGNED_USER_ID) REFERENCES USR_USERS(USR_ID)
);

CREATE SEQUENCE seq_usr_rep_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

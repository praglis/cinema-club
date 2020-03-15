CREATE TABLE USR_TIERS (
    TIR_ID int primary key,
    TIR_MIN_POINTS int not null,
    TIR_NAME varchar(45) not null,
    TIR_DESCRIPTION varchar(45)
);

CREATE TABLE USR_USERS (
    USR_ID int primary key,
    USR_USERNAME varchar(45) unique not null,
    USR_PASSWORD text,
    USR_NAME varchar(45),
    USR_SURNAME varchar(45),
    USR_ENROLMENT_DATE timestamp not null,
    USR_BIRTHDATE timestamp,
    USR_EMAIL varchar(150) unique not null,
    USR_EMAIL_CONFIRMED boolean,
    USR_EMAIL_NEW varchar(150),
    USR_PHONE_NO varchar(45),
    USR_TYPE varchar(1),
    USR_ACCOUNT_STATUS varchar(1) not null,
    USR_POINTS int,
    USR_TIER_ID int,
    FOREIGN KEY (USR_TIER_ID) REFERENCES USR_TIERS(TIR_ID)
);

CREATE TABLE USR_ROLES (
    ROL_ID int primary key,
    ROL_NAME varchar(45),
    ROL_USR_ID int,
    FOREIGN KEY (ROL_USR_ID) REFERENCES USR_USERS(USR_ID)
);

CREATE TABLE USR_CATEGORIES (
    CAT_ID int primary key,
    CAT_INFO_CD timestamp not null,
    CAT_INFO_RD timestamp,
    CAT_NAME varchar(50) not null,
    CAT_DESCRIPTION varchar(200)
);

CREATE TABLE USR_VERIFICATION_TOKENS (
    VRT_ID int primary key,
    VRT_INFO_CD timestamp not null,
    VRT_INFO_RD timestamp,
    VRT_TOKEN varchar(255),
    VRT_TOKEN_EXP timestamp,
    VRT_TOKEN_TYPE varchar(1) not null,
    VRT_USR_ID int,
    FOREIGN KEY (VRT_USR_ID) REFERENCES USR_USERS(USR_ID)
);

CREATE TABLE USR_FAV_MOVIES (
    FVM_USR_ID int,
    FVM_MOV_ID int,
    FVM_CAT_ID int,
    PRIMARY KEY (FVM_USR_ID, FVM_MOV_ID, FVM_CAT_ID)
);

CREATE SEQUENCE seq_usr_user_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

CREATE SEQUENCE seq_usr_tier_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

CREATE SEQUENCE seq_usr_role_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

CREATE SEQUENCE seq_usr_cat_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;

CREATE SEQUENCE seq_usr_vrt_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;


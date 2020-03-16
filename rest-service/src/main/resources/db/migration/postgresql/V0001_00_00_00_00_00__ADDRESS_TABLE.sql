CREATE TABLE SYS_ADDRESSES (
    ADR_ID int primary key,
    ADR_COUNTRY varchar(45),
    ADR_STATE varchar(45),
    ADR_CITY varchar(45),
    ADR_STREET varchar(45),
    ADR_HOUSE_NUMBER varchar(45)
);

ALTER TABLE USR_USERS
ADD COLUMN USR_ADR_ID int;

CREATE SEQUENCE seq_sys_adr_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;
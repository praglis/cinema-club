CREATE TABLE MOV_CINEMAS (
    CIN_ID int primary key,
    CIN_INFO_CD timestamp not null,
    CIN_INFO_RD timestamp,
    CIN_NAME varchar(100),
    CIN_PHONE varchar(12),
    CIN_ADDITIONAL_PHONE varchar(100),
    CIN_ADR_ID int,
    FOREIGN KEY (CIN_ADR_ID) REFERENCES SYS_ADDRESSES(ADR_ID)
);

CREATE SEQUENCE seq_mov_cin_id
    MINVALUE 50
    START WITH 50
    INCREMENT BY 50
    CACHE 10;
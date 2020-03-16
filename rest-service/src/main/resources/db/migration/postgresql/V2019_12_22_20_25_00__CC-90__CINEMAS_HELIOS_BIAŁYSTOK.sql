-- Galeria Alfa
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Białystok', 'Polska', '15', 'Podlaskie', 'Świętojańska') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Białystok Helios Alfa', '', (SELECT * FROM idAddress));

-- Galeria Biała
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Białystok', 'Polska', '2', 'Podlaskie', 'Czesława Miłosza') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Białystok Helios Biała', '', (SELECT * FROM idAddress));

-- Galeria Jurawiecka
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Białystok', 'Polska', '1', 'Podlaskie', 'Jurawiecka') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Białystok Helios Jurawiecka', '', (SELECT * FROM idAddress));
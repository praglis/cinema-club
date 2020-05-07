-- Warszawa, Atlantic V2020_04_29_15_14_49__CC_120_TWENTY_MORE_CINEMAS.sql
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Warszawa', 'Polska', '33', 'mazowieckie', 'Chmielna') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Warszawa, Atlantic', '', (SELECT * FROM idAddress));



-- Warszawa, Złote Tarasy, Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Warszawa', 'Polska', '59', 'mazowieckie', 'Złota') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Warszawa, Złote Tarasy Multikino', '', (SELECT * FROM idAddress));



-- Warszawa, Jana Pawła II, Cinema City
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Warszawa', 'Polska', '82', 'mazowieckie', 'Jana Pawła II') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Warszawa, Jana Pawła II Cinema City', '', (SELECT * FROM idAddress));



-- Kraków, Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Kraków', 'Polska', '128', 'małopolskie', 'Dobrego Pasterza') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Kraków, Multikino', '', (SELECT * FROM idAddress));



-- Kraków, Park Handlowy Zakopianka Cinema City
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Kraków', 'Polska', '62', 'małopolskie', 'Zakopiańska') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Kraków, Park Handlowy Zakopianka Cinema City', '', (SELECT * FROM idAddress));



-- Kraków, Galeria Kazimierz Cinema City
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Kraków', 'Polska', '34', 'małopolskie', 'Podgórska') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Kraków, Galeria Kazimierz Cinema City', '', (SELECT * FROM idAddress));



-- Łódź, Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Łódź', 'Polska', '5', 'łódzkie', 'Marszałka Józefa Piłsudskiego') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Łódź, Multikino', '', (SELECT * FROM idAddress));



-- Łódź, Cinema City
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Łódź', 'Polska', '58', 'łódzkie', 'Drewnowska') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Łódź, Cinema City', '', (SELECT * FROM idAddress));



-- Łódź, Helios
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Łódź', 'Polska', '1', 'łódzkie', 'aleja Politechniki') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Łódź, Helios', '', (SELECT * FROM idAddress));



-- Wrocław, Magnolia Park Helios
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Wrocław', 'Polska', '58', 'dolnośląskie', 'Legnicka') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Wrocław, Magnolia Park Helios', '', (SELECT * FROM idAddress));



-- Wrocław, Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Wrocław', 'Polska', '22', 'dolnośląskie', 'plac Grunwaldzki') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Wrocław, Multikino', '', (SELECT * FROM idAddress));



-- Wrocław, Cinema City
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Wrocław', 'Polska', '1', 'dolnośląskie', 'Sucha') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Wrocław, Wroclavia, Cinema City', '', (SELECT * FROM idAddress));



-- Poznań, Galeria Malta Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Poznań', 'Polska', '1', 'wielkopolskie', 'Maltańska') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Poznań, Galeria Malta Multikino', '', (SELECT * FROM idAddress));


-- Poznań, Stary Browar Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Poznań', 'Polska', '42', 'wielkopolskie', 'Półwiejska') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Poznań, Stary Browar Multikino', '', (SELECT * FROM idAddress));


-- Poznań, Królowej Jadwigi Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Poznań', 'Polska', '51', 'wielkopolskie', 'Królowej Jadwigi') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Poznań, Królowej Jadwigi Multikino', '', (SELECT * FROM idAddress));



-- Gdańsk, Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Gdańsk', 'Polska', '14', 'pomorskie', 'Aleja Zwycięstwa') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Gdańsk, Multikino', '', (SELECT * FROM idAddress));


-- Gdańsk, Forum Helios
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Gdańsk', 'Polska', '7', 'pomorskie', 'Targ Sienny') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Gdańsk, Forum Helios', '', (SELECT * FROM idAddress));


-- Gdańsk, Alfa Centrum Helios
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Gdańsk', 'Polska', '41C', 'pomorskie', 'Kołobrzeska') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Gdańsk, Alfa Centrum Helios', '', (SELECT * FROM idAddress));



-- Szczecin, Multikino
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Szczecin', 'Polska', '18/20', 'zachodniopomorskie', 'aleja Wyzwolenia') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Szczecin, Multikino', '', (SELECT * FROM idAddress));


-- Szczecin, CHR Kupiec Helios
WITH idAddress AS (INSERT INTO SYS_ADDRESSES (ADR_ID, ADR_CITY, ADR_COUNTRY, ADR_HOUSE_NUMBER, ADR_STATE, ADR_STREET)
    VALUES (NEXTVAL('seq_sys_adr_id'), 'Szczecin', 'Polska', '9/10', 'zachodniopomorskie', 'Bolesława Krzywoustego') RETURNING sys_addresses.adr_id
)
INSERT
INTO MOV_CINEMAS(CIN_ID, CIN_ADDITIONAL_PHONE, CIN_INFO_CD, CIN_INFO_RD, CIN_NAME, CIN_PHONE, CIN_ADR_ID)
VALUES (NEXTVAL('seq_mov_cin_id'), '', current_timestamp, null, 'Szczecin, CHR Kupiec Helios', '', (SELECT * FROM idAddress));
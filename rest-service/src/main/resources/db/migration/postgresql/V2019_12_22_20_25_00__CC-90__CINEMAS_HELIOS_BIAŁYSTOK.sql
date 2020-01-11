-- Galeria Alfa
WITH idAddress AS (INSERT INTO address (id, city, country, house_number, state, street_name)
    VALUES (NEXTVAL('ADDRESS_ID_SEQ'), 'Białystok', 'Polska', '15', 'Podlaskie', 'Świętojańska') RETURNING address.id
)
INSERT
INTO cinema(id, additional_phone_nos, infocd, inford, name, phone_no, address_id)
VALUES (NEXTVAL('CINEMA_ID_SEQ'), '', current_timestamp, null, 'Białystok Helios Alfa', '', (SELECT * FROM idAddress));

-- Galeria Biała
WITH idAddress AS (INSERT INTO address (id, city, country, house_number, state, street_name)
    VALUES (NEXTVAL('ADDRESS_ID_SEQ'), 'Białystok', 'Polska', '2', 'Podlaskie', 'Czesława Miłosza') RETURNING address.id
)
INSERT
INTO cinema(id, additional_phone_nos, infocd, inford, name, phone_no, address_id)
VALUES (NEXTVAL('CINEMA_ID_SEQ'), '', current_timestamp, null, 'Białystok Helios Biała', '', (SELECT * FROM idAddress));

-- Galeria Jurawiecka
WITH idAddress AS (INSERT INTO address (id, city, country, house_number, state, street_name)
    VALUES (NEXTVAL('ADDRESS_ID_SEQ'), 'Białystok', 'Polska', '1', 'Podlaskie', 'Jurawiecka') RETURNING address.id
)
INSERT
INTO cinema(id, additional_phone_nos, infocd, inford, name, phone_no, address_id)
VALUES (NEXTVAL('CINEMA_ID_SEQ'), '', current_timestamp, null, 'Białystok Helios Jurawiecka', '', (SELECT * FROM idAddress));
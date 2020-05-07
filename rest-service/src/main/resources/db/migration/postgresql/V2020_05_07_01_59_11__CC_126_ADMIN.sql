DO $$
DECLARE addr_id integer;
BEGIN
    addr_id := nextval('seq_sys_adr_id');

    INSERT INTO SYS_ROLES (rol_id, rol_name)
    VALUES (nextval('seq_usr_role_id'), 'ADMINISTRATOR');

    INSERT INTO SYS_ADDRESSES (adr_id, adr_country, adr_state,
                               adr_city, adr_street, adr_house_number)
    VALUES (addr_id, '', '', '', '', '');

    INSERT INTO USR_USERS (usr_id, usr_username, usr_password, usr_name,
                           usr_surname, usr_enrolment_date, usr_birthdate,
                           usr_email, usr_email_confirmed, usr_email_new,
                           usr_phone_no, usr_type, usr_account_status,
                           usr_points, usr_tier_id, usr_adr_id, usr_badge)
    VALUES (nextval('seq_usr_user_id'), 'admin', '$2a$10$t73btBzivaRnqkSSLaaL1es6LRXM15y/mf4nRGvdim76QqYCQDK32', 'Administrator',
            '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@cinemaclub.com', true, null,
            '000000000', 'A', 'A', 0, null, addr_id, 0);

    INSERT INTO USR_USR_ROLES (rol_id, usr_id)
    VALUES ((SELECT rol_id FROM SYS_ROLES WHERE ROL_NAME = 'ADMINISTRATOR'),
            (SELECT usr_id FROM USR_USERS WHERE usr_username = 'admin'));
END $$;

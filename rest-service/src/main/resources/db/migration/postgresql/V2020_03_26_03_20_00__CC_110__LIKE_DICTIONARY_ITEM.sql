INSERT INTO SYS_DICTIONARIES(dic_id, dic_domain) VALUES (nextval('seq_sys_dic_id'), 'USER_REVIEW');
INSERT INTO SYS_DIC_ITEMS(dit_id, dit_info_cd, dit_name, dit_value, dit_dic_id)
VALUES (nextval('seq_sys_dit_id'), CURRENT_TIMESTAMP, 'CAN_LIKE_SELF_COMMENT', '0', (SELECT DIC_ID FROM SYS_DICTIONARIES WHERE DIC_DOMAIN = 'USER_REVIEW'));
INSERT INTO SYS_DICTIONARIES(dic_id, dic_domain) VALUES (nextval('seq_sys_dic_id'), 'USER_ROLES');


INSERT INTO SYS_DIC_ITEMS(dit_id, dit_info_cd, dit_name, dit_value, dit_dic_id) VALUES (nextval('seq_sys_dit_id'), CURRENT_TIMESTAMP, 'ADMIN', 'ADMIN', (SELECT DIC_ID FROM sys_dictionaries WHERE dic_domain = 'USER_ROLES'));
INSERT INTO SYS_DIC_ITEMS(dit_id, dit_info_cd, dit_name, dit_value, dit_dic_id) VALUES (nextval('seq_sys_dit_id'), CURRENT_TIMESTAMP, 'USER', 'USER', (SELECT DIC_ID FROM sys_dictionaries WHERE dic_domain = 'USER_ROLES'));
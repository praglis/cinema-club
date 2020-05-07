INSERT INTO SYS_DICTIONARIES(dic_id, dic_domain) VALUES (nextval('seq_sys_dic_id'), 'QUERY_PARAM');


INSERT INTO SYS_DIC_ITEMS(dit_id, dit_info_cd, dit_name, dit_value, dit_dic_id) VALUES (nextval('seq_sys_dit_id'), CURRENT_TIMESTAMP, 'WITH_GENRES', 'with_genres', (SELECT DIC_ID FROM sys_dictionaries WHERE dic_domain = 'QUERY_PARAM'));
INSERT INTO SYS_DIC_ITEMS(dit_id, dit_info_cd, dit_name, dit_value, dit_dic_id) VALUES (nextval('seq_sys_dit_id'), CURRENT_TIMESTAMP, 'WITH_CAST', 'with_cast', (SELECT DIC_ID FROM sys_dictionaries WHERE dic_domain = 'QUERY_PARAM'));
INSERT INTO SYS_DIC_ITEMS(dit_id, dit_info_cd, dit_name, dit_value, dit_dic_id) VALUES (nextval('seq_sys_dit_id'), CURRENT_TIMESTAMP, 'WITH_CREW', 'with_crew', (SELECT DIC_ID FROM sys_dictionaries WHERE dic_domain = 'QUERY_PARAM'));

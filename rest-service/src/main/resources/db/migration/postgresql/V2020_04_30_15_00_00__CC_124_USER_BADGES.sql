create table USR_BADGE (
  BAD_ID int primary key,
  BAD_NAME varchar(255) not null,
  BAD_FROM int not null,
  BAD_TO int not null
);

insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (1,'Alus glutinosa', 0, 10);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (2,'Alus incana', 11, 30);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (3,'Fagus sylvatica', 31, 60);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (4,'Populus nigra', 61, 100);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (5,'Sambucus nigra', 101, 150);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (6,'Ulmus minor', 151, 200);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (7,'Padus avium', 201, 300);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (8,'Populus alba', 301, 500);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (9,'Betula pendula', 501, 1000);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (10,'Acer pseudoplatanus', 1001, 2000);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (11,'Salix', 2000, 5000);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (12,'Quercus petraea', 5001, 10000);
insert into USR_BADGE (BAD_ID, BAD_NAME, BAD_FROM, BAD_TO) values (13,'Quercus robur', 10000, 10000000);

alter table usr_users add USR_BADGE int;

update usr_users set USR_BADGE = 0;


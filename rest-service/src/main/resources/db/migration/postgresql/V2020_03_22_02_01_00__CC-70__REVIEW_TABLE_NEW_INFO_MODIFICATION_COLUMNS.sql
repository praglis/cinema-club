ALTER TABLE USR_REVIEWS ADD COLUMN URV_INFO_MD TIMESTAMP;
ALTER TABLE USR_REVIEWS ADD COLUMN URV_INFO_MU int REFERENCES USR_USERS(USR_ID);
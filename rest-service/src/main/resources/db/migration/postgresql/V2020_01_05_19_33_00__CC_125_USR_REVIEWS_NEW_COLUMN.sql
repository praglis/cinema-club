ALTER TABLE USR_REVIEWS ADD COLUMN URV_PARENT_REVIEW INTEGER REFERENCES USR_REVIEWS(URV_ID);

# --- !Ups
CREATE TABLE IMAGES (
  USER_ID INT NOT NULL,
  URL_SMALL VARCHAR(1000) NOT NULL,
  URL_LARGE VARCHAR(1000) NOT NULL,
  CONSTRAINT USER_ID_UK UNIQUE (USER_ID)
);

COMMENT ON TABLE IMAGES IS 'Table that stores all users images.';

COMMENT ON COLUMN IMAGES.USER_ID IS 'User ID.';
COMMENT ON COLUMN IMAGES.URL_SMALL IS 'URL of the small image.';
COMMENT ON COLUMN IMAGES.URL_LARGE IS 'URL of the large image.';

INSERT INTO IMAGES (USER_ID, URL_SMALL, URL_LARGE) VALUES(1,'http://vignette2.wikia.nocookie.net/rocky/images/5/51/Troyb2.jpg/revision/latest?cb=20070530195958', 'http://static.comicvine.com/uploads/original/11119/111190794/4789181-6995769998-ivand.jpg');
INSERT INTO IMAGES (USER_ID, URL_SMALL, URL_LARGE) VALUES(2,'http://www.sceneonfilm.com/image/data/original.jpg','http://media.salon.com/2014/12/rocky_balboa.jpg');

# --- !Downs
DROP TABLE IMAGES;


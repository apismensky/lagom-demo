# --- !Ups
INSERT INTO IMAGES (USER_ID, URL_SMALL, URL_LARGE) VALUES(1,'http://vignette2.wikia.nocookie.net/rocky/images/5/51/Troyb2.jpg/revision/latest?cb=20070530195958', 'http://static.comicvine.com/uploads/original/11119/111190794/4789181-6995769998-ivand.jpg');
INSERT INTO IMAGES (USER_ID, URL_SMALL, URL_LARGE) VALUES(2,'http://www.sceneonfilm.com/image/data/original.jpg','http://media.salon.com/2014/12/rocky_balboa.jpg');

# --- !Downs
DELETE FROM IMAGES WHERE USER_ID IN(1,2)


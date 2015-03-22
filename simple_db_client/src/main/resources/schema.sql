-- DROP TABLE sb_user;

CREATE TABLE sb_user
(
  id   SERIAL PRIMARY KEY    NOT NULL,
  name CHARACTER VARYING(65) NOT NULL,
  alias CHARACTER VARYING(20)
);

INSERT INTO sb_user (name) VALUES ('Ben Linus');
INSERT INTO sb_user (name) VALUES ('Claire Littleton');
INSERT INTO sb_user (name) VALUES ('Desmond Hume');
INSERT INTO sb_user (name) VALUES ('Frank Lapidus');
INSERT INTO sb_user (name, alias) VALUES ('Hugo Reyes', 'Hurley');
INSERT INTO sb_user (name) VALUES ('Jack Shephard');
INSERT INTO sb_user (name, alias) VALUES ('James Ford', 'Sawyer');
INSERT INTO sb_user (name) VALUES ('Jin-Soo Kwon');
INSERT INTO sb_user (name) VALUES ('Miles Straume');
INSERT INTO sb_user (name) VALUES ('Richard Alpert');
INSERT INTO sb_user (name) VALUES ('Sayid Jarrah');
INSERT INTO sb_user (name) VALUES ('Sun-Hwa Kwon');
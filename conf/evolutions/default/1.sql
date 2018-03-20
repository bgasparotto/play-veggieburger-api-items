# --- !Ups

CREATE TABLE IF NOT EXISTS ITEM (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY,
  NAME VARCHAR(30) NOT NULL,
  PRICE DECIMAL(19,2) NOT NULL CHECK (PRICE>=1),
  PRIMARY KEY (ID)
);

# --- !Downs

DROP TABLE ITEM;
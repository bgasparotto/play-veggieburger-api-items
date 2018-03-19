TRUNCATE TABLE ITEM;
ALTER TABLE ITEM ALTER COLUMN ID RESTART WITH 1;

INSERT INTO ITEM
(ID, NAME, PRICE)
VALUES
(1, 'Original Veggie Burger', 10),
(2, 'Small Veggie Burger', 9),
(3, 'Medium Veggie Burger', 11),
(4, 'Big Veggie Burger', 13),
(5, 'Family Veggie Burger', 18);
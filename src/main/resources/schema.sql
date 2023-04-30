DROP TABLE IF EXISTS Currency;

CREATE TABLE IF NOT EXISTS Currency(
currency_id int AUTO_INCREMENT PRIMARY KEY,
currency varchar(255),
code varchar(4),
mid double
);

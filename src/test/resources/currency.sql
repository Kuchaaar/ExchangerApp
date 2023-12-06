CREATE TABLE IF NOT EXISTS currency
(
    currency_id
    int
    AUTO_INCREMENT
    PRIMARY
    KEY,
    currency
    varchar
(
    255
),
    code varchar
(
    4
),
    mid double,
    date date
    );
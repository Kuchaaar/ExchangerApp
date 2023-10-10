CREATE TABLE IF NOT EXISTS Currency
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

CREATE TABLE IF NOT EXISTS Holidays
(
    holidays_id
    int
    AUTO_INCREMENT
    PRIMARY
    KEY,
    date
    date,
    name
    varchar
(
    255
)
    );

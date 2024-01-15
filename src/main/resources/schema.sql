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

CREATE TABLE IF NOT EXISTS holidays
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
CREATE TABLE IF NOT EXISTS roles (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(20) NOT NULL
);
CREATE TABLE IF NOT EXISTS users (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(20) NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       password VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
                            user_id BIGINT,
                            role_id INT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id)
);

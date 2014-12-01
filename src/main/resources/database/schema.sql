DROP TABLE forum_users IF EXISTS;
CREATE TABLE forum_users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    firstname VARCHAR(255),
    lastname VARCHAR(255),
    organization VARCHAR(255),
    title VARCHAR(255),
    password VARCHAR(255),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

DROP TABLE forum_categories IF EXISTS;
CREATE TABLE forum_categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);


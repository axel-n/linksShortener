CREATE TABLE IF NOT EXISTS LINKS (
    link_id int,
    user_id int,
    shortURL VARCHAR(5) NOT NULL UNIQUE,
    longURL TEXT(2048) NOT NULL,
    createTime TIMESTAMP NOT NULL,
    statistics TEXT(100) NOT NULL,
    PRIMARY KEY(link_id),
);

CREATE TABLE IF NOT EXISTS USERS (
    user_id int,
    createTime TIMESTAMP NOT NULL,
    PRIMARY KEY(user_id)
);
CREATE TABLE IF NOT EXISTS LINKS (
    shortURL VARCHAR(5),
    longURL TEXT(2048) NOT NULL,
    createTime TIMESTAMP NOT NULL,
    statistics TEXT(100) NOT NULL,
    PRIMARY KEY(shortURL),
);
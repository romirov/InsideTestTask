CREATE TABLE IF NOT EXISTS user_table (
    id SERIAL,
    username VARCHAR(100) NOT NULL,
    password varchar(100) NOT NULL,
    PRIMARY KEY(id, username),
    UNIQUE(username)
);

CREATE TABLE IF NOT EXISTS message_table (
    id SERIAL,
    username VARCHAR(100) NOT NULL,
    message VARCHAR(100),
    PRIMARY KEY(id, username),
    FOREIGN KEY (username) REFERENCES user_table(username) ON DELETE CASCADE
);
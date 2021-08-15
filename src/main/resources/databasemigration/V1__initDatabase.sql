CREATE TABLE players(
    uuid varchar(36) PRIMARY KEY,
    name varchar(16) NOT NULL ,
    current_server VARCHAR(64) NULL,
    joins INT NOT NULL
)
CREATE TABLE IF NOT EXISTS users
(
    id         BIGINT               NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    username   VARCHAR(32) UNIQUE   NOT NULL,
    email      VARCHAR(100) UNIQUE  NOT NULL,
    is_active  BOOL,
    user_role  SMALLINT default (3) NOT NULL,
    password   VARCHAR(48)          NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
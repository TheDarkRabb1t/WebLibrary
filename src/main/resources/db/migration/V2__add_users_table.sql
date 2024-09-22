CREATE TABLE IF NOT EXISTS users
(
    id        BIGINT              NOT NULL,
    username  VARCHAR(255) UNIQUE NOT NULL,
    email     VARCHAR(255) UNIQUE NOT NULL,
    user_role SMALLINT            NOT NULL,
    password  VARCHAR(255)        NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);
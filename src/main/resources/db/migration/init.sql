CREATE DATABASE "weblirary";
CREATE TABLE IF NOT EXISTS authors
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    created  TIMESTAMP WITHOUT TIME ZONE,
    modified TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_authors PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS books
(
    id           BIGINT  NOT NULL,
    title        VARCHAR(255),
    pages        INTEGER NOT NULL,
    isbn         VARCHAR(255),
    publisher_id BIGINT,
    genre        SMALLINT,
    written      TIMESTAMP WITHOUT TIME ZONE,
    removed      BOOLEAN NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE,
    modified     TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_books PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS publishers
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    address  VARCHAR(255),
    created  TIMESTAMP WITHOUT TIME ZONE,
    modified TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_publishers PRIMARY KEY (id)
);

ALTER TABLE books
    ADD CONSTRAINT FK_BOOKS_ON_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES publishers (id);

CREATE TABLE book_authors
(
    authors_id BIGINT NOT NULL,
    book_id    BIGINT NOT NULL,
    CONSTRAINT pk_book_authors PRIMARY KEY (authors_id, book_id)
);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_booaut_on_author FOREIGN KEY (authors_id) REFERENCES authors (id);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_booaut_on_book FOREIGN KEY (book_id) REFERENCES books (id);
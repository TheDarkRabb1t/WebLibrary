CREATE TABLE IF NOT EXISTS authors
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    email    VARCHAR(255),
    imgUrl   VARCHAR(255),
    created  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    modified TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    CONSTRAINT pk_authors PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS publishers
(
    id       BIGINT NOT NULL,
    name     VARCHAR(255),
    address  VARCHAR(255),
    created  TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    modified TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    CONSTRAINT pk_publishers PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS books
(
    id           BIGINT  NOT NULL,
    title        VARCHAR(255),
    description  VARCHAR(255),
    imgUrl       VARCHAR(255),
    pages        INTEGER NOT NULL,
    isbn         VARCHAR(255),
    publisher_id BIGINT,
    genre        SMALLINT,
    written      TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    removed      BOOLEAN NOT NULL,
    created      TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    modified     TIMESTAMP WITH TIME ZONE DEFAULT current_timestamp,
    CONSTRAINT pk_books PRIMARY KEY (id)
);

CREATE TABLE book_authors
(
    authors_id BIGINT NOT NULL,
    book_id    BIGINT NOT NULL,
    CONSTRAINT pk_book_authors PRIMARY KEY (authors_id, book_id)
);
ALTER TABLE books
    ADD CONSTRAINT fk_books_on_publisher FOREIGN KEY (publisher_id) REFERENCES publishers (id);
ALTER TABLE books
    ADD CONSTRAINT uq_book_isbn UNIQUE (isbn);
ALTER TABLE authors
    ADD CONSTRAINT uq_author_email UNIQUE (email);
ALTER TABLE publishers
    ADD CONSTRAINT uq_publisher_name UNIQUE (name);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_books_and_authors_on_author FOREIGN KEY (authors_id) REFERENCES authors (id);

ALTER TABLE book_authors
    ADD CONSTRAINT fk_books_and_authors_on_book FOREIGN KEY (book_id) REFERENCES books (id);
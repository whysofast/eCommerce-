CREATE TABLE product
(
    id          varchar(50),
    name            varchar(50),
    description      varchar(255),
    price       BIGINT,
    created_at  date,
    modified_at date,
    PRIMARY KEY (id)
);
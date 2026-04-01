CREATE TABLE unit
(
    id        INT           NOT NULL,
    code      VARCHAR(10)  NOT NULL,
    name      VARCHAR(255) NOT NULL,
    contentful_value      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_unit_id PRIMARY KEY (id)
);

INSERT INTO unit(id, code, name, contentful_value)
VALUES (1, 'neo', 'Neo','BH');

CREATE TABLE language
(
    id        INT           NOT NULL,
    code      VARCHAR(10)  NOT NULL,
    name      VARCHAR(255) NOT NULL,
    contentful_value      VARCHAR(255) NOT NULL,
    CONSTRAINT pk_language_id PRIMARY KEY (id)
);

INSERT INTO language(id, code, name, contentful_value)
VALUES (1, 'en', 'English','en-US'),
       ( 2	,'ar',	'Arabic','ar-BH');

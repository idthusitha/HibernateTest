

-- language
CREATE TABLE rezgos_reports.language (
    id integer NOT NULL,
    languagecode character varying (2),
    languagename character varying (100),
    CONSTRAINT language_pkey PRIMARY KEY  (id)
);

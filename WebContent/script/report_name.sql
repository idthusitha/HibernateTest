

-- report_name
CREATE TABLE rezgos_reports.report_name (
    id integer NOT NULL,
    report_name character varying (150),
    status character varying (1),
    CONSTRAINT report_name_pkey PRIMARY KEY  (id)
);

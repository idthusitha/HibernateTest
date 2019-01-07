

-- report_labels
CREATE TABLE rezgos_reports.report_labels (
    id integer NOT NULL,
    label_key character varying (150),
    status character varying (1),
    CONSTRAINT report_labels_pkey PRIMARY KEY  (id)
);

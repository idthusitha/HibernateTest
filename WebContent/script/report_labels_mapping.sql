

-- report_labels_mapping
CREATE TABLE rezgos_reports.report_labels_mapping (
    id integer NOT NULL,
    language_id integer NOT NULL,
    report_id integer NOT NULL,
    report_labels_id integer NOT NULL,
    label_value character varying (1000),
    status character varying (1),
    CONSTRAINT report_labels_mapping_pkey PRIMARY KEY  (id)
);

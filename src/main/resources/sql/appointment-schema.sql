CREATE TABLE tbl_appointments (
    appointment_id character varying NOT NULL,
    technician_id character varying NOT NULL,
    service_id character varying NOT NULL,
    service_started character varying NOT NULL,
    service_finished character varying NOT NULL,
    week_number smallint NOT NULL
);
module osiris.data.common {
    requires kotlin.stdlib;
    requires transitive java.sql;

    exports io.osiris.data.common.binding to osiris.data.jpa, osiris.data.repository, osiris.query.common;

    exports io.osiris.data.common.annotation;
    exports io.osiris.data.common.dto;
    exports io.osiris.data.common.repository;
}
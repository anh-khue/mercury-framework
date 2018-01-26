module osiris.data.common {
    requires osiris.data.connection;
    requires kotlin.stdlib;
    requires java.sql;

    exports io.osiris.data.common.binding.function to osiris.data.jpa, osiris.data.repository, osiris.query.common;

    exports io.osiris.data.common.annotation;
    exports io.osiris.data.common.binding;
    exports io.osiris.data.common.dto;
    exports io.osiris.data.common.repository;
}
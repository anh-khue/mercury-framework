module osiris.data.common {
    requires kotlin.stdlib;
    requires java.sql;

    exports com.osiris.data.common.binding to osiris.data.jpa;
    exports com.osiris.data.common.handler to osiris.data.jpa;

    exports com.osiris.data.common.annotation;
    exports com.osiris.data.common.dto;
    exports com.osiris.data.common.repository;
}
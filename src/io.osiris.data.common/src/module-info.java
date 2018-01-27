module io.osiris.data.common {
    requires kotlin.stdlib;
    requires java.sql;

    exports io.osiris.data.common.binding.function to
            io.osiris.data.jpa,
            io.osiris.data.repository,
            io.osiris.query.common;

    exports io.osiris.data.common.annotation;
    exports io.osiris.data.common.binding;
    exports io.osiris.data.common.dto;
    exports io.osiris.data.common.repository;
}
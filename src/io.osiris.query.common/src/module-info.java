module io.osiris.query.common {
    requires io.osiris.query.tuple;
    requires io.osiris.data.connection;
    requires java.sql;
    requires io.osiris.data.jpa;
    requires io.osiris.data.common;

    exports io.osiris.query.common;
    exports io.osiris.query.common.builder;
}
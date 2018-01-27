module io.osiris.data.jpa {
    requires java.sql;
    requires io.osiris.data.common;
    requires io.osiris.data.connection;

    exports io.osiris.data.jpa.binding to
            io.osiris.data.repository,
            io.osiris.query.common;

    exports io.osiris.data.jpa;
}
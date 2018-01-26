module osiris.data.jpa {
    requires osiris.data.common;
    requires osiris.data.connection;
    requires java.sql;

    exports io.osiris.data.jpa.binding to osiris.data.repository, osiris.query.common;
    exports io.osiris.data.jpa;
}
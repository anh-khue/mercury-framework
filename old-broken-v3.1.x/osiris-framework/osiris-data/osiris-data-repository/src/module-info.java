module osiris.data.repository {
    requires osiris.data.jpa;
    requires osiris.data.common;
    requires osiris.data.connection;
    requires java.sql;

    opens io.osiris.data.repository to osiris.data.jpa;

    exports io.osiris.data.repository;
}
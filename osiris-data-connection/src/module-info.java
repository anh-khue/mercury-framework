module osiris.data.connection {
    requires transitive java.sql;
    requires kotlin.stdlib;

    exports io.osiris.data.connection;
    exports io.osiris.data.connection.xml to osiris.data.jpa, osiris.data.repository;
}
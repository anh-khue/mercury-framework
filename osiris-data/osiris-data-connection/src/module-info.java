module osiris.data.connection {
    requires java.sql;
    requires kotlin.stdlib;

    exports io.osiris.data.connection.xml to osiris.data.jpa, osiris.data.repository, osiris.query.common;
    exports io.osiris.data.connection.properties to osiris.data.jpa, osiris.data.repository, osiris.query.common;

    exports io.osiris.data.connection;
}
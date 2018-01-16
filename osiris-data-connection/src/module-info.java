module osiris.data.connection {
    requires transitive java.sql;
    requires kotlin.stdlib;

    exports com.osiris.data.connection;
    exports com.osiris.data.connection.xml to osiris.data.jpa;
}
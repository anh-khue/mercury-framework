module osiris.data.connection {
    requires transitive java.sql;

    exports com.osiris.data.connection;
    exports com.osiris.data.connection.xml to osiris.data.orm;
}
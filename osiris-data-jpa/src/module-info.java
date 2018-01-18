module osiris.data.jpa {
    requires transitive osiris.data.common;
    requires transitive osiris.data.connection;

    exports io.osiris.data.jpa.binding to osiris.data.repository;
    exports io.osiris.data.jpa;
}
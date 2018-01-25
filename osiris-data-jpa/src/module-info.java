module osiris.data.jpa {
    requires transitive osiris.data.common;
    requires transitive osiris.data.connection;

    exports io.osiris.data.jpa.binding to osiris.data.repository, osiris.query.common;
    exports io.osiris.data.jpa;
}
module osiris.data.jpa {
    requires osiris.data.common;
    requires osiris.data.connection;
    requires kotlin.stdlib;

    exports io.osiris.data.jpa.binding to osiris.data.repository;
    exports io.osiris.data.jpa;
}
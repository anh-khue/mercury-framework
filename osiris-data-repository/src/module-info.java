module osiris.data.repository {
    requires osiris.data.common;
    requires osiris.data.connection;
    requires osiris.data.jpa;

    opens io.osiris.data.repository to osiris.data.jpa;

    exports io.osiris.data.repository;
}
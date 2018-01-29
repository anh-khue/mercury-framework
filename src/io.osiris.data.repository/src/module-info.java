module io.osiris.data.repository {
    requires io.osiris.data.jpa;
    requires io.osiris.data.common;
    requires io.osiris.data.connection;
    requires java.sql;

    opens io.osiris.data.repository.sustenance.dto;

    exports io.osiris.data.repository;
}
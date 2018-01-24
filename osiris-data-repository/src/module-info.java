module osiris.data.repository {
    requires osiris.data.jpa;

    opens io.osiris.data.repository to osiris.data.jpa;

    exports io.osiris.data.repository;
}
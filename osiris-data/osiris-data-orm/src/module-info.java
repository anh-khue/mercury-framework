module osiris.data.orm {
    requires transitive osiris.data.common;
    requires kotlin.stdlib;
    requires java.sql;

    exports io.osiris.data.orm.binding to osiris.data.jpa;
    exports io.osiris.data.orm.handler to osiris.data.jpa;

    exports io.osiris.data.orm.annotation;
}
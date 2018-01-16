module osiris.data.orm {
    requires transitive osiris.data.common;
    requires kotlin.stdlib;
    requires java.sql;

    exports com.osiris.data.orm.binding to osiris.data.jpa;
    exports com.osiris.data.orm.handler to osiris.data.jpa;

    exports com.osiris.data.orm.annotation;
}
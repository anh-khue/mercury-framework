module osiris.data.orm {
    requires transitive osiris.data.common;
    requires osiris.data.connection;
    requires kotlin.stdlib;

    exports com.osiris.data.orm.entity to osiris.data;
    exports com.osiris.data.orm.annotation;
}
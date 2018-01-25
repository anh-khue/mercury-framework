module osiris.query.common {
    requires osiris.data.common;
    requires osiris.data.jpa;
    requires osiris.query.tuple;

    exports io.osiris.query.common.builder;
    exports io.osiris.query.common.predicate;
}
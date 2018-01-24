package io.osiris.query.tuple;

import io.osiris.query.tuple.basic.BasicTupleType;

public class TupleTypeFactory {

    public static TupleType create(Class<?>[] types) {
        return new BasicTupleType(types);
    }
}

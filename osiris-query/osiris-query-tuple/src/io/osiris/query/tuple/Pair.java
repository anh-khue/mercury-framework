package io.osiris.query.tuple;

public class Pair extends QueryTuple implements ConcreteTuple {

    Pair(Object value1, Object value2) {
        super(value1, value2);
    }

    @Override
    public Pair generate(Object... values) {
        if (values.length != 2)
            throw new IllegalArgumentException("Expected 2 parameters.");
        return new Pair(values[0], values[1]);
    }

    public static Pair create(Object value1, Object value2) {
        return new Pair(value1, value2);
    }
}

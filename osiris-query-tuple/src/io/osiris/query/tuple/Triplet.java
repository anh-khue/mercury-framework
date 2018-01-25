package io.osiris.query.tuple;

public class Triplet extends QueryTuple implements ConcreteTuple {

    Triplet(Object value1, Object value2, Object value3) {
        super(value1, value2, value3);
    }

    @Override
    public Triplet generate(Object... values) {
        if (values.length != 3)
            throw new IllegalArgumentException("Expected 3 parameters.");
        return new Triplet(values[0], values[1], values[2]);
    }

    public static Triplet create(Object value1, Object value2, Object value3) {
        return new Triplet(value1, value2, value3);
    }
}

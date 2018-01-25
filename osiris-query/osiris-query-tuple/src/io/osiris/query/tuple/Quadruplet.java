package io.osiris.query.tuple;

public class Quadruplet extends QueryTuple implements ConcreteTuple {

    Quadruplet(Object value1, Object value2, Object value3, Object value4) {
        super(value1, value2, value3, value4);
    }

    @Override
    public Tuple generate(Object... values) {
        if (values.length != 4)
            throw new IllegalArgumentException("Expected 4 parameters.");
        return new Quadruplet(values[0], values[1], values[2], values[3]);
    }

    public static Quadruplet create(Object value1, Object value2, Object value3, Object value4) {
        return new Quadruplet(value1, value2, value3, value4);
    }
}

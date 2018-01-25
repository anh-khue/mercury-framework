package io.osiris.query.tuple;

public class TupleFactory {

    public static Tuple create(Object... values) {
        switch (values.length) {
            case 2:
                return new Pair(values[0], values[1]);
            case 3:
                return new Triplet(values[0], values[1], values[2]);
            case 4:
                return new Quadruplet(values[0], values[1], values[2], values[3]);
            default:
                return new QueryTuple(values);
        }
    }
}

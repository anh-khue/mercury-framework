package io.osiris.query.tuple.basic;

import io.osiris.query.tuple.Tuple;
import io.osiris.query.tuple.TupleType;

public class BasicTupleType implements TupleType {

    private final Class<?>[] types;

    public BasicTupleType(Class<?>[] types) {
        this.types = types != null ? types : new Class<?>[0];
    }

    @Override
    public int size() {
        return types.length;
    }

    @Override
    public Class<?> getTypeAt(int index) {
        return types[index];
    }

    @Override
    public Tuple create(Object... values) {
        if ((values == null && types.length == 0) || (values != null && values.length != types.length))
            throw new IllegalArgumentException(
                    "Expected " + types.length + " values. But there are " + values.length + "values");

        if (values != null) {
            for (int i = 0; i < types.length; i++) {
                final Class<?> type = types[i];
                final Object value = values[i];
                if (value != null && !type.isAssignableFrom(value.getClass()))
                    throw new IllegalArgumentException(
                            "Expected value of '" + value + "' to be '" + type + "'. " +
                                    "But it shows to be '" + value.getClass() + "' instead.");
            }
        }

        return new BasicTuple(values);
    }
}

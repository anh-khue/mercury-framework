package io.osiris.query.tuple.basic;

import io.osiris.query.tuple.Tuple;

public class BasicTuple implements Tuple {

    private final Object[] values;

    BasicTuple(Object[] values) {
        this.values = values;
    }

    @Override
    public int size() {
        return values.length;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getValueAt(int index) {
        return (T) values[index];
    }
}

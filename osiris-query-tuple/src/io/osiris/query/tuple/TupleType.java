package io.osiris.query.tuple;

public interface TupleType {

    int size();

    Class<?> getTypeAt(int index);

    Tuple create(Object... values);
}

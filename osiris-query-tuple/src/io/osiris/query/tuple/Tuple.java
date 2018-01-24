package io.osiris.query.tuple;

public interface Tuple {

    int size();

    <T> T getValueAt(int index);
}

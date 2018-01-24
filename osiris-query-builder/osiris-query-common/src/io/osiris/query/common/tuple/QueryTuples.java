package io.osiris.query.common.tuple;

import io.osiris.query.tuple.Tuple;
import io.osiris.query.tuple.TupleType;

import java.util.Arrays;
import java.util.List;

public class QueryTuples {

    public static class WhereTriTuple implements Tuple {

        private final List<Object> values;

        public WhereTriTuple(List<Object> values) {
            this.values = values;
        }

        @Override
        public int size() {
            return values.size();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> T getValueAt(int index) {
            return (T) values.get(index);
        }
    }


    public static class WhereTriTupleType implements TupleType {

        private final List<Class<?>> types = Arrays.asList(String.class, String.class, String.class);

        @Override
        public int size() {
            return types.size();
        }

        @Override
        public Class<?> getTypeAt(int index) {
            return types.get(index);
        }

        @Override
        public Tuple create(Object... values) {
            if ((values == null && types.size() == 0) || (values != null && values.length != types.size()))
                throw new IllegalArgumentException(
                        "Expected " + types.size() + " values. But there are " + values.length + "values");

            if (values != null) {
                for (int i = 0; i < types.size(); i++) {
                    final Class<?> type = types.get(i);
                    final Object value = values[i];
                    if (value != null && !type.isAssignableFrom(value.getClass()))
                        throw new IllegalArgumentException(
                                "Expected value of '" + value + "' to be '" + type + "'. " +
                                        "But it shows to be '" + value.getClass() + "' instead.");
                }
            }

            return new WhereTriTuple(Arrays.asList(values));
        }

        public static Tuple create(Object... values) {

        }
    }

}

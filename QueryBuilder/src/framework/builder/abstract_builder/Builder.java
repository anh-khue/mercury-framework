package framework.builder.abstract_builder;

import java.io.Serializable;

public interface Builder<T> extends Serializable {
    T build();
}

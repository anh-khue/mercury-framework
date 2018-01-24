package io.osiris.query.common.function;

@FunctionalInterface
public interface QueryHandler<A, B, C, D> {

    void handle();
}

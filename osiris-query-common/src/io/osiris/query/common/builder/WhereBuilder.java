package io.osiris.query.common.builder;

public class WhereBuilder implements WhereComponent {

    private final QueryBuilder queryBuilder;
    public static StringBuilder query;

    protected WhereBuilder(QueryBuilder queryBuilder) {
        query = new StringBuilder("WHERE");
        this.queryBuilder = queryBuilder;
    }

    public WhereComponent where(String param1, String param2, String param3) {
        return this;
    }

    public WhereComponent where(String param1, String param2, int param3) {
        return this;
    }

    public WhereComponent where(String param1, String param2, double param3) {
        return this;
    }

    public WhereComponent where(String param1, String param2, String param3, String param4) {
        return this;
    }

    public WhereComponent where(String param1, String param2, int param3, int param4) {
        return this;
    }

    public WhereComponent where(String param1, String param2, double param3, double param4) {
        return this;
    }

    @Override
    public WhereComponent andWhere(String param1, String param2, String param3) {
        return this;
    }

    @Override
    public WhereComponent andWhere(String param1, String param2, int param3) {
        return this;
    }

    @Override
    public WhereComponent andWhere(String param1, String param2, double param3) {
        return this;
    }

    @Override
    public WhereComponent andWhere(String param1, String param2, String param3, String param4) {
        return this;
    }

    @Override
    public WhereComponent andWhere(String param1, String param2, int param3, int param4) {
        return this;
    }

    @Override
    public WhereComponent andWhere(String param1, String param2, double param3, double param4) {
        return this;
    }

    @Override
    public WhereComponent orWhere(String param1, String param2, String param3) {
        return this;
    }

    @Override
    public WhereComponent orWhere(String param1, String param2, int param3) {
        return this;
    }

    @Override
    public WhereComponent orWhere(String param1, String param2, double param3) {
        return this;
    }

    @Override
    public WhereComponent orWhere(String param1, String param2, String param3, String param4) {
        return this;
    }

    @Override
    public WhereComponent orWhere(String param1, String param2, int param3, int param4) {
        return this;
    }

    @Override
    public WhereComponent orWhere(String param1, String param2, double param3, double param4) {
        return this;
    }

    @Override
    public QueryBuilder then() {
        return this.queryBuilder;
    }
}

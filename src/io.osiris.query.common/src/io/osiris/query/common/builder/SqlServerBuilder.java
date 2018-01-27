package io.osiris.query.common.builder;

import io.osiris.query.tuple.Triplet;

public class SqlServerBuilder extends QueryBuilder {

    @Override
    public QueryBuilder limit(int limit) {
        if (this.limit.called) return this;

        this.limit.command = new StringBuilder("TOP ? ");
        this.limit.maxResult = limit;

        this.limit.called = true;
        return this;
    }

    @Override
    public QueryBuilder paging(int itemPerPage, int page) {
        if (this.paging.called) return this;

        this.paging.command = new StringBuilder("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ");
        this.paging.itemPerPage = itemPerPage;
        this.paging.page = (page - 1) * itemPerPage;

        this.paging.called = true;
        return this;
    }

    @Override
    public QueryBuilder join(String table, String as, Triplet onTriplet) {
        return null;
    }

    @Override
    public SqlServerBuilder build() {
        if (this.query != null) return this;

        this.query = new StringBuilder("SELECT ")
                .append(this.distinct.retrieve())
                .append(this.limit.retrieve())
                .append(columns)
                .append(this.from.retrieve())
                .append(this.join.retrieve())
                .append(this.where.retrieve())
                .append(this.groupBy.retrieve())
                .append(this.having.retrieve())
                .append(this.orderBy.retrieve())
                .append(this.paging.retrieve());

        this.params.addAll(this.where.params);
        this.params.addAll(this.having.params);

        if (this.limit.called) this.params.add(String.valueOf(this.limit.maxResult));

        if (this.paging.called) {
            this.params.add(String.valueOf(this.paging.itemPerPage));
            this.params.add(String.valueOf(this.paging.page));
        }

        return this;
    }
}

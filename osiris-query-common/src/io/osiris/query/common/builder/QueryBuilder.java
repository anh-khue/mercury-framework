package io.osiris.query.common.builder;

import io.osiris.data.common.binding.DataBindingHandler;
import io.osiris.data.common.dto.DTO;
import io.osiris.data.jpa.binding.EntityDataBindings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class QueryBuilder implements Builder {

    protected WhereBuilder whereBuilder = new WhereBuilder(this);

    protected static String table;
    protected static StringBuilder distinct = new StringBuilder("");
    protected static StringBuilder limit = new StringBuilder("");
    protected static StringBuilder columns = new StringBuilder("");
    protected static StringBuilder join = new StringBuilder("");
    protected static StringBuilder where = new StringBuilder("");
    protected static StringBuilder orderBy = new StringBuilder("");
    protected static StringBuilder groupBy = new StringBuilder("");
    protected static StringBuilder having = new StringBuilder("");
    protected static StringBuilder paging = new StringBuilder("");

    QueryBuilder table(Class<DTO> model) {
        this.model = model;
        modelAnnotationProcessor = new ModelAnnotationProcessor(model);
        table = modelAnnotationProcessor.getTableName();
        table = EntityDataBindings.
        columns.append(modelAnnotationProcessor.getTableColumns(model)
                .stream()
                .collect(Collectors.joining(",")));
        columns.append(" ");

        queryParams.put("limit", new ArrayList());
        queryParams.put("where", new ArrayList());
        queryParams.put("having", new ArrayList());
        queryParams.put("paging", new ArrayList());
        return this;
    }

    QueryBuilder distinct() {
        return this;
    }

    // Where
    public WhereComponent where(String param1, String param2, String param3) {
        return whereBuilder;
    }

    public WhereComponent where(String param1, String param2, int param3) {
        return whereBuilder;
    }

    public WhereComponent where(String param1, String param2, double param3) {
        return whereBuilder;
    }

    public WhereComponent where(String param1, String param2, String param3, String param4) {
        return whereBuilder;
    }

    public WhereComponent where(String param1, String param2, int param3, int param4) {
        return whereBuilder;
    }

    public WhereComponent where(String param1, String param2, double param3, double param4) {
        return whereBuilder;
    }

    // Join
    QueryBuilder join() {
        return this;
    }

    // Order By
    QueryBuilder orderBy() {
        return this;
    }


    // Group By
    public QueryBuilder groupBy() {
        return this;
    }

    // Having
    public QueryBuilder having() {
        return this;
    }

    QueryBuilder limit() {
        return this;
    }

    QueryBuilder paging() {
        return this;
    }


    abstract List<DTO> getResultList();

    abstract DTO getSingleResult();

    @Override
    public QueryBuilder build() {
        return this;
    }
}

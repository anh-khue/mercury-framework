package framework.builder.querybuilder;

import framework.builder.abstract_builder.CommonQueryBuilder;
import framework.builder.abstract_builder.QueryBuilder;
import framework.model_mapping.model.Model;

class SQLServerQueryBuilder<T extends Model> extends CommonQueryBuilder<T> {

    @Override
    public QueryBuilder limit(int maxResult) {
        if (limit.toString().equals("")) {
            limit.append("TOP ?").append(" ");
        }
        queryParams.get("paging").add(maxResult);

        return this;
    }

    @Override
    public QueryBuilder paging(int limit, int page) {
        if (paging.toString().equals("")) {
            paging.append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY")
                    .append(" ");
        }
        queryParams.get("paging").add((page - 1) * limit);
        queryParams.get("paging").add(limit);

        return this;
    }

    @Override
    public QueryBuilder build() {
        query = new StringBuilder("SELECT ");
        query.append(distinct)
                .append(limit)
                .append(columns)
                .append("FROM ").append(table).append(" ")
                .append(join)
                .append(where)
                .append(groupBy)
                .append(having)
                .append(orderBy)
                .append(paging);

        parameters.addAll(queryParams.get("limit"));
        parameters.addAll(queryParams.get("where"));
        parameters.addAll(queryParams.get("having"));
        parameters.addAll(queryParams.get("paging"));

        return this;
    }
}

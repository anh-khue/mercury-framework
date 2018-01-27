package framework.builder.querybuilder;

import framework.builder.abstract_builder.CommonQueryBuilder;
import framework.builder.abstract_builder.QueryBuilder;
import framework.model_mapping.model.Model;

public class MySQLQueryBuilder<T extends Model> extends CommonQueryBuilder<T> {
    @Override
    public QueryBuilder limit(int maxResult) {
        if (limit.toString().equals("")) {
            limit.append("LIMIT ?").append(" ");
        }
        queryParams.get("paging").add(maxResult);

        return this;
    }

    @Override
    public QueryBuilder paging(int limit, int page) {
        if (paging.toString().equals("")) {
            paging.append("LIMIT ?")
                    .append(" OFFSET ?")
                    .append(" ");
        }
        queryParams.get("paging").add(limit);
        queryParams.get("paging").add((page - 1) * limit);

        return this;
    }

    @Override
    public QueryBuilder build() {
        query = new StringBuilder("SELECT ");
        query.append(distinct)
                .append(columns)
                .append("FROM ").append(table).append(" ")
                .append(join)
                .append(where)
                .append(groupBy)
                .append(having)
                .append(orderBy)
                .append(limit)
                .append(paging);

        parameters.addAll(queryParams.get("where"));
        parameters.addAll(queryParams.get("having"));
        parameters.addAll(queryParams.get("limit"));
        parameters.addAll(queryParams.get("paging"));

        return this;
    }
}

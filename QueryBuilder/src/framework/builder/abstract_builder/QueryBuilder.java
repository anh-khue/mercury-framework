package framework.builder.abstract_builder;

import com.sun.istack.internal.Nullable;
import framework.model_mapping.model.Model;

import java.util.List;

public interface QueryBuilder<T extends Model> extends Builder<QueryBuilder> {
    QueryBuilder distinct();

    QueryBuilder table(Class<T> model);

    QueryBuilder join(Object[][] joinConditions);

    QueryBuilder where(Object[][] andConditions);

    QueryBuilder orWhere(Object[][] orConditions);

    QueryBuilder whereBetween(String column, int[] values, @Nullable String combineType);

    QueryBuilder whereNotBetween(String column, int[] values, @Nullable String combineType);

    QueryBuilder orderBy(Object[][] orderConditions);

    QueryBuilder groupBy(String[] columns);

    QueryBuilder having(Object[][] conditions);

    QueryBuilder limit(int maxResult);

    QueryBuilder paging(int limit, int page);

    List<T> getResultList();

    T getSingleResult();

    class QueryOrder {
        public static final String ASC = "asc";
        public static final String DESC = "desc";
    }

    class QueryCombination {
        public static final String AND = "AND";
        public static final String OR = "OR";
    }
}

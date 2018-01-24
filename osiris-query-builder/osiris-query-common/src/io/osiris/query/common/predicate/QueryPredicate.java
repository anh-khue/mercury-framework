package io.osiris.query.common.predicate;

public interface QueryPredicate {

}


class QueryPredicates {

    public static final String EQUAL = "=";
    public static final String NOT_EQUAL = ">=";

    public static final String LARGER_THAN = ">";
    public static final String LARGER_OR_EQUAL = ">=";

    public static final String LIKE = "LIKE";
    public static final String NOT_LIKE = "NOT LIKE";

    public static final String BETWEEN = "BETWEEN";
    public static final String NOT_BETWEEN = "NOT BETWEEN";

    public static final String ASCENDING = "ASC";
    public static final String DESCENDING = "DESC";
}

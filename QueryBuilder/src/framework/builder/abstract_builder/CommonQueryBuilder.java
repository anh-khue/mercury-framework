package framework.builder.abstract_builder;

import com.sun.istack.internal.Nullable;
import framework.annotation.processor.ModelAnnotationProcessor;
import framework.model_mapping.model.Model;
import framework.model_mapping.model.ModelUtils;
import framework.util.DbUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class CommonQueryBuilder<T extends Model> implements QueryBuilder<T> {

    private Class<T> model;
    private static ModelAnnotationProcessor modelAnnotationProcessor;
    protected static StringBuilder query;

    protected static StringBuilder distinct = new StringBuilder("");
    protected static StringBuilder limit = new StringBuilder("");
    protected static StringBuilder columns = new StringBuilder("");
    protected static String table;
    protected static StringBuilder join = new StringBuilder("");
    protected static StringBuilder where = new StringBuilder("");
    protected static StringBuilder orderBy = new StringBuilder("");
    protected static StringBuilder groupBy = new StringBuilder("");
    protected static StringBuilder having = new StringBuilder("");
    protected static StringBuilder paging = new StringBuilder("");

    protected static Map<String, List> queryParams = new HashMap<>();

    protected static ArrayList<Object> parameters = new ArrayList<>();

    @Override
    public QueryBuilder distinct() {
        if (distinct.toString().equals("")) {
            distinct.append("DISTINCT ");
        }

        return this;
    }

    @Override
    public QueryBuilder table(Class model) {
        this.model = model;
        modelAnnotationProcessor = new ModelAnnotationProcessor(model);
        table = modelAnnotationProcessor.getTableName();
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

    @Override
    public QueryBuilder join(Object[][] joinConditions) {
        return null;
    }

    @Override
    public QueryBuilder where(Object[][] andConditions) {
        if (where.toString().equals("")) {
            where = new StringBuilder("WHERE ");
            where.append(andConditions[0][0])
                    .append(" ")
                    .append(andConditions[0][1])
                    .append(" ")
                    .append("?")
                    .append(" ");
            queryParams.get("where").add(andConditions[0][2]);
        } else {
            where.append("AND ")
                    .append(andConditions[0][0])
                    .append(" ")
                    .append(andConditions[0][1])
                    .append(" ")
                    .append("?")
                    .append(" ");
            queryParams.get("where").add(andConditions[0][2]);
        }

        if (andConditions.length > 1) {
            for (int i = 1; i < andConditions.length; i++) {
                where.append("AND ")
                        .append(" ")
                        .append(andConditions[i][0])
                        .append(" ")
                        .append(andConditions[i][1])
                        .append(" ")
                        .append("?")
                        .append(" ");
                queryParams.get("where").add(andConditions[i][2]);
            }
        }

        return this;
    }

    @Override
    public QueryBuilder orWhere(Object[][] orConditions) {
        if (where.toString().equals("")) {
            where = new StringBuilder("WHERE ");
            where.append(orConditions[0][0])
                    .append(" ")
                    .append(orConditions[0][1])
                    .append(" ")
                    .append("?")
                    .append(" ");
            queryParams.get("where").add(orConditions[0][2]);
        } else {
            where.append("OR ")
                    .append(orConditions[0][0])
                    .append(" ")
                    .append(orConditions[0][1])
                    .append(" ")
                    .append("?")
                    .append(" ");
            queryParams.get("where").add(orConditions[0][2]);
        }

        if (orConditions.length > 1) {
            for (int i = 1; i < orConditions.length; i++) {
                where.append("OR ")
                        .append(orConditions[i][0])
                        .append(" ")
                        .append(orConditions[i][1])
                        .append(" ")
                        .append("?")
                        .append(" ");
                queryParams.get("where").add(orConditions[i][2]);
            }
        }

        return this;
    }

    @Override
    public QueryBuilder whereBetween(String column, int[] values, @Nullable String combineType) {
        if (where.toString().equals("")) {
            where.append("WHERE ");
        } else {
            combineType = combineType == null ? QueryCombination.AND : combineType;
            where.append(combineType).append(" ");
        }
        where.append(column)
                .append(" BETWEEN ? AND ?")
                .append(" ");
        queryParams.get("where").add(values[0]);
        queryParams.get("where").add(values[1]);

        return this;
    }

    @Override
    public QueryBuilder whereNotBetween(String column, int[] values, @Nullable String combineType) {
        if (where.toString().equals("")) {
            where.append("WHERE ");
        } else {
            combineType = combineType == null ? QueryCombination.AND : combineType;
            where.append(combineType).append(" ");
        }
        where.append(column)
                .append(" NOT BETWEEN ? AND ?")
                .append(" ");
        queryParams.get("where").add(values[0]);
        queryParams.get("where").add(values[1]);

        return this;
    }

    @Override
    public QueryBuilder orderBy(Object[][] orderConditions) {
        if (orderBy.toString().equals("")) {
            orderBy.append("ORDER BY ");
        }
        orderBy.append(orderConditions[0][0])
                .append(" ")
                .append(orderConditions[0][1]);

        if (orderConditions.length > 1) {
            for (int i = 1; i < orderConditions.length; i++) {
                orderBy.append(", ")
                        .append(orderConditions[i][0])
                        .append(" ")
                        .append(orderConditions[i][1]);
            }
        }
        orderBy.append(" ");

        return this;
    }

    @Override
    public QueryBuilder groupBy(String[] columns) {
        if (groupBy.toString().equals("")) {
            groupBy.append("GROUP BY ");
        }
        groupBy.append(columns[0]);

        if (columns.length > 1) {
            for (int i = 1; i < columns.length; i++) {
                groupBy.append(", ")
                        .append(columns[0]);
            }
        }
        groupBy.append(" ");

        return this;
    }

    @Override
    public QueryBuilder having(Object[][] havingConditions) {
        return null;
    }

    @Override
    public abstract QueryBuilder limit(int maxResult);

    @Override
    public abstract QueryBuilder paging(int limit, int page);

    @Override
    public List getResultList() {
        this.build();
        List<Field> modelFields = modelAnnotationProcessor.getModelFields(model);

        List<T> result = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbUtils.makeConnection();
            System.out.println(query.toString());
            statement = connection.prepareStatement(query.toString());
            for (int i = 0; i < parameters.size(); i++) {
                statement.setString((i + 1), parameters.get(i).toString());
            }

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                T entity = model.newInstance();
                ModelUtils.setEntityFields(entity, modelFields, resultSet);
                result.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(connection, statement, resultSet);
        }

        return result;
    }

    @Override
    public T getSingleResult() {
        return null;
    }

    @Override
    public abstract QueryBuilder build();
}

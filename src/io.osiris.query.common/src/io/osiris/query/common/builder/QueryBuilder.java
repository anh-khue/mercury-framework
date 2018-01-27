package io.osiris.query.common.builder;

import io.osiris.data.connection.ConnectionFactory;
import io.osiris.data.jpa.Entity;
import io.osiris.data.jpa.binding.EntityBindingsFactory;
import io.osiris.data.jpa.binding.EntityDataBindings;
import io.osiris.query.common.Builder;
import io.osiris.query.tuple.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.osiris.data.common.binding.function.DataBindingHandler.fetchColumns;
import static io.osiris.data.common.binding.function.DataBindingHandler.fetchFields;
import static io.osiris.data.common.binding.function.DataBindingHandler.setFields;
import static io.osiris.query.common.builder.QueryFunctionHandler.pairHandler;

public abstract class QueryBuilder implements Builder {

    protected StringBuilder query;
    protected List<String> params = new ArrayList<>();

    protected Class<? extends Entity> entityClass;

    protected StringBuilder columns = new StringBuilder("");

    protected Distinct distinct = new Distinct();
    protected From from = new From();
    protected Where where = new Where();
    protected OrderBy orderBy = new OrderBy();
    protected GroupBy groupBy = new GroupBy();
    protected Having having = new Having();
    protected Paging paging = new Paging();
    protected Limit limit = new Limit();
    protected Join join = new Join();

    public QueryBuilder from(Class<? extends Entity> entityClass) {

        if (this.from.called) return this;

        columns.append(
                fetchColumns(entityClass)
                        .stream()
                        .collect(Collectors.joining(",")))
                .append(" ");

        this.entityClass = entityClass;

        EntityBindingsFactory bindingsFactory = new EntityBindingsFactory(entityClass);

        EntityDataBindings entityDataBindings = bindingsFactory.createDataBindings();
        this.from.command = new StringBuilder("FROM " + entityDataBindings.table() + " ");

        this.from.called = true;
        return this;
    }

    public QueryBuilder distinct() {
        if (!this.distinct.called) this.distinct.command = new StringBuilder("DISTINCT ");
        this.distinct.called = true;
        return this;
    }

    public QueryBuilder where(Triplet triplet) {
        this.where.process(triplet, null);
        return this;
    }

    public QueryBuilder where(Quadruplet quadruplet) {
        this.where.process(quadruplet, null);
        return this;
    }

    public QueryBuilder andWhere(Triplet triplet) {
        this.where.process(triplet, QueryCondition.AND);
        return this;
    }

    public QueryBuilder andWhere(Quadruplet quadruplet) {
        this.where.process(quadruplet, QueryCondition.AND);
        return this;
    }

    public QueryBuilder orWhere(Triplet triplet) {
        this.where.process(triplet, QueryCondition.OR);
        return this;
    }

    public QueryBuilder orWhere(Quadruplet quadruplet) {
        this.where.process(quadruplet, QueryCondition.OR);
        return this;
    }

    public QueryBuilder orderBy(Pair... pairs) {
        if (this.orderBy.called) return this;

        this.orderBy.command = new StringBuilder("ORDER BY ");
        this.orderBy.command
                .append(Arrays.stream(pairs)
                        .map(pairHandler::apply)
                        .collect(Collectors.joining(", ")));
        this.orderBy.command.append(" ");
        System.out.println(this.orderBy.command.toString());
        this.orderBy.called = true;
        return this;
    }

    public QueryBuilder groupBy(String... columns) {
        if (this.groupBy.called) return this;

        this.groupBy.command = new StringBuilder("GROUP BY ");
        this.groupBy.command.append(
                Arrays.stream(columns)
                        .collect(Collectors.joining(", ")))
                .append(" ");
        System.out.println(this.groupBy.command);
        this.groupBy.called = true;
        return this;
    }

    public QueryBuilder having(Triplet triplet) {
        this.where.process(triplet, null);
        return this;
    }

    public QueryBuilder having(Quadruplet quadruplet) {
        this.where.process(quadruplet, null);
        return this;
    }

    public QueryBuilder andHaving(Triplet triplet) {
        this.where.process(triplet, QueryCondition.AND);
        return this;
    }

    public QueryBuilder andHaving(Quadruplet quadruplet) {
        this.where.process(quadruplet, QueryCondition.AND);
        return this;
    }

    public QueryBuilder orHaving(Triplet triplet) {
        this.where.process(triplet, QueryCondition.OR);
        return this;
    }

    public QueryBuilder orHaving(Quadruplet quadruplet) {
        this.where.process(quadruplet, QueryCondition.OR);
        return this;
    }

    abstract public QueryBuilder limit(int limit);

    abstract public QueryBuilder paging(int itemPerPage, int page);

    abstract public QueryBuilder join(String table, String as, Triplet onTriplet);

    public List<? extends Entity> getResultList() {
        this.build();
        List<Field> entityFields = fetchFields(this.entityClass);

        List<Entity> entities = new ArrayList<>();

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(this.query.toString())) {

            System.out.println(query.toString());

            for (int i = 0; i < this.params.size(); i++) {
                statement.setString((i + 1), params.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Entity entity = entityClass.getConstructor().newInstance();
                setFields(entity, entityFields, resultSet);
                entities.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entities;
    }

    public Optional<? extends Entity> getSingleResult() {
        this.build();
        List<Field> entityFields = fetchFields(this.entityClass);

        Optional<? extends Entity> entity = Optional.empty();

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(this.query.toString())) {

            System.out.println(query.toString());

            for (int i = 0; i < this.params.size(); i++) {
                statement.setString((i + 1), params.get(i));
            }

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Entity entityInstance = entityClass.getConstructor().newInstance();
                setFields(entityInstance, entityFields, resultSet);
                entity = Optional.of(entityInstance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public abstract QueryBuilder build();
}


class Distinct extends QueryComponent {

}


class From extends QueryComponent {

}


class Where extends QueryCondition {

    protected void process(Tuple tuple, String disjunction) {
        super.process(tuple, disjunction, "WHERE");
    }
}


class OrderBy extends QueryComponent {

}


class GroupBy extends QueryComponent {

}


class Having extends QueryCondition {

    protected void process(Tuple tuple, String disjunction) {
        super.process(tuple, disjunction, "HAVING");
    }
}


class Limit extends QueryComponent {

    protected int maxResult;
}


class Paging extends QueryComponent {

    protected int itemPerPage;
    protected int page;
}


class Join extends QueryComponent {

}
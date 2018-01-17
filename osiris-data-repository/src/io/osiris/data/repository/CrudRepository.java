package io.osiris.data.repository;

import io.osiris.data.connection.ConnectionFactory;
import io.osiris.data.connection.xml.XmlConnectionFactory;
import io.osiris.data.jpa.Entity;
import io.osiris.data.jpa.binding.EntityBindingsFactory;
import io.osiris.data.jpa.binding.EntityDataBindings;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.osiris.data.common.binding.DataBindingHandler.setFields;

public abstract class CrudRepository<T extends Entity, R extends Serializable> implements JpaRepository {

    @SuppressWarnings("unchecked")
    private final Class<T> entityClass = (Class<T>) ((ParameterizedType) this.getClass()
            .getGenericSuperclass())
            .getActualTypeArguments()[0];

    @SuppressWarnings("unchecked")
    private final Class<R> idClass = (Class<R>) ((ParameterizedType) this.getClass()
            .getGenericSuperclass())
            .getActualTypeArguments()[1];

    private final ConnectionFactory connectionFactory = new XmlConnectionFactory();
    private final EntityBindingsFactory bindingsFactory = new EntityBindingsFactory(entityClass);

    private final EntityDataBindings dataBindings = bindingsFactory.createDataBindings();

    private final String table;
    private final List<String> columns;
    private final List<Field> fields;
    private final List<String> idColumns;

    public CrudRepository() {
        this.table = dataBindings.table();
        this.columns = dataBindings.columns();
        this.fields = dataBindings.fields();
        this.idColumns = dataBindings.idColumns();
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();

        String query = "SELECT * FROM " + table;

        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                T entity = entityClass.getConstructor().newInstance();
                setFields(entity, fields, resultSet);
                result.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Optional<T> findById(Serializable... ids) {
        Optional<T> entity = Optional.empty();

        String query = "SELECT * FROM " + table + " WHERE " +
                idColumns.stream()
                        .map(idColumn -> idColumn + " = ?")
                        .collect(Collectors.joining(" AND "));

        try (Connection connection = connectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            for (int i = 0; i < ids.length; i++) {
                if (ids[i] instanceof Integer) {
                    statement.setInt((i + 1), (Integer) ids[i]);
                } else {
                    statement.setLong((i + 1), (Long) ids[i]);
                }
            }

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                T result = entityClass.getConstructor().newInstance();
                setFields(result, fields, resultSet);
                entity = Optional.of(result);
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public void save(Entity entity) {

    }

    @Override
    public void remove(Serializable... id) {

    }

    public String update(Entity entity) {
        StringBuilder valuesTuple = new StringBuilder("");
        valuesTuple.append(
                columns
                        .stream()
                        .map(col -> col + " = ?")
                        .collect(Collectors.joining(","))
        );

        Map<String, Serializable> idMap = entity.idMap();

        StringBuilder idBuilder = new StringBuilder(idColumns.get(0))
                .append(" = ")
                .append(idMap.get(idColumns.get(0)));

        if (idColumns.size() > 1) {
            for (String column : idColumns.subList(1, idColumns.size())) {
                idBuilder.append(" AND ")
                        .append(column)
                        .append(" = ")
                        .append(idMap.get(column));
            }
        }

        System.out.println("UPDATE " + table + " SET " + valuesTuple + " WHERE " + idBuilder.toString());
        return "UPDATE " + table + " SET " + valuesTuple + " WHERE " + idBuilder.toString();
    }

    public String insert() {
        StringBuilder columns = new StringBuilder("");
        columns.append(this.columns
                .stream()
                .collect(Collectors.joining(",")));

        StringBuilder values = new StringBuilder("");
        values.append(this.columns
                .stream()
                .map(col -> "?")
                .collect(Collectors.joining(",")));

        return "INSERT INTO " + table + "(" + columns + ")" + " VALUES(" + values + ")";
    }
}

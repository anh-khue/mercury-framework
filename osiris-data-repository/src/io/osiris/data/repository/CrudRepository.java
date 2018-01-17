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

    public CrudRepository() {
        this.table = dataBindings.table();
        this.columns = dataBindings.columns();
        this.fields = dataBindings.fields();
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
    public Optional<? extends Entity> findById(Serializable[] id) {
        return Optional.empty();
    }

    @Override
    public void save(Entity entity) {

    }

    @Override
    public void remove(Serializable[] id) {

    }

    private String update(Entity entity) {
        StringBuilder valuesTuple = new StringBuilder("");
        valuesTuple.append(
                columns.subList(1, columns.size())
                        .stream()
                        .map(col -> col + " = ?")
                        .collect(Collectors.joining(","))
        );

//        return "UPDATE " + table + " SET " + valuesTuple + " WHERE id = " + entity.getId();
        return null;
    }

    private String insert() {
        StringBuilder columns = new StringBuilder("");
        columns.append(this.columns
                .subList(1, this.columns.size())
                .stream()
                .collect(Collectors.joining(",")));

        StringBuilder values = new StringBuilder("");
        values.append(this.columns
                .subList(1, this.columns.size())
                .stream()
                .map(col -> "?")
                .collect(Collectors.joining(",")));

        return "INSERT INTO " + table + "(" + columns + ")" + " VALUES(" + values + ")";
    }
}

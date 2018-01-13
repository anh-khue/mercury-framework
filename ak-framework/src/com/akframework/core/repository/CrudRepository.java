package com.akframework.core.repository;

import com.akframework.core.data.annotation.Column;
import com.akframework.core.data.annotation.processor.DataAnnotationProcessor;
import com.akframework.core.data.common.Entity;
import com.akframework.kotlin.data.function.EntityFunctions;
import com.akframework.kotlin.data.function.TableFunctions;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.akframework.core.connection.ConnectionManager.*;

public abstract class CrudRepository<T extends Entity> implements DataRepository {

    private String table;
    private List<String> columns;
    private List<Field> fields;

    private Class<T> entityClass;

    private DataAnnotationProcessor processor;

    @SuppressWarnings(value = "unchecked")
    private void initModel() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public CrudRepository() {
        initModel();
        processor = new DataAnnotationProcessor(entityClass);
        table = processor.getTable();
        columns = TableFunctions.getColumns(entityClass);
        fields = EntityFunctions.getFields(entityClass);
    }

    public List<T> findAll() {
        List<T> result = new ArrayList<>();

        String query = "SELECT * FROM " + table;

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                T entity = entityClass.newInstance();
                EntityFunctions.setFields(entity, fields, resultSet);
                result.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Optional<T> findById(int... id) {
        Optional<T> entity = Optional.empty();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM " + table + " WHERE ");
        if (id.length > 1) {
            List<String> modelCombineKeys = processor.getCombineKey();
            queryBuilder.append(modelCombineKeys.stream()
                    .map(key -> key + " = ?")
                    .collect(Collectors.joining(" AND ")));
        } else {
            queryBuilder.append("id = ?");
        }


        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {
            for (int i = 0; i < id.length; i++) {
                statement.setInt((i + 1), id[i]);
            }

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                T foundEntity = entityClass.newInstance();
                EntityFunctions.setFields(foundEntity, fields, resultSet);
                entity = Optional.of(foundEntity);
            }

            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return entity;
    }

    public void save(Entity entity) {
        String query = findById(entity.getId()).isPresent() ? update(entity) : insert();

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 1; i < fields.size(); i++) {
                Column column = fields.get(i).getAnnotation(Column.class);
                if (column != null) {
                    fields.get(i).setAccessible(true);
                    Object value = fields.get(i).get(entity);

                    statement.setString(
                            i,
                            value == null ? null : String.valueOf(value));
                }
            }

            if (statement.executeUpdate() > 0) {
                System.out.println("Save Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void remove(int... id) {
//        if (findById(id) == null) {
//            System.out.println("No entity found!");
//            return;
//        }

        if (!findById(id).isPresent()) {
            System.out.println("No entity found!");
            return;
        }

        StringBuilder queryBuilder = new StringBuilder("DELETE FROM " + table + " WHERE ");
        if (id.length > 1) {
            List<String> entityCombineKeys = processor.getCombineKey();
            queryBuilder.append(entityCombineKeys.stream()
                    .map(key -> key + " = ?")
                    .collect(Collectors.joining(" AND ")));
        } else {
            queryBuilder.append("id = ?");
        }

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < id.length; i++) {
                statement.setInt((i + 1), id[i]);
            }

            if (statement.executeUpdate() > 0) {
                System.out.println("Delete Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String update(Entity entity) {
        StringBuilder valuesTuple = new StringBuilder("");
        valuesTuple.append(
                columns.subList(1, columns.size())
                        .stream()
                        .map(col -> col + " = ?")
                        .collect(Collectors.joining(","))
        );

        return "UPDATE " + table + " SET " + valuesTuple + " WHERE id = " + entity.getId();
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

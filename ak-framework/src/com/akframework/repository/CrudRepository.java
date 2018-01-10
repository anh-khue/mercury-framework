package com.akframework.repository;

import com.akframework.data.annotation.Column;
import com.akframework.data.annotation.processor.TableAnnotationProcessor;
import com.akframework.data.mapping.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.akframework.EntityFunctions.*;
import static com.akframework.database.DatabaseConnectionManager.*;

public abstract class CrudRepository<T extends Entity> implements IRepository {

    private String table;
    private List<String> columns;
    private List<Field> fields;

    private Class<T> modelClass;

    private TableAnnotationProcessor processor;

    @SuppressWarnings(value = "unchecked")
    private void initModel() {
        this.modelClass = (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public CrudRepository() {
        initModel();
        processor = new TableAnnotationProcessor(modelClass);
        table = processor.getTable();
        columns = processor.getColumns(modelClass);
        fields = getFields(modelClass);
    }

    public List<T> getAll() throws SQLException {
        List<T> result = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = openConnection();

            String query = "SELECT * FROM " + table;
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                T entity = modelClass.newInstance();
                setFields(entity, fields, resultSet);
                result.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement, resultSet);
        }

        return result;
    }

    public T findById(int... id) throws SQLException {
        T entity = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = openConnection();

            String query;
            if (id.length > 1) {
                List<String> modelCombineKeys = processor.getCombineKey();
                StringBuilder combineKey = new StringBuilder("");
                combineKey.append(modelCombineKeys.stream()
                        .map(key -> key + " = ?")
                        .collect(Collectors.joining(" AND ")));

                query = "SELECT * FROM " + table + " WHERE " + combineKey;
                System.out.println(query);
                statement = connection.prepareStatement(query);
                for (int i = 0; i < id.length; i++) {
                    statement.setInt((i + 1), id[i]);
                }
            } else {
                query = "SELECT * FROM " + table + " WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id[0]);
            }

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = modelClass.newInstance();
                setFields(entity, fields, resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement, resultSet);
        }

        return entity;
    }

    public void save(T entity) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = openConnection();

            String query = findById(entity.getId()) != null ? updateQuery(entity) : addQuery();

            statement = connection.prepareStatement(query);

            int parameterIndex = 0;
            for (int i = 1; i < fields.size(); i++) {
                Column column = fields.get(i).getAnnotation(Column.class);
                if (column != null) {
                    fields.get(i).setAccessible(true);
                    statement.setString(++parameterIndex, String.valueOf(fields.get(i).get(entity)));
                }
            }

            if (statement.executeUpdate() > 0) {
                System.out.println("Save Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement);
        }
    }

    public void remove(int... id) throws SQLException {
        if (findById(id) == null) {
            System.out.println("No mapping found!");
            return;
        }
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = openConnection();

            StringBuilder query = new StringBuilder("DELETE FROM " + table + " WHERE ");
            if (id.length > 1) {
                List<String> modelCombineKeys = processor.getCombineKey();
                query.append(modelCombineKeys.stream()
                        .map(key -> key + " = ?")
                        .collect(Collectors.joining(" AND ")));
                System.out.println(query);
                statement = connection.prepareStatement(query.toString());
                for (int i = 0; i < id.length; i++) {
                    statement.setInt((i + 1), id[i]);
                }
            } else {
                query.append("id = ?");
                statement = connection.prepareStatement(query.toString());
                statement.setInt(1, id[0]);
            }

            if (statement.executeUpdate() > 0) {
                System.out.println("Delete Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement);
        }
    }

    private String updateQuery(T entity) {
        StringBuilder setValues = new StringBuilder("");
        setValues.append(columns
                .subList(1, columns.size())
                .stream()
                .map(col -> col + " = ?")
                .collect(Collectors.joining(",")));

        return "UPDATE " + table + " SET " + setValues + " WHERE id = " + entity.getId();
    }

    private String addQuery() {
        StringBuilder queryTableColumns = new StringBuilder("");
        queryTableColumns.append(columns
                .subList(1, columns.size())
                .stream()
                .collect(Collectors.joining(",")));

        StringBuilder queryRowParameters = new StringBuilder("");
        queryRowParameters.append(columns
                .subList(1, columns.size())
                .stream()
                .map(col -> "?")
                .collect(Collectors.joining(",")));

        return "INSERT INTO " + table + "(" + queryTableColumns + ")" + " VALUES(" + queryRowParameters + ")";
    }
}

package framework.model_mapping.repository;

import framework.model_mapping.annotation.model.Column;
import framework.model_mapping.annotation.processor.ModelAnnotationProcessor;
import framework.model_mapping.model.Model;
import app.util.DbUtils;
import framework.model_mapping.model.ModelUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CrudRepository<T extends Model> implements IRepository {
    private String tableName;
    private List<String> tableColumns;
    private List<Field> modelFields;

    private Class<T> modelClass;

    private ModelAnnotationProcessor modelAnnotationProcessor;

    @SuppressWarnings(value = "unchecked")
    private void initModel() {
        this.modelClass = (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public CrudRepository() {
        initModel();
        modelAnnotationProcessor = new ModelAnnotationProcessor(modelClass);
        tableName = modelAnnotationProcessor.getTableName();
        tableColumns = modelAnnotationProcessor.getTableColumns(modelClass);
        modelFields = modelAnnotationProcessor.getModelFields(modelClass);
    }

    public List<T> getAll() throws SQLException {
        List<T> result = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbUtils.makeConnection();

            String query = "SELECT * FROM " + tableName;
            statement = connection.prepareStatement(query);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                T entity = modelClass.newInstance();
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

    public T findById(int... id) throws SQLException {
        T entity = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbUtils.makeConnection();

            String query;
            if (id.length > 1) {
                List<String> modelCombineKeys = modelAnnotationProcessor.getCombineKey();
                StringBuilder combineKey = new StringBuilder("");
                combineKey.append(modelCombineKeys.stream()
                        .map(key -> key + " = ?")
                        .collect(Collectors.joining(" AND ")));

                query = "SELECT * FROM " + tableName + " WHERE " + combineKey;
                System.out.println(query);
                statement = connection.prepareStatement(query);
                for (int i = 0; i < id.length; i++) {
                    statement.setInt((i + 1), id[i]);
                }
            } else {
                query = "SELECT * FROM " + tableName + " WHERE id = ?";
                statement = connection.prepareStatement(query);
                statement.setInt(1, id[0]);
            }

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                entity = modelClass.newInstance();
                ModelUtils.setEntityFields(entity, modelFields, resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(connection, statement, resultSet);
        }

        return entity;
    }

    public void save(T entity) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DbUtils.makeConnection();

            String query = findById(entity.getId()) != null ? updateQuery(entity) : addQuery();

            statement = connection.prepareStatement(query);

            int parameterIndex = 0;
            for (int i = 1; i < modelFields.size(); i++) {
                Column column = modelFields.get(i).getAnnotation(Column.class);
                if (column != null) {
                    modelFields.get(i).setAccessible(true);
                    statement.setString(++parameterIndex, String.valueOf(modelFields.get(i).get(entity)));
                }
            }

            if (statement.executeUpdate() > 0) {
                System.out.println("Save Successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(connection, statement);
        }
    }

    public void remove(int... id) throws SQLException {
        if (findById(id) == null) {
            System.out.println("No entity found!");
            return;
        }
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DbUtils.makeConnection();

            StringBuilder query = new StringBuilder("DELETE FROM " + tableName + " WHERE ");
            if (id.length > 1) {
                List<String> modelCombineKeys = modelAnnotationProcessor.getCombineKey();
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
            DbUtils.closeConnection(connection, statement);
        }
    }

    private String updateQuery(T entity) {
        StringBuilder setValues = new StringBuilder("");
        setValues.append(tableColumns
                .subList(1, tableColumns.size())
                .stream()
                .map(col -> col + " = ?")
                .collect(Collectors.joining(",")));

        return "UPDATE " + tableName + " SET " + setValues + " WHERE id = " + entity.getId();
    }

    private String addQuery() {
        StringBuilder queryTableColumns = new StringBuilder("");
        queryTableColumns.append(tableColumns
                .subList(1, tableColumns.size())
                .stream()
                .collect(Collectors.joining(",")));

        StringBuilder queryRowParameters = new StringBuilder("");
        queryRowParameters.append(tableColumns
                .subList(1, tableColumns.size())
                .stream()
                .map(col -> "?")
                .collect(Collectors.joining(",")));

        return "INSERT INTO " + tableName + "(" + queryTableColumns + ")" + " VALUES(" + queryRowParameters + ")";
    }
}

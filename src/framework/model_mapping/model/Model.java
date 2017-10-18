package framework.model_mapping.model;

import framework.annotation.model.Column;
import framework.annotation.processor.ModelAnnotationProcessor;
import framework.util.DbUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Model implements Serializable {
    @Column(value = "id")
    private int id;

    private ModelAnnotationProcessor modelAnnotationProcessor = new ModelAnnotationProcessor(this.getClass());

    public int getId() {
        return id;
    }

    protected Model manyToOne() {
        Model entity = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

            String tableName = modelAnnotationProcessor.getTableName();

            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> manyToOneInformation = modelAnnotationProcessor.getManyToOneInformation(method);

            connection = DbUtils.makeConnection();

            String query = "SELECT * " +
                    "FROM " + manyToOneInformation.get("referencedTable") + " " +
                    "WHERE id = (SELECT " + manyToOneInformation.get("referencedColumn") + " FROM " + tableName + " WHERE id = ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Class<?> returnType = method.getReturnType();
                entity = (Model) returnType.newInstance();
                List<Field> modelFields = modelAnnotationProcessor.getModelFields(returnType);
                ModelUtils.setEntityFields(entity, modelFields, resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(connection, statement, resultSet);
        }
        return entity;
    }

    protected List<? extends Model> oneToMany() {
        List<Model> entityList = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

            String tableName = modelAnnotationProcessor.getTableName();

            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> oneToManyInformation = modelAnnotationProcessor.getOneToManyInformation(method);

            connection = DbUtils.makeConnection();

            String query = "SELECT * " +
                    "FROM " + oneToManyInformation.get("table") + " " +
                    "WHERE " + oneToManyInformation.get("column") + " = (SELECT id FROM " + tableName + " WHERE id = ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId());

            resultSet = statement.executeQuery();

            entityList = new ArrayList<>();

            Type listType = method.getGenericReturnType();
            Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

            Class<?> elementClass = Class.forName(elementType.getTypeName());

            while (resultSet.next()) {
                Model entity = (Model) elementClass.newInstance();
                List<Field> modelFields = modelAnnotationProcessor.getModelFields(elementClass);
                ModelUtils.setEntityFields(entity, modelFields, resultSet);
                entityList.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeConnection(connection, statement, resultSet);
        }
        return entityList;
    }
}

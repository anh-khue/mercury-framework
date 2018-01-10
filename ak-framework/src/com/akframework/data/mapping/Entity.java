package com.akframework.data.mapping;

import com.akframework.data.annotation.Column;
import com.akframework.data.annotation.processor.TableAnnotationProcessor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.akframework.EntityFunctions.*;
import static com.akframework.database.DatabaseConnectionManager.*;

public abstract class Entity implements Serializable {

    private TableAnnotationProcessor processor = new TableAnnotationProcessor(this.getClass());

    @Column(value = "id")
    private int id;

    public int getId() {
        return id;
    }

    protected Entity manyToOne() {
        Entity entity = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

            String tableName = processor.getTable();

            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> manyToOneInformation = processor.getManyToOneInformation(method);

            connection = openConnection();

            String query = "SELECT * " +
                    "FROM " + manyToOneInformation.get("referencedTable") + " " +
                    "WHERE id = (SELECT " + manyToOneInformation.get("referencedColumn") + " FROM " + tableName + " WHERE id = ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Class<?> returnType = method.getReturnType();
//                entity = (Entity) returnType.newInstance();
                entity = (Entity) returnType.getConstructor().newInstance();
                List<Field> modelFields = getFields(returnType);
                setFields(entity, modelFields, resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return entity;
    }

    protected List<? extends Entity> oneToMany() {
        List<Entity> entityList = null;

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> oneToManyInformation = processor.getOneToManyInformation(method);

            String table = processor.getTable();

            connection = openConnection();

            String query = "SELECT * " +
                    "FROM " + oneToManyInformation.get("table") + " " +
                    "WHERE " + oneToManyInformation.get("column") + " = (SELECT id FROM " + table + " WHERE id = ?)";
            statement = connection.prepareStatement(query);
            statement.setInt(1, this.getId());

            resultSet = statement.executeQuery();

            entityList = new ArrayList<>();

            Type listType = method.getGenericReturnType();
            Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

            Class<?> elementClass = Class.forName(elementType.getTypeName());

            while (resultSet.next()) {
//                Entity entity = (Entity) elementClass.newInstance();
                Entity entity = (Entity) elementClass.getConstructor().newInstance();
                List<Field> modelFields = getFields(elementClass);
                setFields(entity, modelFields, resultSet);
                entityList.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection, statement, resultSet);
        }
        return entityList;
    }
}
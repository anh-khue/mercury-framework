package com.akframework.core.data.common;

import com.akframework.core.data.annotation.Column;
import com.akframework.core.data.annotation.processor.DataAnnotationProcessor;
import com.akframework.kotlin.data.function.EntityFunctions;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.akframework.core.connection.ConnectionManager.*;

public abstract class Entity implements Serializable {

    @Column(value = "id")
    private int id;

    public int getId() {
        return id;
    }

    protected Entity manyToOne() throws SQLException {
        DataAnnotationProcessor processor = EntityFunctions.getDataAnnotationProcessor(this.getClass());

        Entity entity = null;

        String tableName = processor.getTable();

        String query = "SELECT * " +
                "FROM ? " +
                "WHERE id = (SELECT ? FROM " + tableName + " WHERE id = ?)";

        ResultSet resultSet = null;

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> manyToOneMap = EntityFunctions.getManyToOneMap(method);

            statement.setString(1, manyToOneMap.get("referencedTable"));
            statement.setString(2, manyToOneMap.get("column"));
            statement.setInt(3, this.getId());

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Class<?> returnType = method.getReturnType();
//                entity = (Entity) returnType.newInstance();
                entity = (Entity) returnType.getConstructor().newInstance();
                List<Field> fieldList = EntityFunctions.getFields(returnType);
                EntityFunctions.setFields(entity, fieldList, resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
        }

        return entity;
    }

    protected List<? extends Entity> oneToMany() throws SQLException {
        DataAnnotationProcessor processor = EntityFunctions.getDataAnnotationProcessor(this.getClass());

        List<Entity> entityList = null;

        String table = processor.getTable();

        String query = "SELECT * " +
                "FROM ? " +
                "WHERE ? = (SELECT id FROM " + table + " WHERE id = ?)";

        ResultSet resultSet = null;

        try (Connection connection = openConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> oneToManyMap = EntityFunctions.getOneToManyMap(method);


            statement.setString(1, oneToManyMap.get("table"));
            statement.setString(2, oneToManyMap.get("referenceColumn"));
            statement.setInt(3, this.getId());

            resultSet = statement.executeQuery();

            entityList = new ArrayList<>();

            Type listType = method.getGenericReturnType();
            Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

            Class<?> elementClass = Class.forName(elementType.getTypeName());

            while (resultSet.next()) {
//                Entity entity = (Entity) elementClass.newInstance();
                Entity entity = (Entity) elementClass.getConstructor().newInstance();
                List<Field> fieldList = EntityFunctions.getFields(elementClass);
                EntityFunctions.setFields(entity, fieldList, resultSet);
                entityList.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) resultSet.close();
        }

        return entityList;
    }
}
package com.akframework.core.data.common;

import com.akframework.core.data.annotation.Column;
import com.akframework.core.data.annotation.processor.DataAnnotationProcessor;
import com.akframework.kotlin.data.function.EntityFunctions;

import java.io.Serializable;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.akframework.core.connection.ConnectionManager.*;

public abstract class Entity implements Serializable {

    @Column(value = "id")
    private int id;

    public int getId() {
        return id;
    }

    protected Optional<? extends Entity> manyToOne() {

        Optional<Entity> entity = Optional.empty();

        try {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> manyToOneMap = EntityFunctions.scanManyToOne(method);

            DataAnnotationProcessor processor = EntityFunctions.createProcessor(this.getClass());
            String tableName = processor.getTable();

            String query = "SELECT * " +
                    "FROM " + manyToOneMap.get("referencedTable") + " " +
                    "WHERE id = (SELECT " + manyToOneMap.get("column") + " FROM " + tableName + " WHERE id = ?)";

            try (Connection connection = openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, this.getId());

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Class<?> returnType = method.getReturnType();
                    Entity entityInstance = (Entity) returnType.getConstructor().newInstance();
                    List<Field> fieldList = EntityFunctions.loadFields(returnType);
                    EntityFunctions.setFields(entityInstance, fieldList, resultSet);
                    entity = Optional.of(entityInstance);
                }

                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return entity;
    }

    protected List<? extends Entity> oneToMany() {

        List<Entity> entityList = new ArrayList<>();

        try {
            String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

            Method method = this.getClass().getDeclaredMethod(methodName);
            Map<String, String> oneToManyMap = EntityFunctions.scanOneToMany(method);

            DataAnnotationProcessor processor = EntityFunctions.createProcessor(this.getClass());
            String table = processor.getTable();

            String query = "SELECT * " +
                    "FROM " + oneToManyMap.get("table") + " " +
                    "WHERE " + oneToManyMap.get("referenceColumn") + " = (SELECT id FROM " + table + " WHERE id = ?)";

            try (Connection connection = openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, this.getId());

                ResultSet resultSet = statement.executeQuery();

                Type listType = method.getGenericReturnType();
                Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

                Class<?> elementClass = Class.forName(elementType.getTypeName());

                while (resultSet.next()) {
                    Entity entity = (Entity) elementClass.getConstructor().newInstance();
                    List<Field> fieldList = EntityFunctions.loadFields(elementClass);
                    EntityFunctions.setFields(entity, fieldList, resultSet);
                    entityList.add(entity);
                }
                resultSet.close();
            } catch (InstantiationException | InvocationTargetException |
                    SQLException | IllegalAccessException |
                    ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return entityList;
    }
}
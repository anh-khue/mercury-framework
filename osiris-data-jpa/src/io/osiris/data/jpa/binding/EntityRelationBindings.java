package io.osiris.data.jpa.binding;

import io.osiris.data.common.binding.RelationBindings;
import io.osiris.data.connection.ConnectionProvider;
import io.osiris.data.connection.xml.XmlConnection;
import io.osiris.data.jpa.Entity;

import java.io.Serializable;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.osiris.data.common.binding.DataBindingHandler.setFields;
import static io.osiris.data.common.binding.RelationBindingHandler.fetchManyToOne;
import static io.osiris.data.common.binding.RelationBindingHandler.fetchOneToMany;

public class EntityRelationBindings implements RelationBindings {

    private final Class<? extends Entity> entityClass;
    private final ConnectionProvider connectionProvider;
    private final EntityDataBindings entityDataBindings;

    EntityRelationBindings(Class<? extends Entity> entityClass) {
        this.entityClass = entityClass;
        this.entityDataBindings = dataBindings();
        this.connectionProvider = new XmlConnection();
    }

    @Override
    public EntityDataBindings dataBindings() {
        return new EntityDataBindings(entityClass);
    }

    @Override
    public Optional<? extends Entity> manyToOne(Serializable entityId) {
        Optional<? extends Entity> entity = Optional.empty();

        try {
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            Method method = entityClass.getDeclaredMethod(methodName);
            Map<String, String> manyToOneMap = fetchManyToOne(method);

            String table = entityDataBindings.table();

            String query = "SELECT * " +
                    "FROM " + manyToOneMap.get("referencedTable") + " " +
                    "WHERE id = (SELECT " + manyToOneMap.get("column") + " FROM " + table + " WHERE id = ?)";

            try (Connection connection = connectionProvider.openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                if (entityId instanceof Integer) {
                    statement.setInt(1, (Integer) entityId);
                } else {
                    statement.setLong(1, (Long) entityId);
                }

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Class<?> returnType = method.getReturnType();
                    Entity entityInstance = (Entity) returnType.getConstructor().newInstance();
                    EntityDataBindings dataBindings = new EntityDataBindings((Class<? extends Entity>) returnType);
                    List<Field> fieldList = dataBindings.fields();
                    setFields(entityInstance, fieldList, resultSet);
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

    @Override
    public List<? extends Entity> oneToMany(Serializable entityId) {
        List<Entity> entityList = new ArrayList<>();

        try {
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            Method method = entityClass.getDeclaredMethod(methodName);
            Map<String, String> oneToManyMap = fetchOneToMany(method);

            String table = entityDataBindings.table();

            String query = "SELECT * " +
                    "FROM " + oneToManyMap.get("value") + " " +
                    "WHERE " + oneToManyMap.get("referenceColumn") + " = (SELECT id FROM " + table + " WHERE id = ?)";

            try (Connection connection = connectionProvider.openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                if (entityId instanceof Integer) {
                    statement.setInt(1, (Integer) entityId);
                } else {
                    statement.setLong(1, (Long) entityId);
                }

                ResultSet resultSet = statement.executeQuery();

                Type listType = method.getGenericReturnType();
                Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

                Class<?> elementClass = Class.forName(elementType.getTypeName());

                while (resultSet.next()) {
                    Entity entity = (Entity) elementClass.getConstructor().newInstance();
                    EntityDataBindings dataBindings = new EntityDataBindings((Class<? extends Entity>) elementClass);
                    List<Field> fieldList = dataBindings.fields();
                    setFields(entity, fieldList, resultSet);
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
package com.osiris.data.jpa.relation;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.connection.ConnectionFactory;
import com.osiris.data.connection.xml.XmlConnectionFactory;
import com.osiris.data.jpa.Entity;
import com.osiris.data.jpa.binding.JpaEntityBindings;
import com.osiris.data.orm.binding.DataBindingHandler;
import com.osiris.data.orm.relation.RelationBindings;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.osiris.data.orm.relation.RelationBindingHandler.fetchManyToOne;
import static com.osiris.data.orm.relation.RelationBindingHandler.fetchOneToMany;

public class JpaRelationBindings implements RelationBindings {

    private final Class<? extends DTO> entityClass;
    private final ConnectionFactory connectionFactory;
    private final JpaEntityBindings bindings;

    public JpaRelationBindings(Class<? extends DTO> entityClass) {
        this.entityClass = entityClass;
        this.bindings = DTOBindings();
        this.connectionFactory = new XmlConnectionFactory();
    }

    @Override
    public JpaEntityBindings DTOBindings() {
        return new JpaEntityBindings(entityClass);
    }

    @Override
    public Optional<? extends Entity> manyToOne(int entityId) {
        Optional<? extends Entity> entity = Optional.empty();

        try {
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            Method method = entityClass.getDeclaredMethod(methodName);
            Map<String, String> manyToOneMap = fetchManyToOne(method);

            String table = bindings.table();

            String query = "SELECT * " +
                    "FROM " + manyToOneMap.get("referencedTable") + " " +
                    "WHERE id = (SELECT " + manyToOneMap.get("column") + " FROM " + table + " WHERE id = ?)";

            try (Connection connection = connectionFactory.openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, entityId);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Class<?> returnType = method.getReturnType();
                    Entity entityInstance = (Entity) returnType.getConstructor().newInstance();
                    List<Field> fieldList = bindings.fields();
                    DataBindingHandler.setFields(entityInstance, fieldList, resultSet);
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
    public List<? extends Entity> oneToMany(int entityId) {
        List<Entity> entityList = new ArrayList<>();

        try {
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            Method method = entityClass.getDeclaredMethod(methodName);
            Map<String, String> oneToManyMap = fetchOneToMany(method);

            String table = bindings.table();

            String query = "SELECT * " +
                    "FROM " + oneToManyMap.get("table") + " " +
                    "WHERE " + oneToManyMap.get("referenceColumn") + " = (SELECT id FROM " + table + " WHERE id = ?)";

            try (Connection connection = connectionFactory.openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, entityId);

                ResultSet resultSet = statement.executeQuery();

                Type listType = method.getGenericReturnType();
                Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

                Class<?> elementClass = Class.forName(elementType.getTypeName());

                while (resultSet.next()) {
                    Entity entity = (Entity) elementClass.getConstructor().newInstance();
                    List<Field> fieldList = bindings.fields();
                    DataBindingHandler.setFields(entity, fieldList, resultSet);
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

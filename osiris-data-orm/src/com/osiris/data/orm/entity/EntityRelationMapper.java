package com.osiris.data.orm.entity;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.common.orm.DataMapper;
import com.osiris.data.connection.ConnectionFactory;
import com.osiris.data.connection.xml.XmlConnectionFactory;
import com.osiris.data.orm.relation.RelationMapper;

import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EntityRelationMapper implements RelationMapper {

    private final Class<? extends DTO> entityClass;
    private final ConnectionFactory connectionFactory;

    public EntityRelationMapper(Class<? extends DTO> entityClass) {
        this.entityClass = entityClass;
        this.connectionFactory = new XmlConnectionFactory();
    }

    @Override
    public DataMapper dataMapper() {
        return new EntityDataMapper(entityClass);
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public Optional<? extends DTO> manyToOne() {
        Optional<? extends DTO> entity = Optional.empty();

        try {
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            Method method = entityClass.getDeclaredMethod(methodName);
            Map<String, String> manyToOneMap = RelationScanner.scanManyToOne(method);

            String table = dataMapper().table();

            String query = "SELECT * " +
                    "FROM " + manyToOneMap.get("referencedTable") + " " +
                    "WHERE id = (SELECT " + manyToOneMap.get("column") + " FROM " + table + " WHERE id = ?)";

            try (Connection connection = connectionFactory.openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, this.getId());

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    Class<?> returnType = method.getReturnType();
                    DTO entityInstance = (DTO) returnType.getConstructor().newInstance();
                    List<Field> fieldList = dataMapper().fields();
                    DataTransferHandler.setFields(entityInstance, fieldList, resultSet);
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
    public List<? extends DTO> oneToMany() {
        List<DTO> entityList = new ArrayList<>();

        try {
            String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();

            Method method = entityClass.getDeclaredMethod(methodName);
            Map<String, String> oneToManyMap = RelationScanner.scanOneToMany(method);

            String table = dataMapper().table();

            String query = "SELECT * " +
                    "FROM " + oneToManyMap.get("table") + " " +
                    "WHERE " + oneToManyMap.get("referenceColumn") + " = (SELECT id FROM " + table + " WHERE id = ?)";

            try (Connection connection = connectionFactory.openConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                statement.setInt(1, this.getId());

                ResultSet resultSet = statement.executeQuery();

                Type listType = method.getGenericReturnType();
                Type elementType = ((ParameterizedType) listType).getActualTypeArguments()[0];

                Class<?> elementClass = Class.forName(elementType.getTypeName());

                while (resultSet.next()) {
                    DTO entity = (DTO) elementClass.getConstructor().newInstance();
                    List<Field> fieldList = dataMapper().fields();
                    DataTransferHandler.setFields(entity, fieldList, resultSet);
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

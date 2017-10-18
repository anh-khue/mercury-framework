package framework.model_mapping.model;

import framework.annotation.model.Column;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ModelUtils implements Serializable {
    public static void setEntityFields(Model entity, List<Field> modelFields, ResultSet resultSet)
            throws SQLException, IllegalAccessException {
        for (Field field : modelFields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                field.setAccessible(true);
                Object value;
                Class<?> fieldType = field.getType();
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    value = resultSet.getInt(column.value());
                } else if (fieldType.equals(String.class)) {
                    value = resultSet.getString(column.value());
                } else if (fieldType.equals(boolean.class) || fieldType.equals(Boolean.class)) {
                    value = resultSet.getBoolean(column.value());
                } else if (fieldType.equals(Timestamp.class)) {
                    value = resultSet.getTimestamp(column.value());
                } else if (fieldType.equals(double.class) || fieldType.equals(Double.class)) {
                    value = resultSet.getDouble(column.value());
                } else {
                    value = resultSet.getString(column.value());
                }
                field.set(entity, value);
            }
        }
    }
}

package com.akframework.data.annotation.processor;

import com.akframework.data.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class TableAnnotationProcessor implements Serializable {
    private Class<?> model;

    public TableAnnotationProcessor(Class<?> model) {
        this.model = model;
    }

    public String getTable() {
        return model.getAnnotation(Table.class).table();
    }

    public List<String> getColumns(Class<?> model) {
        List<String> columns = new ArrayList<>();

        if (model.getSuperclass() != null) {
            columns.addAll(getColumns(model.getSuperclass()));
        }

        Field[] fields = model.getDeclaredFields();
        for (Field field : fields) {
            Column tableColumn = field.getAnnotation(Column.class);
            if (tableColumn != null) {
                columns.add(tableColumn.value());
            }
        }

        return columns;
    }

    public List<Field> getFields(Class<?> model) {
        List<Field> entityFields = new ArrayList<>();

        if (model.getSuperclass() != null) {
            entityFields.addAll(getFields(model.getSuperclass()));
        }

        Field[] fields = model.getDeclaredFields();
        entityFields.addAll(Arrays.asList(fields));

        return entityFields;
    }

    public Map<String, String> getManyToOneInformation(Method method) {
        Map<String, String> result = new HashMap<>();

        ManyToOne annotation = method.getAnnotation(ManyToOne.class);

        result.put("referencedTable", annotation.referencedTable());
        result.put("referencedColumn", annotation.column());
        return result;
    }

    public Map<String, String> getOneToManyInformation(Method method) {
        Map<String, String> result = new HashMap<>();

        OneToMany annotation = method.getAnnotation(OneToMany.class);

        result.put("table", annotation.table());
        result.put("column", annotation.column());

        return result;
    }

    public List<String> getCombineKey() {
        List<String> combineKeys = new ArrayList<>();

        Field[] fields = model.getDeclaredFields();
        for (Field field : fields) {
            CombineKey combineKey = field.getAnnotation(CombineKey.class);
            Column tableColumn = field.getAnnotation(Column.class);
            if (combineKey != null && tableColumn != null) {
                combineKeys.add(tableColumn.value());
            }
        }

        return combineKeys;
    }
}

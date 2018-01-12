package com.akframework.core.data.annotation.processor;

import com.akframework.core.data.annotation.Column;
import com.akframework.core.data.annotation.CombineKey;
import com.akframework.core.data.annotation.Table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataAnnotationProcessor implements Serializable {

    private Class<?> entityClass;

    public DataAnnotationProcessor(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public String getTable() {
        return entityClass.getAnnotation(Table.class).table();
    }

    public List<String> getCombineKey() {
        Field[] fields = entityClass.getDeclaredFields();


        //        for (Field field : fields) {
//            CombineKey combineKey = field.getAnnotation(CombineKey.class);
//            Column tableColumn = field.getAnnotation(Column.class);
//            if (combineKey != null && tableColumn != null) {
//                combineKeys.add(tableColumn.value());
//            }
//        }

        return Arrays.stream(fields)
                .filter(field -> field.getAnnotation(CombineKey.class) != null
                        && field.getAnnotation(Column.class) != null)
                .map(field -> field.getAnnotation(Column.class).value())
                .collect(Collectors.toList());
    }
}

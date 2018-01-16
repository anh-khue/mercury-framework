package com.osiris.data.jpa.common;

import com.osiris.data.common.annotation.Column;
import com.osiris.data.common.annotation.CombineKey;
import com.osiris.data.common.annotation.Table;
import com.osiris.data.common.binding.DataBindings;
import com.osiris.data.common.dto.DTO;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDataBindings implements DataBindings {

    private final Class<? extends DTO> entityClass;

    public EntityDataBindings(Class<? extends DTO> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String table() {
        return entityClass.getAnnotation(Table.class).table();
    }

    @Override
    public List<String> primaryKey() {
        Field[] fields = entityClass.getDeclaredFields();

        return Arrays.stream(fields)
                .filter(field -> field.getAnnotation(CombineKey.class) != null
                        && field.getAnnotation(Column.class) != null)
                .map(field -> field.getAnnotation(Column.class).value())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> columns() {
        return com.osiris.data.common.handler.DataBindingHandler.fetchColumns(entityClass);
    }

    @Override
    public List<Field> fields() {
        return com.osiris.data.common.handler.DataBindingHandler.fetchFields(entityClass);
    }
}

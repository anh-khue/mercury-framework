package com.osiris.data.jpa.binding;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.orm.annotation.Column;
import com.osiris.data.orm.annotation.CombineKey;
import com.osiris.data.orm.annotation.Table;
import com.osiris.data.orm.binding.DTOBindings;
import com.osiris.data.orm.binding.DataBindingHandler;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JpaEntityBindings implements DTOBindings {

    private final Class<? extends DTO> entityClass;

    public JpaEntityBindings(Class<? extends DTO> entityClass) {
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
        return DataBindingHandler.fetchColumns(entityClass);
    }

    @Override
    public List<Field> fields() {
        return DataBindingHandler.fetchFields(entityClass);
    }
}

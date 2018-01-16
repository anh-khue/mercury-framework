package com.osiris.data.jpa.common;

import com.osiris.data.common.binding.DTOBindingsFactory;
import com.osiris.data.common.dto.DTO;

public class EntityBindingsFactory implements DTOBindingsFactory {

    private final Class<? extends DTO> entityClass;

    public EntityBindingsFactory(Class<? extends DTO> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public EntityDataBindings createDataBindings() {
        return new EntityDataBindings(entityClass);
    }

    @Override
    public EntityRelationBindings createRelationBindings() {
        return new EntityRelationBindings(entityClass);
    }
}

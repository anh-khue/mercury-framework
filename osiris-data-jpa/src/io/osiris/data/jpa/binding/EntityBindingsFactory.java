package io.osiris.data.jpa.binding;

import io.osiris.data.common.binding.DTOBindingsFactory;
import io.osiris.data.jpa.Entity;

public class EntityBindingsFactory implements DTOBindingsFactory {

    private final Class<? extends Entity> entityClass;

    public EntityBindingsFactory(Class<? extends Entity> entityClass) {
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

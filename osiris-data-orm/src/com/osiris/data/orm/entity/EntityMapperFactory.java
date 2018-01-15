package com.osiris.data.orm.entity;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.common.orm.DataMapper;
import com.osiris.data.common.orm.DataMapperFactory;

public class EntityMapperFactory implements DataMapperFactory {

    private final Class<? extends DTO> entityClass;

    public EntityMapperFactory(Class<? extends DTO> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public DataMapper createDataMapper() {
        return new EntityDataMapper(entityClass);
    }

}

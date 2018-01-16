package com.osiris.data.jpa.binding;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.orm.binding.DTOBindings;
import com.osiris.data.orm.binding.DTOBindingsFactory;

public class JpaEntityBindingsFactory implements DTOBindingsFactory {

    private final Class<? extends DTO> entityClass;

    public JpaEntityBindingsFactory(Class<? extends DTO> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public DTOBindings createDTOBindings() {
        return new JpaEntityBindings(entityClass);
    }
}

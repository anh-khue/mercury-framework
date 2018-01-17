package io.osiris.data.jpa;

import io.osiris.data.common.binding.DataBindingHandler;
import io.osiris.data.jpa.binding.EntityBindingsFactory;
import io.osiris.data.jpa.binding.EntityDataBindings;
import io.osiris.data.jpa.binding.EntityRelationBindings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Entity implements JpaDTO {

    private final EntityDataBindings dataBindings;
    private final EntityRelationBindings relationBindings;
    private final List<String> idColumns;

    protected Entity() {
        EntityBindingsFactory bindingsFactory = new EntityBindingsFactory(this.getClass());
        this.dataBindings = bindingsFactory.createDataBindings();
        this.relationBindings = bindingsFactory.createRelationBindings();
        this.idColumns = dataBindings.idColumns();
    }

    public Map<String, Serializable> idMap() {
        Map<String, Serializable> idMap = new HashMap<>();

        List idValues = DataBindingHandler.fetchIds(this.getClass(), this);

        for (int i = 0; i < idColumns.size(); i++) {
            idMap.put(idColumns.get(i), (Serializable) idValues.get(i));
        }

        return idMap;
    }

    public Optional<? extends Entity> manyToOne() {
//        TODO: Manage Entity's IDs
        return relationBindings.manyToOne(-1);
    }

    public List<? extends Entity> oneToMany() {
//        TODO: Manage Entity 's IDs
        return relationBindings.oneToMany(-1);
    }
}

package com.osiris.data.jpa;

import com.osiris.data.common.annotation.Column;
import com.osiris.data.jpa.common.EntityBindingsFactory;
import com.osiris.data.jpa.common.EntityRelationBindings;

import java.util.List;
import java.util.Optional;

public abstract class Entity implements JpaDTO {

    private final EntityRelationBindings relationBindings;

    protected Entity() {
        EntityBindingsFactory bindingsFactory = new EntityBindingsFactory(this.getClass());
        this.relationBindings = bindingsFactory.createRelationBindings();
    }

    @Column("id")
    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    protected Optional<? extends Entity> manyToOne() {
        return relationBindings.manyToOne(this.id);
    }

    protected List<? extends Entity> oneToMany() {
        return relationBindings.oneToMany(this.id);
    }
}

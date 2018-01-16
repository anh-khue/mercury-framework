package com.osiris.data.jpa;

import com.osiris.data.jpa.binding.JpaEntityBindingsFactory;
import com.osiris.data.jpa.relation.JpaRelationBindings;
import com.osiris.data.orm.annotation.Column;
import com.osiris.data.orm.binding.DTOBindingsFactory;

import java.util.List;
import java.util.Optional;

public abstract class Entity implements JpaDTO {

    @Column("id")
    private int id;

    @Override
    public int getId() {
        return this.id;
    }

    protected Optional<? extends Entity> manyToOne() {
        DTOBindingsFactory bindingsFactory = new JpaEntityBindingsFactory(this.getClass());
        JpaRelationBindings relationBindings = (JpaRelationBindings) bindingsFactory.createDTOBindings();

        return relationBindings.manyToOne(this.id);
    }

    protected List<? extends Entity> oneToMany() {
        DTOBindingsFactory bindingsFactory = new JpaEntityBindingsFactory(this.getClass());
        JpaRelationBindings relationBindings = (JpaRelationBindings) bindingsFactory.createDTOBindings();

        return relationBindings.oneToMany(this.id);
    }
}

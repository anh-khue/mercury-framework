package com.osiris.data;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.orm.relation.RelationMapper;
import com.osiris.data.orm.annotation.Column;
import com.osiris.data.orm.entity.EntityRelationMapper;

import java.util.List;
import java.util.Optional;

public abstract class Entity implements DTO {

    private final RelationMapper relationFactory;

    @Column()
    private int id;

    protected Entity() {
        this.relationFactory = new EntityRelationMapper(this.getClass(), dataMapper);
    }

    public int getId() {
        return this.id;
    }

    protected Optional manyToOne() {
        return relationFactory.manyToOne();
    }

    protected List oneToMany() {
        return relationFactory.oneToMany();
    }

}
package com.osiris.data.orm.relation;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.orm.binding.DTOBindings;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface RelationBindings<T extends DTO, R extends DTOBindings> extends Serializable {

    R DTOBindings();

    Optional<T> manyToOne(int dtoId);

    List<T> oneToMany(int dtoId);
}

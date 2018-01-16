package com.osiris.data.common.binding;

import com.osiris.data.common.dto.DTO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface RelationBindings extends Serializable {

    DataBindings dataBindings();

    Optional<? extends DTO> manyToOne(int dtoId);

    List<? extends DTO> oneToMany(int dtoId);
}

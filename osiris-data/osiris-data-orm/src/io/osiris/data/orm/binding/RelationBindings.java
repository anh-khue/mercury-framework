package io.osiris.data.orm.binding;

import io.osiris.data.common.dto.DTO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Deprecated
public interface RelationBindings extends Serializable {

    DataBindings dataBindings();

    Optional<? extends DTO> manyToOne(int dtoId);

    List<? extends DTO> oneToMany(int dtoId);
}

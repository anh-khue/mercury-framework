package io.osiris.data.common.binding;

import io.osiris.data.common.dto.DTO;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface RelationBindings extends Serializable {

    DataBindings dataBindings();

    Optional<? extends DTO> manyToOne(Serializable entityId);

    List<? extends DTO> oneToMany(Serializable entityId);
}

package com.osiris.data.orm.relation;

import com.osiris.data.common.dto.DTO;
import com.osiris.data.common.orm.DataMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface RelationMapper<T extends DTO> extends Serializable {

    int getId();

    DataMapper dataMapper();

    Optional<T> manyToOne();

    List<T> oneToMany();
}

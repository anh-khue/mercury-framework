package io.osiris.data.jpa;

import io.osiris.data.common.dto.DTO;

import java.util.List;
import java.util.Optional;

public interface JpaDTO extends DTO {

    Optional<? extends JpaDTO> manyToOne();

    List<? extends JpaDTO> oneToMany();
}
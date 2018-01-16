package com.osiris.data.orm.binding;

import java.io.Serializable;

public interface DTOBindingsFactory extends Serializable {

    DTOBindings createDTOBindings();
}

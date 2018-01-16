package com.osiris.data.orm.binding;

import java.io.Serializable;

public interface DTOBindingsFactory<T extends DataBindings, R extends RelationBindings> extends Serializable {

    T createDataBindings();

    R createRelationBindings();
}

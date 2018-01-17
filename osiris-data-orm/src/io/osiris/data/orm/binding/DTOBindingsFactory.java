package io.osiris.data.orm.binding;

import java.io.Serializable;

@Deprecated
public interface DTOBindingsFactory<T extends DataBindings, R extends RelationBindings> extends Serializable {

    T createDataBindings();

    R createRelationBindings();
}

package com.osiris.data.common.binding;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public interface DataBindings extends Serializable {

    String table();

    List<String> primaryKey();

    List<String> columns();

    List<Field> fields();
}

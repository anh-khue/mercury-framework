package com.osiris.data.common.orm;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

public interface DataMapper extends Serializable {

    String table();

    List<String> primaryKey();

    List<String> columns();

    List<Field> fields();

}

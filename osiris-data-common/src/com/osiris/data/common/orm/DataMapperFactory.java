package com.osiris.data.common.orm;

import java.io.Serializable;

public interface DataMapperFactory extends Serializable {

    DataMapper createDataMapper();

}

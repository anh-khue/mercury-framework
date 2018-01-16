package com.osiris.test.processor;

import com.osiris.core.data.annotation.Table;
import com.osiris.sample.entity.Drink;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DataAnnotationProcessorTest {

    @Test
    public void getTable() {
        Class<?> entityClass = Drink.class;

        String resultTableName = entityClass.getAnnotation(Table.class).table();

        assertEquals("drinks", resultTableName);
    }

}
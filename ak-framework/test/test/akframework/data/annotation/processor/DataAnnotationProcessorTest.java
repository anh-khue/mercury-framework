package test.akframework.data.annotation.processor;

import com.akframework.core.data.annotation.Table;
import org.junit.Test;
import test.akframework.app.Drink;

import static org.junit.Assert.*;

public class DataAnnotationProcessorTest {

    @Test
    public void getTable() {
        Class<?> entityClass = Drink.class;

        String resultTableName = entityClass.getAnnotation(Table.class).table();

        assertEquals("drinks", resultTableName);
    }

    @Test
    public void getCombineKey() {
    }
}
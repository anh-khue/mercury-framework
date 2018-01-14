package framework.annotation.processor;

import framework.annotation.model.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ModelAnnotationProcessor implements Serializable {
    private Class<?> model;

    public ModelAnnotationProcessor(Class<?> model) {
        this.model = model;
    }

    public String getTableName() {
        return model.getAnnotation(Table.class).tableName();
    }

    public List<String> getTableColumns(Class<?> model) {
        List<String> tableColumns = new ArrayList<>();

        if (model.getSuperclass() != null) {
            tableColumns.addAll(getTableColumns(model.getSuperclass()));
        }

        Field[] fields = model.getDeclaredFields();
        for (Field field : fields) {
            Column tableColumn = field.getAnnotation(Column.class);
            if (tableColumn != null) {
                tableColumns.add(tableColumn.value());
            }
        }

        return tableColumns;
    }

    public List<Field> getModelFields(Class<?> model) {
        List<Field> modelFields = new ArrayList<>();

        if (model.getSuperclass() != null) {
            modelFields.addAll(getModelFields(model.getSuperclass()));
        }

        Field[] fields = model.getDeclaredFields();
        modelFields.addAll(Arrays.asList(fields));

        return modelFields;
    }

    public Map<String, String> getManyToOneInformation(Method method) {
        Map<String, String> result = new HashMap<>();

        ManyToOne annotation = method.getAnnotation(ManyToOne.class);

        result.put("referencedTable", annotation.referencedTable());
        result.put("referencedColumn", annotation.column());
        return result;
    }

    public Map<String, String> getOneToManyInformation(Method method) {
        Map<String, String> result = new HashMap<>();

        OneToMany annotation = method.getAnnotation(OneToMany.class);

        result.put("table", annotation.table());
        result.put("column", annotation.column());

        return result;
    }

    public List<String> getCombineKey() {
        List<String> combineKeys = new ArrayList<>();

        Field[] fields = model.getDeclaredFields();
        for (Field field : fields) {
            CombineKey combineKey = field.getAnnotation(CombineKey.class);
            Column tableColumn = field.getAnnotation(Column.class);
            if (combineKey != null && tableColumn != null) {
                combineKeys.add(tableColumn.value());
            }
        }

        return combineKeys;
    }
}

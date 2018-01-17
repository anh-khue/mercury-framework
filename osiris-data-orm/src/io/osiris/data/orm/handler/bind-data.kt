@file:JvmName("DataBindingHandler")

package io.osiris.data.orm.handler

import io.osiris.data.common.dto.DTO
import com.osiris.data.orm.annotation.Column
import java.lang.reflect.Field
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.util.Arrays
import kotlin.collections.ArrayList

@Deprecated("Going to remove ORM module")
fun fetchColumns(entityClass: Class<*>): List<String> {
    val columns = ArrayList<String>()
    
    if (entityClass.superclass != null) {
        columns.addAll(fetchColumns(entityClass.superclass))
    }
    
    entityClass.declaredFields
            .asSequence()
            .mapNotNull { it.getAnnotation(Column::class.java) }
            .mapTo(columns) { it.value }
    
    return columns
}

@Deprecated("Going to remove ORM module")
fun fetchFields(entityClass: Class<*>): List<Field> {
    val fieldList = ArrayList<Field>()
    
    if (entityClass.superclass != null) {
        fieldList.addAll(fetchFields(entityClass.superclass))
    }
    
    val modelFieldArray = entityClass.declaredFields
    fieldList.addAll(Arrays.asList(*modelFieldArray))
    
    return fieldList
}

@Deprecated("Going to remove ORM module")
@Throws(SQLException::class, IllegalAccessException::class)
fun setFields(dto: DTO, fieldList: List<Field>, resultSet: ResultSet) {
    for (field in fieldList) {
        val column: Column? = field.getAnnotation(Column::class.java)
        if (column != null) {
            field.isAccessible = true
            val value: Any?
            val fieldType = field.type
            value = try {
                if (fieldType == Int::class.javaPrimitiveType || fieldType == Int::class.java) {
                    resultSet.getInt(column.value)
                } else if (fieldType == Boolean::class.javaPrimitiveType || fieldType == Boolean::class.java) {
                    resultSet.getBoolean(column.value)
                } else if (fieldType == Timestamp::class.java) {
                    resultSet.getTimestamp(column.value)
                } else if (fieldType == Double::class.javaPrimitiveType || fieldType == Double::class.java) {
                    resultSet.getDouble(column.value)
                } else {
                    resultSet.getString(column.value)
                }
            } catch (e: Exception) {
                null
            }
            
            field.set(dto, value)
        }
    }
}
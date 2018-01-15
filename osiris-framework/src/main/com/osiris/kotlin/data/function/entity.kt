@file:JvmName("EntityFunctions")

package com.osiris.kotlin.data.function

import com.osiris.core.data.annotation.Column
import com.osiris.core.data.annotation.ManyToOne
import com.osiris.core.data.annotation.OneToMany
import com.osiris.core.data.annotation.processor.DataAnnotationProcessor
import com.osiris.core.data.common.Entity
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*

fun createProcessor(entityClass: Class<*>): DataAnnotationProcessor = DataAnnotationProcessor(entityClass)

fun loadFields(model: Class<*>): List<Field> {
    val fieldList = ArrayList<Field>()
    
    if (model.superclass != null) {
        fieldList.addAll(loadFields(model.superclass))
    }
    
    val modelFieldArray = model.declaredFields
    fieldList.addAll(Arrays.asList(*modelFieldArray))
    
    return fieldList
}

@Throws(SQLException::class, IllegalAccessException::class)
fun setFields(entity: Entity, fieldList: List<Field>, resultSet: ResultSet) {
    for (field in fieldList) {
        val column = field.getAnnotation(Column::class.java)
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
            
            field.set(entity, value)
        }
    }
}

fun scanManyToOne(method: Method): Map<String, String> {
    val map = HashMap<String, String>()
    
    val annotation = method.getAnnotation(ManyToOne::class.java)
    
    map.put("referencedTable", annotation.referencedTable)
    map.put("column", annotation.column)
    
    return map
}

fun scanOneToMany(method: Method): Map<String, String> {
    val map = HashMap<String, String>()
    
    val annotation = method.getAnnotation(OneToMany::class.java)
    
    map.put("table", annotation.table)
    map.put("referenceColumn", annotation.referenceColumn)
    
    return map
}

@file:JvmName("EntityFunctions")

package com.akframework

import com.akframework.data.annotation.Column
import com.akframework.data.mapping.Entity
import java.lang.reflect.Field
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*


fun getFields(model: Class<*>): List<Field> {
    val entityFields = ArrayList<Field>()

    if (model.superclass != null) {
        entityFields.addAll(getFields(model.superclass))
    }

    val fields = model.declaredFields
    entityFields.addAll(Arrays.asList(*fields))

    return entityFields
}

@Throws(SQLException::class, IllegalAccessException::class)
fun setFields(entity: Entity, fields: List<Field>, resultSet: ResultSet) {
    for (field in fields) {
        val column = field.getAnnotation(Column::class.java)
        if (column != null) {
            field.isAccessible = true
            val value: Any
            val fieldType = field.type
            value = if (fieldType == Int::class.javaPrimitiveType || fieldType == Int::class.java) {
                resultSet.getInt(column.value)
            } else if (fieldType == String::class.java) {
                resultSet.getString(column.value)
            } else if (fieldType == Boolean::class.javaPrimitiveType || fieldType == Boolean::class.java) {
                resultSet.getBoolean(column.value)
            } else if (fieldType == Timestamp::class.java) {
                resultSet.getTimestamp(column.value)
            } else if (fieldType == Double::class.javaPrimitiveType || fieldType == Double::class.java) {
                resultSet.getDouble(column.value)
            } else {
                resultSet.getString(column.value)
            }

            field.set(entity, value)
        }
    }
}

@file:JvmName("TableFunctions")

package com.akframework.kotlin.data.function

import com.akframework.core.data.annotation.Column
import java.util.*

fun getColumns(entityClass: Class<*>): List<String> {
    val columns = ArrayList<String>()
    
    if (entityClass.superclass != null) {
        columns.addAll(getColumns(entityClass.superclass))
    }
    
    entityClass.declaredFields
            .asSequence()
            .mapNotNull { it.getAnnotation(Column::class.java) }
            .mapTo(columns) { it.value }
    
    return columns
}
@file:JvmName("RelationScanner")

package com.osiris.data.orm.entity

import com.osiris.data.orm.annotation.ManyToOne
import com.osiris.data.orm.annotation.OneToMany
import java.lang.reflect.Method
import java.util.*

fun scanManyToOne(method: Method): Map<String, String> {
    val relationMap = HashMap<String, String>()
    
    val annotation = method.getAnnotation(ManyToOne::class.java)
    
    relationMap.put("referencedTable", annotation.referencedTable)
    relationMap.put("column", annotation.column)
    
    return relationMap
}


fun scanOneToMany(method: Method): Map<String, String> {
    val relationMap = HashMap<String, String>()
    
    val annotation = method.getAnnotation(OneToMany::class.java)
    
    relationMap.put("table", annotation.table)
    relationMap.put("referenceColumn", annotation.referenceColumn)
    
    return relationMap
}
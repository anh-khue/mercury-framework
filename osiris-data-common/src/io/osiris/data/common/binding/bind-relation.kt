@file:JvmName("RelationBindingHandler")

package io.osiris.data.common.binding

import io.osiris.data.common.annotation.ManyToOne
import io.osiris.data.common.annotation.OneToMany

import java.lang.reflect.Method
import java.util.*

fun fetchManyToOne(method: Method): Map<String, String> {
    val relationMap = HashMap<String, String>()
    
    val annotation = method.getAnnotation(ManyToOne::class.java)
    
    relationMap.put("column", annotation.column)
    relationMap.put("table", annotation.table)
    relationMap.put("target", annotation.target)
    
    return relationMap
}

fun fetchOneToMany(method: Method): Map<String, String> {
    val relationMap = HashMap<String, String>()
    
    val annotation = method.getAnnotation(OneToMany::class.java)
    
    relationMap.put("table", annotation.table)
    relationMap.put("column", annotation.column)
    relationMap.put("target", annotation.target)
    
    return relationMap
}
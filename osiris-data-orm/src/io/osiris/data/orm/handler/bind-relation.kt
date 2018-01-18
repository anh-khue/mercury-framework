@file:JvmName("RelationBindingHandler")

package io.osiris.data.orm.handler

import com.osiris.data.orm.annotation.ManyToOne
import com.osiris.data.orm.annotation.OneToMany
import java.lang.reflect.Method
import java.util.*

@Deprecated("Going to remove ORM module")
fun fetchManyToOne(method: Method): Map<String, String> {
    val relationMap = HashMap<String, String>()
    
    val annotation = method.getAnnotation(ManyToOne::class.java)
    
    relationMap.put("referencedTable", annotation.referencedTable)
    relationMap.put("column", annotation.column)
    
    return relationMap
}

@Deprecated("Going to remove ORM module")
fun fetchOneToMany(method: Method): Map<String, String> {
    val relationMap = HashMap<String, String>()
    
    val annotation = method.getAnnotation(OneToMany::class.java)
    
    relationMap.put("value", annotation.table)
    relationMap.put("referenceColumn", annotation.referenceColumn)
    
    return relationMap
}
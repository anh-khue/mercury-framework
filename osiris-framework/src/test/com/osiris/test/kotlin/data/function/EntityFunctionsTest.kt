package com.osiris.test.kotlin.data.function

import com.osiris.kotlin.data.function.loadFields
import com.osiris.sample.entity.Drink
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class EntityFunctionsTest {
    
    @Test
    fun getFields() {
        val expected: List<String> = Arrays.asList("id", "drinkName", "price", "createdDate", "deletedDate", "materialList")
        
        val result: List<String> = loadFields(Drink::class.java)
                .map { it.name }
                .toList()
        
        result.forEach({ println(it) })
        
        assertEquals(expected, result)
    }
    
    @Test
    fun getManyToOneMap() {
    }
    
    @Test
    fun getOneToManyMap() {
    }
    
}
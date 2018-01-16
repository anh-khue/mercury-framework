package com.osiris.data.orm.binding

import app.osiris.sample.Drink
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

internal class DataBindingHandlerTest {
    
    @Test
    fun fetchColumns() {
        val expectedList: List<String> = Arrays.asList("id", "name", "price", "origin_id")
        expectedList.forEach({ println(it) })
        
        val result: List<String> = fetchColumns(Drink::class.java)
        result.forEach({ println(it) })
        
        assertEquals(expectedList, result)
    }
    
    @Test
    fun fetchFields() {
        val expectedList: List<String> = Arrays.asList("id", "drinkName", "drinkPrice", "drinkOriginId")
        expectedList.forEach({ println(it) })
        
        val result: List<String> = fetchFields(Drink::class.java)
                .map { it.name }
                .toList()
        result.forEach({ println(it) })
        
        assertEquals(expectedList, result)
    }
    
}
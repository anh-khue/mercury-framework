package com.osiris.test.kotlin.data.function

import com.osiris.kotlin.data.function.getColumns
import com.osiris.sample.entity.Drink
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class TableFunctionsTest {
    
    @Test
    fun getColumnsTest() {
        
        val result: List<String> = getColumns(Drink::class.java)
        
        val expectList: List<String> = Arrays.asList("id", "drink_name", "price", "created_date", "deleted_date")
        
        assertEquals(expectList, result)
    }
    
}
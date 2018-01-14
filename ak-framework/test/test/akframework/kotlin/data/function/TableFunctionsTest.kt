package test.akframework.kotlin.data.function

import com.akframework.kotlin.data.function.getColumns
import org.junit.Assert.*
import org.junit.Test
import test.akframework.app.entity.Drink
import java.util.*

class TableFunctionsTest {
    
    @Test
    fun getColumnsTest() {
        
        val result: List<String> = getColumns(Drink::class.java)
        
        val expectList: List<String> = Arrays.asList("id", "drink_name", "price", "created_date", "deleted_date")
        
        assertEquals(expectList, result)
    }
    
}
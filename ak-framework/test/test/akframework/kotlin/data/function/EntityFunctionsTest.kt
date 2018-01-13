package test.akframework.kotlin.data.function

import com.akframework.kotlin.data.function.*
import org.junit.Test

import org.junit.Assert.*
import test.akframework.app.Drink
import java.util.*

class EntityFunctionsTest {
    
    @Test
    fun getFields() {
        val expected: List<String> = Arrays.asList("id", "drinkName", "price", "createdDate", "deletedDate")
        
        val result: List<String> = getFields(Drink::class.java)
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
package test.akframework.kotlin.data.function

import com.akframework.kotlin.data.function.*
import org.junit.Test

import org.junit.Assert.*
import test.akframework.app.entity.Drink
import java.util.*

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
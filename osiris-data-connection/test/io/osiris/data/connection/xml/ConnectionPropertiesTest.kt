package io.osiris.data.connection.xml

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ConnectionPropertiesTest {
    
    @Test
    fun getDriverClass() {
        assertEquals("com.mysql.jdbc.Driver", ConnectionProperties.driverClass)
    }
    
    @Test
    fun getUrl() {
        assertEquals("jdbc:mysql://0.0.0.0:3306/framework_test_db", ConnectionProperties.url)
    }
    
    @Test
    fun getUsername() {
        assertEquals("root", ConnectionProperties.username)
    }
    
    @Test
    fun getPassword() {
        assertEquals("password", ConnectionProperties.password)
    }
}
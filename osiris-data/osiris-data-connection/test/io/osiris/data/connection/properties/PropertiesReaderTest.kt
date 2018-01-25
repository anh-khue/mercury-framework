package io.osiris.data.connection.properties

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PropertiesReaderTest {

    @Test
    fun getDriverClass() {
        assertEquals("com.mysql.jdbc.Driver", PropertiesReader.driverClass)
    }

    @Test
    fun getUrl() {
        assertEquals("jdbc:mysql://0.0.0.0:3306/osiris_test_db", PropertiesReader.url)
    }

    @Test
    fun getUsername() {
        assertEquals("osiris-master", PropertiesReader.username)
    }

    @Test
    fun getPassword() {
        assertEquals("123456", PropertiesReader.password)
    }
}
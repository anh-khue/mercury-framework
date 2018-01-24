package io.osiris.data.connection.xml

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class XmlPropertiesTest {

    @Test
    fun getDriverClass() {
        assertEquals("com.mysql.jdbc.Driver", XmlReader.driverClass)
    }

    @Test
    fun getUrl() {
        assertEquals("jdbc:mysql://0.0.0.0:3306/osiris_test_db", XmlReader.url)
    }

    @Test
    fun getUsername() {
        assertEquals("osiris-master", XmlReader.username)
    }

    @Test
    fun getPassword() {
        assertEquals("123456", XmlReader.password)
    }
}
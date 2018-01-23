package io.osiris.data.connection.xml

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class XmlPropertiesTest {

    @Test
    fun getDriverClass() {
        assertEquals("com.mysql.jdbc.Driver", XmlReader.driverClass)
    }

    @Test
    fun getUrl() {
        assertEquals("jdbc:mysql://localhost:3306/osiris_test_db", XmlReader.url)
    }

    @Test
    fun getUsername() {
        assertEquals("osiris", XmlReader.username)
    }

    @Test
    fun getPassword() {
        assertEquals("123456", XmlReader.password)
    }
}
package io.osiris.data.connection

import io.osiris.data.connection.properties.PropertiesConnection
import io.osiris.data.connection.xml.XmlConnection

import java.sql.Connection
import java.sql.SQLException

class ConnectionFactory : ConnectionAdapter {

    @Throws(ClassNotFoundException::class, SQLException::class)
    override fun openConnection(): Connection {
        val url = ClassLoader.getSystemResource("resources/connection.properties")
        return if (url != null) {
            PropertiesConnection.openConnection()
        } else {
            XmlConnection.openConnection()
        }
    }
}

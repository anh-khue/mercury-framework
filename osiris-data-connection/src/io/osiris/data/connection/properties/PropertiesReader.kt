package io.osiris.data.connection.properties

import io.osiris.data.connection.ConnectionReadable

import java.io.FileInputStream
import java.io.IOException
import java.util.Properties

object PropertiesReader : ConnectionReadable {

    override fun getDriverClass(): String {
        return properties.getProperty("driver")
    }

    override fun getUrl(): String {
        return properties.getProperty("url")
    }

    override fun getUsername(): String {
        return properties.getProperty("username")
    }

    override fun getPassword(): String {
        return properties.getProperty("password")
    }
}

private val properties: Properties = try {
    val inputStream = FileInputStream("resources/connection.properties")

    val properties = Properties()

    properties.load(inputStream)
    properties
} catch (e: IOException) {
    Properties()
}

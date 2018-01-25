package io.osiris.data.connection.properties

import io.osiris.data.connection.ConnectionReader
import java.io.FileNotFoundException
import java.util.*

object PropertiesReader : ConnectionReader {

    override fun getDriverClass(): String {
        return getProperties().getProperty("driver")
    }

    override fun getUrl(): String {
        return getProperties().getProperty("url")
    }

    override fun getUsername(): String {
        return getProperties().getProperty("username")
    }

    override fun getPassword(): String {
        return getProperties().getProperty("password")
    }
}

private fun getProperties(): Properties = try {
    val inputStream = ClassLoader.getSystemResourceAsStream("resources/connection.properties")
    val properties = Properties()

    properties.load(inputStream)
    properties
} catch (e: FileNotFoundException) {
    Properties()
}

private fun fail(): Nothing = throw IllegalArgumentException("Error occurs in connection.properties")

package io.osiris.data.connection.xml

import io.osiris.data.connection.ConnectionReadable
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.io.FileNotFoundException
import javax.xml.parsers.DocumentBuilderFactory

object XmlReader : ConnectionReadable {
    override fun getDriverClass(): String {
        return getPropertyFromNode("driver") ?: fail()
    }

    override fun getUrl(): String {
        return getPropertyFromNode("url") ?: fail()
    }

    override fun getUsername(): String {
        return getPropertyFromNode("username") ?: fail()
    }

    override fun getPassword(): String {
        return getPropertyFromNode("password") ?: fail()
    }
}

private val xmlFile: File? = try {
    val classLoader = Thread.currentThread().contextClassLoader
    val url = classLoader.getResource("resources/connection.xml")

    File(url.file)
} catch (e: FileNotFoundException) {
    null
}

private val xmlNode: Node? = try {
    val document = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder()
            .parse(xmlFile)
    document.documentElement.normalize()

    document.getElementsByTagName("connection").item(0)
} catch (e: Exception) {
    null
}

private fun getPropertyFromNode(property: String): String? {
    val element = xmlNode as Element
    return element.getElementsByTagName(property).item(0).textContent
}

private fun fail(): Nothing = throw IllegalArgumentException("Error occurs in connection.xml")
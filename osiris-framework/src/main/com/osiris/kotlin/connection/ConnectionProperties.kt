package com.osiris.kotlin.connection

import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import java.io.FileNotFoundException
import javax.xml.parsers.DocumentBuilderFactory

object ConnectionProperties {
    val driverClass = getPropertyFromNode("driver") ?: fail()
    val url = getPropertyFromNode("url") ?: fail()
    val username = getPropertyFromNode("username") ?: fail()
    val password = getPropertyFromNode("password") ?: fail()
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
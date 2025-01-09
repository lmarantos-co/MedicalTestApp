package com.example.cvdriskestimator.customClasses

import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


object XMLUtils {
    fun parseXMLFromString(xmlString: String?): Document? {
        return try {
            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val inputSource = InputSource(StringReader(xmlString))
            builder.parse(inputSource)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
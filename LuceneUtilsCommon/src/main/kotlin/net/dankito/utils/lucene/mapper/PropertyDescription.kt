package net.dankito.utils.lucene.mapper

import java.lang.reflect.Field
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaField


open class PropertyDescription(
        val type: PropertyType,
        val documentFieldName: String,
        val field: Field
) {

    constructor(type: PropertyType, documentFieldName: String, property: KProperty1<*, *>) :
            this(type, documentFieldName, property.javaField!!)

    constructor(type: PropertyType, documentFieldName: String, resultClass: Class<*>, objectPropertyName: String) :
            this(type, documentFieldName, resultClass.getDeclaredField(objectPropertyName))


    override fun toString(): String {
        return "${field.name} $type"
    }

}
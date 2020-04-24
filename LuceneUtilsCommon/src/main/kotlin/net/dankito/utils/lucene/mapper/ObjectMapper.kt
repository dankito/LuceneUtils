package net.dankito.utils.lucene.mapper

import net.dankito.utils.lucene.search.FieldMapper
import net.dankito.utils.lucene.search.SearchResult
import net.dankito.utils.lucene.search.SearchResults
import org.apache.lucene.document.Document
import java.lang.reflect.Constructor


/**
 * Automatically maps search results to objects.
 *
 * But adds quite a lot of runtime overhead as it's using reflection to instantiate objects and set property values.
 * Approximately doubles mapping time on Android compared to creating objects directly.
 */
open class ObjectMapper {

    protected val fieldMapper = FieldMapper()


    open fun <T> map(results: SearchResults, objectClass: Class<T>, properties: List<PropertyDescription>): List<T> {
        return map(results.hits.map { it.document }, objectClass, properties)
    }

    open fun <T> map(documents: List<Document>, objectClass: Class<T>, properties: List<PropertyDescription>): List<T> {
        return documents.map { document ->
            toObject(document, objectClass, properties)
        }
    }

    open fun <T> toObject(result: SearchResult, objectClass: Class<T>, properties: List<PropertyDescription>): T {
        return toObject(result.document, objectClass, properties)
    }

    open fun <T> toObject(document: Document, objectClass: Class<T>, properties: List<PropertyDescription>): T {
        val zeroArgsConstructor = getAccessibleZeroArgsConstructor(objectClass)

        val result = zeroArgsConstructor.newInstance()

        properties.forEach { propertyDescription ->
            val property = propertyDescription.field
            property.isAccessible = true

            // TODO: use setInt(), setDouble(), ... methods?
            property.set(result, mapDocumentField(document, propertyDescription))
        }

        return result
    }

    protected open fun <T> getAccessibleZeroArgsConstructor(objectClass: Class<T>): Constructor<T> {
        val constructors = objectClass.declaredConstructors
        val zeroArgsConstructor = constructors.firstOrNull { it.parameterTypes.isEmpty() }

        if (zeroArgsConstructor == null) {
            throw InstantiationException("Class $objectClass has no zero argument constructor. Such one is needed to instantiate an instance of $objectClass")
        }

        zeroArgsConstructor.isAccessible = true

        return zeroArgsConstructor as Constructor<T>
    }


    protected open fun mapDocumentField(document: Document, propertyDescription: PropertyDescription): Any? {
        return when (propertyDescription.type) {
            PropertyType.String -> fieldMapper.string(document, propertyDescription.documentFieldName)
            PropertyType.NullableString -> fieldMapper.nullableString(document, propertyDescription.documentFieldName)
            PropertyType.Int -> fieldMapper.int(document, propertyDescription.documentFieldName)
            PropertyType.NullableInt -> fieldMapper.nullableInt(document, propertyDescription.documentFieldName)
            PropertyType.Long -> fieldMapper.long(document, propertyDescription.documentFieldName)
            PropertyType.NullableLong -> fieldMapper.nullableLong(document, propertyDescription.documentFieldName)
            PropertyType.Float -> fieldMapper.float(document, propertyDescription.documentFieldName)
            PropertyType.NullableFloat -> fieldMapper.nullableFloat(document, propertyDescription.documentFieldName)
            PropertyType.Double -> fieldMapper.double(document, propertyDescription.documentFieldName)
            PropertyType.NullableDouble -> fieldMapper.nullableDouble(document, propertyDescription.documentFieldName)
            PropertyType.Date -> fieldMapper.date(document, propertyDescription.documentFieldName)
            PropertyType.NullableDate -> fieldMapper.nullableDate(document, propertyDescription.documentFieldName)
        }
    }

}
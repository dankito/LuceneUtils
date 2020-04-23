package net.dankito.utils.lucene.index

import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.StringField
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexableField
import org.apache.lucene.index.Term


open class DocumentsWriterBase(protected val writer: IndexWriter) : AutoCloseable {

    override fun close() {
        val analyzer = writer.analyzer

        writer.close()

        writer.directory.close()

        analyzer.close()
    }


    open fun createDocument(vararg fields: IndexableField): Document {
        return createDocument(fields.toList())
    }

    open fun createDocument(fields: List<IndexableField>): Document {
        val document = Document()

        fields.forEach { field ->
            document.add(field)
        }

        return document
    }

    open fun createDocumentForNonNullFields(vararg fields: IndexableField?): Document {
        return createDocument(fields.filterNotNull())
    }

    open fun createDocumentForNonNullFields(fields: List<IndexableField?>): Document {
        return createDocument(fields.filterNotNull())
    }


    open fun saveDocument(vararg fields: IndexableField): Document {
        return saveDocument(fields.toList())
    }

    open fun saveDocument(fields: List<IndexableField>): Document {
        val document = createDocument(fields)

        saveDocument(document)

        return document
    }

    open fun saveDocument(document: Document) {
        writer.addDocument(document)

        writer.commit() // TODO: very expensive call, call only every few seconds
    }

    open fun saveDocumentForNonNullFields(vararg fields: IndexableField?): Document {
        return saveDocument(*fields.filterNotNull().toTypedArray())
    }

    open fun saveDocuments(documents: List<Document>) {
        documents.forEach { document ->
            writer.addDocument(document)
        }

        writer.commit()
    }


    open fun updateDocument(idFieldName: String, idFieldValue: String, vararg fields: IndexableField): Document {
        val fieldsIncludingIdField = mutableListOf(createIdField(idFieldName, idFieldValue))
        fieldsIncludingIdField.addAll(fields.toList())

        val document = createDocument(fieldsIncludingIdField)

        val findExistingDocumentTerm = Term(idFieldName, idFieldValue)

        writer.updateDocument(findExistingDocumentTerm, document)

        writer.commit() // TODO: very expensive call, call only every few seconds

        return document
    }

    open fun updateDocumentForNonNullFields(idFieldName: String, idFieldValue: String, vararg fields: IndexableField?): Document {
        return updateDocument(idFieldName, idFieldValue, *fields.filterNotNull().toTypedArray())
    }

    /**
     * Some of the ugliest code i've ever written. Find a better solution for nestedListObjects: List<List<List<IndexableField>?>?>?
     */
    open fun updateDocumentForNonNullFields(idFieldName: String, idFieldValue: String, nestedListObjects: List<List<List<IndexableField>?>?>? = null,
                                            vararg fields: IndexableField?): Document {

        if (nestedListObjects == null) {
            return updateDocument(idFieldName, idFieldValue, *fields.filterNotNull().toTypedArray())
        }

        val allFields = fields.toMutableList()

        allFields.addAll(nestedListObjects.filterNotNull().flatten().filterNotNull().flatten())

        return updateDocument(idFieldName, idFieldValue, *allFields.filterNotNull().toTypedArray())
    }


    open fun deleteDocument(idFieldName: String, idFieldValue: String) {
        writer.deleteDocuments(Term(idFieldName, idFieldValue))

        writer.commit()
    }

    /**
     * NOTE: if you want to maximize search performance, you can optionally call forceMerge here.
     * This can be a terribly costly operation, so generally it's only worth it when your index is relatively
     * static (ie you're done adding documents to it)
     */
    open fun optimizeIndex() {
        writer.forceMerge(1)
    }


    protected open fun createIdField(fieldName: String, value: String): IndexableField {
        return StringField(fieldName, value, Field.Store.YES)
    }

}
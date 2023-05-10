package net.dankito.utils.lucene.index

import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.StringField
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexableField
import org.apache.lucene.index.Term
import org.apache.lucene.search.Query
import java.util.concurrent.CopyOnWriteArraySet


abstract class DocumentsWriterBase(val indexWriter: IndexWriter) : AutoCloseable {

    abstract fun saveDocument(document: Document)

    abstract fun updateDocument(findExistingDocumentTerm: Term, document: Document)

    abstract fun deleteDocuments(vararg terms: Term)

    abstract fun deleteDocuments(vararg queries: Query)


    private val indexChangedListeners = CopyOnWriteArraySet<() -> Unit>()


    override fun close() {
        val analyzer = indexWriter.analyzer

        indexWriter.close()

        indexWriter.directory.close()

        analyzer.close()
    }


    open fun createDocument(vararg fields: IndexableField): Document {
        return createDocument(fields.toList())
    }

    open fun createDocument(vararg fields: Collection<IndexableField>): Document {
        return createDocument(fields.toList().flatten())
    }

    open fun createDocument(fields: Collection<IndexableField>): Document {
        val document = Document()

        fields.forEach { field ->
            document.add(field)
        }

        return document
    }

    open fun createDocumentForNonNullFields(vararg fields: IndexableField?): Document {
        return createDocument(fields.filterNotNull())
    }

    open fun createDocumentForNonNullFields(vararg fields: Collection<IndexableField?>): Document {
        return createDocument(fields.toList().flatten().filterNotNull())
    }

    open fun createDocumentForNonNullFields(fields: Collection<IndexableField?>): Document {
        return createDocument(fields.filterNotNull())
    }


    open fun saveDocument(vararg fields: IndexableField): Document {
        return saveDocument(fields.toList())
    }

    open fun saveDocument(vararg fields: Collection<IndexableField>): Document {
        return saveDocument(fields.toList().flatten())
    }

    open fun saveDocument(fields: Collection<IndexableField>): Document {
        val document = createDocument(fields)

        saveDocument(document)

        notifyIndexChanged()

        return document
    }

    open fun saveDocumentForNonNullFields(vararg fields: IndexableField?): Document {
        return saveDocument(fields.filterNotNull())
    }

    open fun saveDocumentForNonNullFields(vararg fields: Collection<IndexableField?>): Document {
        return saveDocument(fields.toList().flatten().filterNotNull())
    }

    open fun saveDocumentForNonNullFields(fields: Collection<IndexableField?>): Document {
        return saveDocument(fields.filterNotNull())
    }


    open fun saveDocuments(documents: Collection<Document>) {
        documents.forEach { document ->
            saveDocument(document)
        }

        notifyIndexChanged()
    }

    open fun saveDocumentsAndFlushChangesToDisk(documents: Collection<Document>) {
        saveDocuments(documents)

        flushChangesToDisk()
    }


    open fun updateDocument(idFieldName: String, idFieldValue: String, vararg fields: IndexableField): Document {
        return updateDocument(idFieldName, idFieldValue, fields.toList())
    }

    open fun updateDocument(idFieldName: String, idFieldValue: String, fields: Collection<IndexableField>): Document {
        val fieldsIncludingIdField = mutableListOf(createIdField(idFieldName, idFieldValue))
        fieldsIncludingIdField.addAll(fields)

        val document = createDocument(fieldsIncludingIdField)

        val findExistingDocumentTerm = Term(idFieldName, idFieldValue)

        updateDocument(findExistingDocumentTerm, document)

        notifyIndexChanged()

        return document
    }

    open fun updateDocumentForNonNullFields(idFieldName: String, idFieldValue: String, vararg fields: IndexableField?): Document {
        return updateDocument(idFieldName, idFieldValue, fields.filterNotNull())
    }

    open fun updateDocumentForNonNullFields(idFieldName: String, idFieldValue: String, vararg fields: Collection<IndexableField?>): Document {
        return updateDocument(idFieldName, idFieldValue, fields.toList().flatten().filterNotNull())
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
        deleteDocuments(Term(idFieldName, idFieldValue))

        notifyIndexChanged()
    }

    open fun deleteDocumentAndFlushChangesToDisk(idFieldName: String, idFieldValue: String) {
        deleteDocument(idFieldName, idFieldValue)

        flushChangesToDisk()
    }

    open fun deleteDocuments(idFieldName: String, vararg idFieldValues: String) {
        val terms = idFieldValues.map { Term(idFieldName, it) }

        deleteDocuments(*terms.toTypedArray())

        notifyIndexChanged()
    }

    open fun deleteDocumentsAndFlushChangesToDisk(idFieldName: String, vararg idFieldValues: String) {
        deleteDocuments(idFieldName, *idFieldValues)

        flushChangesToDisk()
    }

    open fun deleteDocumentsAndFlushChangesToDisk(query: Query) {
        deleteDocuments(query)

        flushChangesToDisk()

        notifyIndexChanged()
    }


    /**
     * A reader / searcher will not see the changes until they are flushed to disk.
     *
     * By default changes in first instance are cached in memory. Lucene has a default mechanism implemented that
     * after a certain amount document changes or memory used it automatically writes the changes to disk. If you don't
     * want to wait till this occurs but want to see changes immediately, you have to commit them / write them to disk.
     *
     * Be aware this is a costly operation which impacts performance. So don't call this too often.
     */
    abstract fun flushChangesToDisk()

    /**
     * To maximize search performance, you can ask Lucene to merge its segments.
     *
     * This can be a terribly costly operation, so generally it's only worth it when your index is relatively
     * static (ie you're done adding documents to it).
     *
     * So don't call this too often, e.g. only once per month.
     */
    open fun optimizeIndex() {
        flushChangesToDisk()

        indexWriter.forceMerge(1)
    }


    protected open fun createIdField(fieldName: String, value: String): IndexableField {
        return StringField(fieldName, value, Field.Store.YES)
    }


    fun addIndexChangedListener(listener: () -> Unit) {
        this.indexChangedListeners.add(listener)
    }

    private fun notifyIndexChanged() {
        ArrayList(indexChangedListeners).forEach { listener ->
            listener()
        }
    }

}
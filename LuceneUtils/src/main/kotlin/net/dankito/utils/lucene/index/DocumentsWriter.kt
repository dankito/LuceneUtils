package net.dankito.utils.lucene.index

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.index.Term
import org.apache.lucene.search.Query
import org.apache.lucene.store.FSDirectory
import java.io.File
import java.nio.file.Path


open class DocumentsWriter(writer: IndexWriter) : DocumentsWriterBase(writer) {

    companion object {

        @JvmStatic
        fun createDefaultConfig(analyzer: Analyzer): IndexWriterConfig {
            val config = IndexWriterConfig(analyzer)

            config.openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND

            return config
        }

    }


    @JvmOverloads
    constructor(directory: File, analyzer: Analyzer = StandardAnalyzer(), config: IndexWriterConfig = createDefaultConfig(analyzer)) :
            this(directory.toPath(), analyzer, config)

    @JvmOverloads
    constructor(directory: Path, analyzer: Analyzer = StandardAnalyzer(), config: IndexWriterConfig = createDefaultConfig(analyzer)) :
            this(IndexWriter(FSDirectory.open(directory), config))


    override fun saveDocument(document: Document) {
        writer.addDocument(document)
    }

    override fun updateDocument(findExistingDocumentTerm: Term, document: Document) {
        writer.updateDocument(findExistingDocumentTerm, document)
    }

    override fun deleteDocuments(vararg terms: Term) {
        writer.deleteDocuments(*terms)
    }

    override fun deleteDocuments(vararg queries: Query) {
        writer.deleteDocuments(*queries)
    }

    override fun flushChangesToDisk() {
        writer.commit()
    }

}
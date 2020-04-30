package net.dankito.utils.lucene.index

import net.dankito.utils.lucene.Lucene4Constants
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.FSDirectory
import java.io.File


open class DocumentsWriter(writer: IndexWriter) : DocumentsWriterBase(writer) {

    companion object {

        @JvmStatic
        fun createDefaultConfig(analyzer: Analyzer): IndexWriterConfig {
            val config = IndexWriterConfig(Lucene4Constants.LuceneVersion, analyzer)

            config.openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND

            return config
        }

    }

    @JvmOverloads
    constructor(directory: File, analyzer: Analyzer = StandardAnalyzer(Lucene4Constants.LuceneVersion), config: IndexWriterConfig = createDefaultConfig(analyzer)) :
            this(IndexWriter(FSDirectory.open(directory), config))

}
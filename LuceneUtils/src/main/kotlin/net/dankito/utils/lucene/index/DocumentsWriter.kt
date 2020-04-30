package net.dankito.utils.lucene.index

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
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

}
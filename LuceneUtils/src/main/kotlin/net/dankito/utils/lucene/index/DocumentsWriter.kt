package net.dankito.utils.lucene.index

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.FSDirectory
import java.io.File
import java.nio.file.Path


open class DocumentsWriter(writer: IndexWriter) : DocumentsWriterBase(writer) {

    @JvmOverloads
    constructor(directory: File, analyzer: Analyzer = StandardAnalyzer()) : this(directory.toPath(), analyzer)

    @JvmOverloads
    constructor(directory: Path, analyzer: Analyzer = StandardAnalyzer()) :
            this(IndexWriter(FSDirectory.open(directory), IndexWriterConfig(analyzer).apply { openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND }))

}
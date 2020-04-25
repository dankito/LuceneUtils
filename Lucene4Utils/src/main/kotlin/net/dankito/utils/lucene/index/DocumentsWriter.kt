package net.dankito.utils.lucene.index

import net.dankito.utils.lucene.Lucene4Constants
import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.store.FSDirectory
import java.io.File


open class DocumentsWriter(writer: IndexWriter) : DocumentsWriterBase(writer) {

    constructor(directory: File, analyzer: Analyzer = StandardAnalyzer(Lucene4Constants.LuceneVersion)) :
            this(IndexWriter(FSDirectory.open(directory), IndexWriterConfig(Lucene4Constants.LuceneVersion, analyzer).apply { openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND }))

}
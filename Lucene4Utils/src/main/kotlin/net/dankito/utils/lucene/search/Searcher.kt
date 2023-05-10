package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.index.DocumentsWriter
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.search.TopDocs
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import java.io.File


open class Searcher private constructor(
	private val directory: Directory? = null,
	private val indexWriter: IndexWriter? = null
) : SearcherBase() {

	constructor(directory: Directory) : this(directory, null)

	constructor(indexDirectory: File) : this(FSDirectory.open(indexDirectory))

	constructor(writer: IndexWriter) : this(null, writer)

	constructor(writer: DocumentsWriter) : this(writer.indexWriter) {
		writer.addIndexChangedListener {
			cachedIndexReader = null
		}
	}


	private var cachedIndexReader: IndexReader? = null

	override fun getIndexReader(): IndexReader {
		cachedIndexReader?.let {
			return it
		}

		return createIndexReader().apply {
			cachedIndexReader = this
		}
	}

	private fun createIndexReader(): IndexReader {
		return if (directory != null) {
			DirectoryReader.open(directory)
		} else if (indexWriter != null) {
			DirectoryReader.open(indexWriter, true)
		} else {
			// should never come to this as constructors prohibit setting an illegal state
			throw IllegalArgumentException("Either directory or indexWriter has to be set")
		}
	}

	override fun getCountTotalHits(topDocs: TopDocs): Long {
		return topDocs.totalHits.toLong()
	}

}
package net.dankito.utils.lucene.search

import org.apache.lucene.search.TopDocs
import org.apache.lucene.store.Directory
import org.apache.lucene.store.FSDirectory
import java.io.File


open class Searcher(directory: Directory) : SearcherBase(directory) {

	constructor(indexDirectory: File) : this(FSDirectory.open(indexDirectory))


	override fun getCountTotalHits(topDocs: TopDocs): Long {
		return topDocs.totalHits.toLong()
	}

}
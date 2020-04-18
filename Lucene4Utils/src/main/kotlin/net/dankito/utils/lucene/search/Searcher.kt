package net.dankito.utils.lucene.search

import org.apache.lucene.search.TopDocs


open class Searcher : SearcherBase() {

	override fun getCountTotalHits(topDocs: TopDocs): Long {
		return topDocs.totalHits.toLong()
	}

}
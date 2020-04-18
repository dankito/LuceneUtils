package net.dankito.utils.lucene.search

import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.search.*
import org.apache.lucene.store.Directory


abstract class SearcherBase(protected val directory: Directory) : AutoCloseable {

	protected abstract fun getCountTotalHits(topDocs: TopDocs): Long


	override fun close() {
		directory.close()
	}


	@JvmOverloads
	open fun search(query: Query, countMaxResults: Int = 10_000,
					sortFields: List<SortField> = listOf()): SearchResults {
		val reader = DirectoryReader.open(directory)
		val searcher = IndexSearcher(reader)

		val topDocs = if (sortFields.isEmpty()) searcher.search(query, countMaxResults)
					  else searcher.search(query, countMaxResults, Sort(*sortFields.toTypedArray()))

		val hits = topDocs.scoreDocs.map { SearchResult(it.score, searcher.doc(it.doc)) }

		reader.close()

		return SearchResults(getCountTotalHits(topDocs), hits)
	}

}
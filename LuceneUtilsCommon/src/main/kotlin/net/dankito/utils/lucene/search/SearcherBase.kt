package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.mapper.ObjectMapper
import net.dankito.utils.lucene.mapper.PropertyDescription
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.search.*
import org.apache.lucene.store.Directory
import org.slf4j.LoggerFactory


abstract class SearcherBase(protected val directory: Directory) : AutoCloseable {

	companion object {
		const val DefaultCountMaxResults = 10_000

		const val DefaultCountResultsToPreload = 10

		private val log = LoggerFactory.getLogger(SearcherBase::class.java)
	}


	protected abstract fun getCountTotalHits(topDocs: TopDocs): Long


	protected val mapper = ObjectMapper()


	override fun close() {
		directory.close()
	}


	@JvmOverloads
	open fun search(query: Query, countMaxResults: Int = DefaultCountMaxResults,
					sortFields: List<SortField> = listOf()): SearchResults {

		return search(query, countMaxResults, sortFields, { error -> SearchResults(error) }) { searcher, topDocs ->
			val hits = topDocs.scoreDocs.map { SearchResult(it.score, searcher.doc(it.doc)) }

			SearchResults(getCountTotalHits(topDocs), hits)
		}
	}


	@JvmOverloads
	open fun <T> searchAndMap(query: Query, objectClass: Class<T>, properties: List<PropertyDescription>,
							  countMaxResults: Int = DefaultCountMaxResults, sortFields: List<SortField> = listOf()): List<T> {

		return search(query, countMaxResults, sortFields, { listOf() }) { searcher, topDocs ->
			val fieldsToLoad = properties.map { it.documentFieldName }.toSet()
			val documents = topDocs.scoreDocs.map { searcher.doc(it.doc, fieldsToLoad) }

			mapper.map(documents, objectClass, properties)
		}
	}

	@JvmOverloads
	open fun <T> searchAndMapLazily(query: Query, objectClass: Class<T>, properties: List<PropertyDescription>,
									countMaxResults: Int = DefaultCountMaxResults, countResultToPreload: Int = DefaultCountResultsToPreload,
									sortFields: List<SortField> = listOf()): List<T> {

		return search(query, countMaxResults, sortFields, false, { listOf() }) { searcher, topDocs ->
			val documentIds = topDocs.scoreDocs.map { it.doc }

			LazyLoadingSearchResultsList(documentIds, searcher, objectClass, properties, countResultToPreload)
		}
	}


	protected open fun <T> search(query: Query, countMaxResults: Int, sortFields: List<SortField>,
								  errorOccurred: (Exception) -> T, mapper: (IndexSearcher, TopDocs) -> T): T {

		return search(query, countMaxResults, sortFields, true, errorOccurred, mapper)
	}

	protected open fun <T> search(query: Query, countMaxResults: Int, sortFields: List<SortField>, closeReader: Boolean = true,
								  errorOccurred: (Exception) -> T, mapper: (IndexSearcher, TopDocs) -> T): T {
		try {
			val reader = DirectoryReader.open(directory)
			val searcher = IndexSearcher(reader)

			val topDocs = if (sortFields.isEmpty()) searcher.search(query, countMaxResults)
			else searcher.search(query, countMaxResults, Sort(*sortFields.toTypedArray()))

			val result = mapper(searcher, topDocs)

			if (closeReader) {
				reader.close()
			}

			return result
		} catch (e: Exception) {
			log.error("Could not execute query $query", e)

			return errorOccurred(e)
		}
	}

}
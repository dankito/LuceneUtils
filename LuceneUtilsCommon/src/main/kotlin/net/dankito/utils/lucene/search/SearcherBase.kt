package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.mapper.ObjectMapper
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.Query
import org.apache.lucene.search.Sort
import org.apache.lucene.search.TopDocs
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


	open fun search(query: Query): SearchResults {
		return search(SearchConfig(query))
	}

	open fun search(config: SearchConfig): SearchResults {

		return search(config, { error -> SearchResults(error) }) { searcher, topDocs ->
			val hits = topDocs.scoreDocs.map { SearchResult(it.score, searcher.doc(it.doc)) }

			SearchResults(getCountTotalHits(topDocs), hits)
		}
	}


	open fun <T> searchAndMap(config: MappedSearchConfig<T>): List<T> {

		return search(config, { listOf() }) { searcher, topDocs ->
			val fieldsToLoad = config.properties.map { it.documentFieldName }.toSet()
			val documents = topDocs.scoreDocs.map { searcher.doc(it.doc, fieldsToLoad) }

			mapper.map(documents, config.objectClass, config.properties)
		}
	}

	open fun <T> searchAndMapLazily(config: MappedSearchConfig<T>): List<T> {

		return search(config, false, { listOf() }) { searcher, topDocs ->
			val documentIds = topDocs.scoreDocs.map { it.doc }

			LazyLoadingSearchResultsList(documentIds, searcher, config.objectClass, config.properties, config.countResultToPreload)
		}
	}


	protected open fun <T> search(config: SearchConfig,
								  errorOccurred: (Exception) -> T, mapper: (IndexSearcher, TopDocs) -> T): T {

		return search(config, true, errorOccurred, mapper)
	}

	protected open fun <T> search(config: SearchConfig, closeReader: Boolean = true,
								  errorOccurred: (Exception) -> T, mapper: (IndexSearcher, TopDocs) -> T): T {
		try {
			val reader = DirectoryReader.open(directory)
			val searcher = IndexSearcher(reader)

			val topDocs = if (config.hasNoSortFields) searcher.search(config.query, config.countMaxResults)
			else searcher.search(config.query, config.countMaxResults, Sort(*config.sortFields.toTypedArray()))

			val result = mapper(searcher, topDocs)

			if (closeReader) {
				reader.close()
			}

			return result
		} catch (e: Exception) {
			log.error("Could not execute query ${config.query}", e)

			return errorOccurred(e)
		}
	}

}
package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.mapper.ObjectMapper
import net.dankito.utils.lucene.mapper.PropertyDescription
import org.apache.lucene.search.IndexSearcher
import java.util.concurrent.ConcurrentHashMap


open class LazyLoadingSearchResultsList<T>(
    protected val documentIds: List<Int>,
    protected val searcher: IndexSearcher,
    protected val objectClass: Class<T>,
    protected val properties: List<PropertyDescription>,
    countResultToPreload: Int = 10,
    protected val mapper: ObjectMapper = ObjectMapper()
) : AbstractList<T>() {

    protected var cachedSearchResults = ConcurrentHashMap<Int, T>()


    protected val fieldsToLoad = properties.map { it.documentFieldName }.toSet()


    override val size: Int
        get() = documentIds.size


    init {
        preloadResults(countResultToPreload)
    }


    protected open fun preloadResults(countResultToPreload: Int) {
        for (index in 0 until countResultToPreload) {
            if (index < size) {
                get(index)
            }
        }
    }


    override fun get(index: Int): T {
        cachedSearchResults.get(index)?.let {
            return it
        }

        val documentId = documentIds[index]

        val mappedSearchResult = mapSearchResult(documentId)

        cachedSearchResults.put(index, mappedSearchResult)

        return mappedSearchResult
    }

    protected open fun mapSearchResult(documentId: Int): T {
        val document = searcher.doc(documentId, fieldsToLoad)

        return mapper.toObject(document, objectClass, properties)
    }

}
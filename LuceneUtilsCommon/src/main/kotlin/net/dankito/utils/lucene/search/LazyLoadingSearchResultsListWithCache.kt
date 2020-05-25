package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.cache.ICache
import net.dankito.utils.lucene.mapper.Identifiable
import net.dankito.utils.lucene.mapper.ObjectMapper
import net.dankito.utils.lucene.mapper.PropertyDescription
import org.apache.lucene.search.IndexSearcher


open class LazyLoadingSearchResultsListWithCache<ID, T : Identifiable<ID>>(
    protected val documentIdsToItemIds: Map<Int, ID>,
    searcher: IndexSearcher,
    objectClass: Class<T>,
    properties: List<PropertyDescription>,
    protected val cache: ICache<ID>,
    countResultToPreload: Int = 10,
    mapper: ObjectMapper = ObjectMapper()
) : LazyLoadingSearchResultsList<T>(documentIdsToItemIds.keys.toList(), searcher, objectClass, properties, countResultToPreload, mapper) {


    init {
        super.preloadResults(countResultToPreload)
    }


    override fun preloadResults(countResultToPreload: Int) {
        // avoid that preloadResults() gets called in parent's constructor as this class is at this point not initialized yet
    }

    override fun mapSearchResult(documentId: Int): T {
        documentIdsToItemIds[documentId]?.let { itemId ->
            cache.get(itemId)?.let {
                return it as T
            }
        }

        return super.mapSearchResult(documentId)
    }
}
package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.cache.ICache
import net.dankito.utils.lucene.mapper.Identifiable
import net.dankito.utils.lucene.mapper.PropertyDescription
import org.apache.lucene.search.Query
import org.apache.lucene.search.SortField


open class MapCachedSearchConfig<ID, T : Identifiable<ID>>(
    query: Query,
    objectClass: Class<T>,
    properties: List<PropertyDescription>,
    val cache: ICache<ID>,
    countMaxResults: Int = DefaultCountMaxResults,
    sortFields: List<SortField> = listOf(),
    /**
     * Only needed when mapping lazily.
     */
    countResultToPreload: Int = DefaultCountResultsToPreload
) : MappedSearchConfig<T>(query, objectClass, properties, countMaxResults, sortFields, countResultToPreload)
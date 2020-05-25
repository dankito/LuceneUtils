package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.mapper.PropertyDescription
import org.apache.lucene.search.Query
import org.apache.lucene.search.SortField


open class MappedSearchConfig<T> @JvmOverloads constructor(
        query: Query,
        val objectClass: Class<T>,
        val properties: List<PropertyDescription>,
        countMaxResults: Int = DefaultCountMaxResults,
        sortFields: List<SortField> = listOf(),
        /**
         * Only needed when mapping lazily.
         */
        val countResultToPreload: Int = DefaultCountResultsToPreload
) : SearchConfig(query, countMaxResults, sortFields) {

    companion object {
        const val DefaultCountResultsToPreload = 10
    }

}
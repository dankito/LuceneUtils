package net.dankito.utils.lucene.search

import org.apache.lucene.search.Query
import org.apache.lucene.search.SortField


open class SearchConfig @JvmOverloads constructor(
    val query: Query,
    val countMaxResults: Int = DefaultCountMaxResults,
    val sortFields: Collection<SortField> = emptyList()
) {

    companion object {
        const val DefaultCountMaxResults = 10_000
    }


    @JvmOverloads
    constructor(query: Query, countMaxResults: Int = DefaultCountMaxResults, vararg sortFields: SortField) :
            this(query, countMaxResults, sortFields.toList())


    open val hasNoSortFields: Boolean
        get() = sortFields.isEmpty()

}
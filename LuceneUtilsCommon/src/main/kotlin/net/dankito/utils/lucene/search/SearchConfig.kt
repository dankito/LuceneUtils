package net.dankito.utils.lucene.search

import org.apache.lucene.search.Query
import org.apache.lucene.search.SortField


open class SearchConfig @JvmOverloads constructor(
    val query: Query,
    val countMaxResults: Int = DefaultCountMaxResults,
    val sortFields: List<SortField> = listOf()
) {

    companion object {
        const val DefaultCountMaxResults = 10_000
    }


    open val hasNoSortFields: Boolean
        get() = sortFields.isEmpty()

}
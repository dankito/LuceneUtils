package net.dankito.utils.lucene.sort

import org.apache.lucene.search.SortField


class StringSortField @JvmOverloads constructor(
        fieldName: String,
        sortDescending: Boolean = false
) : SortField(fieldName, Type.STRING, sortDescending)

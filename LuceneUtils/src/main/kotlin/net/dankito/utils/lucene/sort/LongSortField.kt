package net.dankito.utils.lucene.sort

import org.apache.lucene.search.SortedNumericSortField


open class LongSortField @JvmOverloads constructor(
        fieldName: String,
        sortDescending: Boolean = false
) : SortedNumericSortField(fieldName, Type.LONG, sortDescending)

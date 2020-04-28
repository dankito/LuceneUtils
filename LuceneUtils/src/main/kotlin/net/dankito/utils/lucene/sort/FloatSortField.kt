package net.dankito.utils.lucene.sort

import org.apache.lucene.search.SortedNumericSortField


open class FloatSortField @JvmOverloads constructor(
        fieldName: String,
        sortDescending: Boolean = false
) : SortedNumericSortField(fieldName, Type.FLOAT, sortDescending)

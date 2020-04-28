package net.dankito.utils.lucene.sort

import org.apache.lucene.search.SortField


open class FloatSortField @JvmOverloads constructor(
        fieldName: String,
        sortDescending: Boolean = false
) : SortField(fieldName, Type.FLOAT, sortDescending)

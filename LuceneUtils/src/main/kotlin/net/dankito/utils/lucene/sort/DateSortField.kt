package net.dankito.utils.lucene.sort


open class DateSortField @JvmOverloads constructor(
        fieldName: String,
        sortDescending: Boolean = false
) : LongSortField(fieldName, sortDescending)

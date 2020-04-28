package net.dankito.utils.lucene

import net.dankito.utils.lucene.sort.*
import org.apache.lucene.search.SortField


class SortFieldTest : SortFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }


    override fun createIntSortField(fieldName: String, sortDescending: Boolean): SortField {
        return IntSortField(fieldName, sortDescending)
    }

    override fun createLongSortField(fieldName: String, sortDescending: Boolean): SortField {
        return LongSortField(fieldName, sortDescending)
    }

    override fun createFloatSortField(fieldName: String, sortDescending: Boolean): SortField {
        return FloatSortField(fieldName, sortDescending)
    }

    override fun createDoubleSortField(fieldName: String, sortDescending: Boolean): SortField {
        return DoubleSortField(fieldName, sortDescending)
    }

    override fun createDateSortField(fieldName: String, sortDescending: Boolean): SortField {
        return DateSortField(fieldName, sortDescending)
    }

}
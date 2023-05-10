package net.dankito.utils.lucene

import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import net.dankito.utils.lucene.search.QueryBuilder
import net.dankito.utils.lucene.search.Searcher
import net.dankito.utils.lucene.sort.*
import org.apache.lucene.search.SortField


class SortFieldTest : SortFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<FieldBuilder, DocumentsWriter, QueryBuilder, Searcher> {
        return LuceneTestInstancesCreator()
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
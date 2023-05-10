package net.dankito.utils.lucene

import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import net.dankito.utils.lucene.search.QueryBuilder
import net.dankito.utils.lucene.search.Searcher


class ListFieldTest : ListFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<FieldBuilder, DocumentsWriter, QueryBuilder, Searcher> {
        return LuceneTestInstancesCreator()
    }

}
package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.ILuceneTestInstancesCreator
import net.dankito.utils.lucene.LuceneTestInstancesCreator
import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder


class SearcherTest : SearcherTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<FieldBuilder, DocumentsWriter, QueryBuilder, Searcher> {
        return LuceneTestInstancesCreator()
    }

}
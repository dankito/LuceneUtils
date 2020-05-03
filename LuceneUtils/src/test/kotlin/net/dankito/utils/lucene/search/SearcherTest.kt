package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.ILuceneTestInstancesCreator
import net.dankito.utils.lucene.LuceneTestInstancesCreator


class SearcherTest : SearcherTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return LuceneTestInstancesCreator()
    }

}
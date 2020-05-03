package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.ILuceneTestInstancesCreator
import net.dankito.utils.lucene.Lucene4TestInstancesCreator


class SearcherTest : SearcherTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.ILuceneTestInstancesCreator
import net.dankito.utils.lucene.Lucene4TestInstancesCreator
import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4SearcherTest")
class SearcherTest : SearcherTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
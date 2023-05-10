package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.ILuceneTestInstancesCreator
import net.dankito.utils.lucene.Lucene4TestInstancesCreator
import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4SearcherTest")
class SearcherTest : SearcherTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<FieldBuilder, DocumentsWriter, QueryBuilder, Searcher> {
        return Lucene4TestInstancesCreator()
    }

}
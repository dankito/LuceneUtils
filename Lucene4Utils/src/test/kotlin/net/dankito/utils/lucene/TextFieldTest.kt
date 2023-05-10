package net.dankito.utils.lucene

import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import net.dankito.utils.lucene.search.QueryBuilder
import net.dankito.utils.lucene.search.Searcher
import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4TextFieldTest")
class TextFieldTest : TextFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<FieldBuilder, DocumentsWriter, QueryBuilder, Searcher> {
        return Lucene4TestInstancesCreator()
    }

}
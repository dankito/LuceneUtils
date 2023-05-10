package net.dankito.utils.lucene

import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4DocumentsWriterTest")
class DocumentsWriterTest : DocumentsWriterTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
package net.dankito.utils.lucene


class DocumentsWriterTest : DocumentsWriterTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
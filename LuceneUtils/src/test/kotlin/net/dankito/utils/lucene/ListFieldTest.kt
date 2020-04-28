package net.dankito.utils.lucene


class ListFieldTest : ListFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return LuceneTestInstancesCreator()
    }

}
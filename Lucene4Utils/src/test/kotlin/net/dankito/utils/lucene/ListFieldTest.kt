package net.dankito.utils.lucene

import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4ListFieldTest")
class ListFieldTest : ListFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
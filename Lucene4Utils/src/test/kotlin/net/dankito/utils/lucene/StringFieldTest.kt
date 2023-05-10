package net.dankito.utils.lucene

import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4StringFieldTest")
open class StringFieldTest : StringFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
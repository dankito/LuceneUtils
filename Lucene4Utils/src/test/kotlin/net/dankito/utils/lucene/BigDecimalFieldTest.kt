package net.dankito.utils.lucene

import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4BigDecimalFieldTest")
class BigDecimalFieldTest : BigDecimalFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
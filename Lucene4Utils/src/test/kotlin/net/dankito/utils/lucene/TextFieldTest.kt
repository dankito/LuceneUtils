package net.dankito.utils.lucene

import org.junit.jupiter.api.DisplayName

@DisplayName("Lucene4TextFieldTest")
class TextFieldTest : TextFieldTestBase() {

    override fun createInstancesCreator(): ILuceneTestInstancesCreator<*, *, *, *> {
        return Lucene4TestInstancesCreator()
    }

}
package net.dankito.utils.lucene

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*


class ListFieldTest : LuceneTestBase() {

    companion object {
        private val TestValueStart = "TestValue"

        private val StringFieldValues = IntRange(1, 5).map { TestValueStart + it }
    }


    @Test
    fun indexList_StringField() {

        // given
        indexStringField(FieldName, StringFieldValues)

        // when
        val query = queries.startsWith(FieldName, TestValueStart, false)
        val result = search(query)

        // then
        assertThat(result).hasSize(1)

        assertThat(mapper.stringList(result.first(), FieldName)).containsExactly(*StringFieldValues.toTypedArray())
    }

    @Test
    fun indexList_TextField() {

        // given
        indexTextField(FieldName, StringFieldValues)

        // when
        val query = queries.startsWith(FieldName, TestValueStart)
        val result = search(query)

        // then
        assertThat(result).hasSize(1)

        assertThat(mapper.stringList(result.first(), FieldName)).containsExactly(*StringFieldValues.toTypedArray())
    }


    @Test
    fun indexList_IntField() {

        // given
        val values = IntRange(1, 5).toList()
//        index(fields.intField(FieldName, values)) // TODO: Ints only get indexed but not stored -> cannot be retrieved again?
        index(fields.storedIntListField(FieldName, values))

        // when
        val result = search(queries.allDocuments()) // TODO: create queries for Ints

        // then
        assertThat(result).hasSize(1)

        assertThat(mapper.intList(result.first(), FieldName)).containsExactly(*values.toTypedArray())
    }

    @Test
    fun indexList_LongField() {

        // given
        val values = LongRange(1, 5).toList()
//        index(fields.longField(FieldName, values)) // TODO: Longs only get indexed but not stored -> cannot be retrieved again?
        index(fields.storedLongListField(FieldName, values))

        // when
        val result = search(queries.allDocuments()) // TODO: create queries for Longs

        // then
        assertThat(result).hasSize(1)

        assertThat(mapper.longList(result.first(), FieldName)).containsExactly(*values.toTypedArray())
    }

    @Test
    fun indexList_FloatField() {

        // given
        val values = IntRange(1, 5).map { it.toFloat() }
//        index(fields.floatField(FieldName, values)) // TODO: Floats only get indexed but not stored -> cannot be retrieved again?
        index(fields.storedFloatListField(FieldName, values))

        // when
        val result = search(queries.allDocuments()) // TODO: create queries for Floats

        // then
        assertThat(result).hasSize(1)

        assertThat(mapper.floatList(result.first(), FieldName)).containsExactly(*values.toTypedArray())
    }

    @Test
    fun indexList_DoubleField() {

        // given
        val values = IntRange(1, 5).map { it.toDouble() }
//        index(fields.doubleField(FieldName, values)) // TODO: Doubles only get indexed but not stored -> cannot be retrieved again?
        index(fields.storedDoubleListField(FieldName, values))

        // when
        val result = search(queries.allDocuments()) // TODO: create queries for Doubles

        // then
        assertThat(result).hasSize(1)

        assertThat(mapper.doubleList(result.first(), FieldName)).containsExactly(*values.toTypedArray())
    }

    @Test
    fun indexList_DateField() {

        // given
        val values = LongRange(1, 5).map { Date(it) }
//        index(fields.dateTimeField(FieldName, values)) // TODO: Dates (Longs) only get indexed but not stored -> cannot be retrieved again?
        index(fields.storedDateListField(FieldName, values))

        // when
        val result = search(queries.allDocuments()) // TODO: create queries for Dates

        // then
        assertThat(result).hasSize(1)

        assertThat(mapper.dateList(result.first(), FieldName)).containsExactly(*values.toTypedArray())
    }

}
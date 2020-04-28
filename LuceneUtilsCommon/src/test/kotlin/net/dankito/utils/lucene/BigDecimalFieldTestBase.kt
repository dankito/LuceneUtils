package net.dankito.utils.lucene

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal


abstract class BigDecimalFieldTestBase : LuceneTestBase() {

    @Test
    fun noDecimalPlaces() {

        // given
        val value = BigDecimal("1")
        val precision = 0

        index(value, precision)


        // when
        val result = search(value, precision)


        // then
        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(value)
    }

    @Test
    fun noDecimalPlacesButStoredWithPrecisionOfTwo() {

        // given
        val value = BigDecimal("1")
        val expected = BigDecimal("1.00")
        val precision = 2

        index(value, precision)


        // when
        val result = search(value, precision)


        // then
        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(expected)
    }

    @Test
    fun twoDecimalPlaces() {

        // given
        val value = BigDecimal("1.49")
        val precision = 2

        index(value, precision)


        // when
        val result = search(value, precision)


        // then
        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(value)
    }

    @Test
    fun threeDecimalPlacesButStoredWithPrecisionOfTwo() {

        // given
        val value = BigDecimal("1.234")
        val expected = BigDecimal("1.23")
        val precision = 2

        index(value, precision)


        // when
        val result = search(value, precision)


        // then
        assertThat(result).hasSize(1)
        assertThat(result.first()).isEqualTo(expected)
    }


    private fun index(value: BigDecimal, precision: Int) {
        index(listOf(
            fields.bigDecimalField(FieldName, value, precision),
            fields.storedField(StoredFieldName, value, precision)
        ))
    }

    private fun search(value: BigDecimal, precision: Int): List<BigDecimal> {
        val searchResults = search(queries.matches(FieldName, value, precision))

        return searchResults.map { mapper.bigDecimal(it, StoredFieldName, precision) }
    }

}
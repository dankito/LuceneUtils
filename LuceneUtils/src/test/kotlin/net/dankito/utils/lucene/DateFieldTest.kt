package net.dankito.utils.lucene

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


open class DateFieldTest : LuceneTestBase() {

	companion object {
		val Date = java.util.Date(88, 2, 27)
	}


	@Test
	fun `Exact date`() {

		// given
		index(fields.dateField(FieldName, Date))

		// when
		val result = search(queries.matches(FieldName, Date))

		// then
		assertThat(result).hasSize(1)
	}

	@Test
	fun `Date after`() {

		// given
		index(fields.dateField(FieldName, Date))

		// when
		val result = search(queries.afterDate(FieldName, Date))

		// then
		assertThat(result).hasSize(1)
	}

	@Test
	fun `Date before`() {

		// given
		index(fields.dateField(FieldName, Date))

		// when
		val result = search(queries.beforeDate(FieldName, Date))

		// then
		assertThat(result).hasSize(1)
	}

	@Test
	fun `Date after out of range`() {

		// given
		index(fields.dateField(FieldName, Date))

		// when
		val result = search(queries.afterDate(FieldName, java.util.Date(Date.time + 1)))

		// then
		assertThat(result).isEmpty()
	}

	@Test
	fun `Date before out of range`() {

		// given
		index(fields.dateField(FieldName, Date))

		// when
		val result = search(queries.beforeDate(FieldName, java.util.Date(Date.time - 1)))

		// then
		assertThat(result).isEmpty()
	}

	@Test
	fun `Date between`() {

		// given
		index(fields.dateField(FieldName, Date))

		// when
		val result = search(queries.between(FieldName, java.util.Date(Date.time - 1), java.util.Date(Date.time + 1)))

		// then
		assertThat(result).hasSize(1)
	}

}
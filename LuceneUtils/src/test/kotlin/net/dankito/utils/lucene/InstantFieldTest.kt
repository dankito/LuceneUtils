package net.dankito.utils.lucene

import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import net.dankito.utils.lucene.search.QueryBuilder
import net.dankito.utils.lucene.search.Searcher
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.ZoneOffset

class InstantFieldTest: LuceneTestBase() {

	override fun createInstancesCreator(): ILuceneTestInstancesCreator<FieldBuilder, DocumentsWriter, QueryBuilder, Searcher> {
		return LuceneTestInstancesCreator()
	}

	companion object {
		val Instant = LocalDate.of(1988, 3, 27).atTime(0, 0).toInstant(ZoneOffset.UTC)
	}

	private val fieldBuilder = FieldBuilder()

	private val queriesBuilder = QueryBuilder()


	@Test
	fun `Matches Instant`() {

		// given
		index(fieldBuilder.instantField(FieldName, Instant))

		// when
		val result = search(queriesBuilder.matches(FieldName, Instant))

		// then
		assertThat(result).hasSize(1)
	}

	@Test
	fun `Instant after`() {

		// given
		index(fieldBuilder.instantField(FieldName, Instant))

		// when
		val result = search(queriesBuilder.after(FieldName, Instant))

		// then
		assertThat(result).hasSize(1)
	}

	@Test
	fun `Instant before`() {

		// given
		index(fieldBuilder.instantField(FieldName, Instant))

		// when
		val result = search(queriesBuilder.before(FieldName, Instant))

		// then
		assertThat(result).hasSize(1)
	}

	@Test
	fun `Instant after out of range`() {

		// given
		index(fieldBuilder.instantField(FieldName, Instant))

		// when
		val result = search(queriesBuilder.after(FieldName, java.time.Instant.ofEpochMilli(Instant.toEpochMilli() + 1)))

		// then
		assertThat(result).isEmpty()
	}

	@Test
	fun `Instant before out of range`() {

		// given
		index(fieldBuilder.instantField(FieldName, Instant))

		// when
		val result = search(queriesBuilder.before(FieldName, java.time.Instant.ofEpochMilli(Instant.toEpochMilli() - 1)))

		// then
		assertThat(result).isEmpty()
	}

	@Test
	fun `Instant between`() {

		// given
		index(fieldBuilder.instantField(FieldName, Instant))

		// when
		val result = search(queriesBuilder.between(FieldName, java.time.Instant.ofEpochMilli(Instant.toEpochMilli() - 1), java.time.Instant.ofEpochMilli(Instant.toEpochMilli() + 1)))

		// then
		assertThat(result).hasSize(1)
	}

}
package net.dankito.utils.lucene

import net.dankito.utils.datetime.asUtilDate
import net.dankito.utils.lucene.search.SearchConfig
import net.dankito.utils.lucene.sort.StringSortField
import org.apache.lucene.search.SortField
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate


abstract class SortFieldTestBase : LuceneTestBase() {

    protected abstract fun createIntSortField(fieldName: String, sortDescending: Boolean = false): SortField

    protected abstract fun createLongSortField(fieldName: String, sortDescending: Boolean = false): SortField

    protected abstract fun createFloatSortField(fieldName: String, sortDescending: Boolean = false): SortField

    protected abstract fun createDoubleSortField(fieldName: String, sortDescending: Boolean = false): SortField

    protected abstract fun createDateSortField(fieldName: String, sortDescending: Boolean = false): SortField


    @Test
    fun sortString() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, "/home/user/music/Music.mp3")
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, "/home/user/docs/book.pdf")
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, "/home/user/pics/holiday.jpg")
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, "/home/user/docs/Document.odt")
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(StringSortField(SortFieldName))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(1, 2, 3, 4)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(1, 2, 3, 4)
    }

    @Test
    fun sortStringDescending() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, "/home/user/music/Music.mp3")
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, "/home/user/docs/book.pdf")
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, "/home/user/pics/holiday.jpg")
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, "/home/user/docs/Document.odt")
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(StringSortField(SortFieldName, true))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(4, 3, 2, 1)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(4, 3, 2, 1)
    }

    @Test
    fun sortInt() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, 3)
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, 1)
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, 4)
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, 2)
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(createIntSortField(SortFieldName))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(1, 2, 3, 4)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(1, 2, 3, 4)
    }

    @Test
    fun sortLong() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, 3L)
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, 1L)
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, 4L)
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, 2L)
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(createLongSortField(SortFieldName))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(1, 2, 3, 4)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(1, 2, 3, 4)
    }

    @Test
    fun sortFloat() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, 3.14f)
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, 1.5f)
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, 4.3f)
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, 2.75f)
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(createFloatSortField(SortFieldName))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(1, 2, 3, 4)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(1, 2, 3, 4)
    }

    @Test
    fun sortDouble() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, 3.14)
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, 1.5)
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, 4.3)
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, 2.75)
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(createDoubleSortField(SortFieldName))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(1, 2, 3, 4)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(1, 2, 3, 4)
    }

    @Test
    fun sortDate() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, LocalDate.of(2023, 3, 3).asUtilDate())
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, LocalDate.of(2001, 1, 1).asUtilDate())
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, LocalDate.of(2034, 4, 4).asUtilDate())
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, LocalDate.of(2012, 2, 2).asUtilDate())
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(createDateSortField(SortFieldName))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(1, 2, 3, 4)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(1, 2, 3, 4)
    }

    @Test
    fun sortDateDescending() {

        index(listOf(
            fields.storedField(FieldName, 3),
            fields.sortField(SortFieldName, LocalDate.of(2023, 3, 3).asUtilDate())
        ))

        index(listOf(
            fields.storedField(FieldName, 1),
            fields.sortField(SortFieldName, LocalDate.of(2001, 1, 1).asUtilDate())
        ))

        index(listOf(
            fields.storedField(FieldName, 4),
            fields.sortField(SortFieldName, LocalDate.of(2034, 4, 4).asUtilDate())
        ))

        index(listOf(
            fields.storedField(FieldName, 2),
            fields.sortField(SortFieldName, LocalDate.of(2012, 2, 2).asUtilDate())
        ))


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(createDateSortField(SortFieldName, true))))


        // then
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.int(it, FieldName) }).containsExactly(4, 3, 2, 1)

        // compare with unsorted result
        val unsortedResult = searcher.search(queries.allDocuments())

        assertThat(unsortedResult.totalHits).isEqualTo(4)
        assertThat(unsortedResult.hits.map { mapper.int(it, FieldName) }).doesNotContainSequence(4, 3, 2, 1)
    }

}
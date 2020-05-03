package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.LuceneTestBase
import net.dankito.utils.lucene.sort.StringSortField
import net.dankito.utils.lucene.utils.StringTestObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


abstract class SearcherTestBase : LuceneTestBase() {

    @Test
    fun search() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.search(queries.allDocuments())


        // then
        assertThat(result.hits).hasSize(4)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, FieldName) }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun search_countMaxResults() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.search(queries.allDocuments(), 2)


        // then
        assertThat(result.hits).hasSize(2)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, FieldName) }).containsExactly("1", "2")
    }

    @Test
    fun search_sortedDescending() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.search(queries.allDocuments(), sortFields = listOf(StringSortField(FieldName, true)))


        // then
        assertThat(result.hits).hasSize(4)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, FieldName) }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun search_countMaxResultsAndSortedDescending() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.search(queries.allDocuments(), 2, listOf(StringSortField(FieldName, true)))


        // then
        assertThat(result.hits).hasSize(2)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, FieldName) }).containsExactly("4", "3")
    }


    @Test
    fun searchAndMap() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMap(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties)


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.name }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun searchAndMap_countMaxResults() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMap(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, 2)


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.name }).containsExactly("1", "2")
    }

    @Test
    fun searchAndMap_sortedDescending() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMap(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                sortFields = listOf(StringSortField(FieldName, true)))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.name }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun searchAndMap_countMaxResultsAndSortedDescending() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMap(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                2, listOf(StringSortField(FieldName, true)))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.name }).containsExactly("4", "3")
    }


    @Test
    fun searchAndMapLazily() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMapLazily(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties)


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.name }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun searchAndMapLazily_countMaxResults() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMapLazily(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, 2, 0)


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.name }).containsExactly("1", "2")
    }

    @Test
    fun searchAndMapLazily_sortedDescending() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMapLazily(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                countResultToPreload = 0, sortFields = listOf(StringSortField(FieldName, true)))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.name }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun searchAndMapLazily_countMaxResultsAndSortedDescending() {

        // given
        indexString("1")
        indexString("2")
        indexString("3")
        indexString("4")


        // when
        val result = searcher.searchAndMapLazily(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                2, 0, listOf(StringSortField(FieldName, true)))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.name }).containsExactly("4", "3")
    }

}
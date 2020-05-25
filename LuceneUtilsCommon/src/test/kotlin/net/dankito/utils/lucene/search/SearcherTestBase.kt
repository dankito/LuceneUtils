package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.Constants.Companion.IdFieldName
import net.dankito.utils.lucene.LuceneTestBase
import net.dankito.utils.lucene.cache.MapBasedCache
import net.dankito.utils.lucene.sort.StringSortField
import net.dankito.utils.lucene.utils.StringTestObject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


abstract class SearcherTestBase : LuceneTestBase() {

    protected val fieldName = IdFieldName


    @Test
    fun search() {

        // given
        index4Items()


        // when
        val result = searcher.search(queries.allDocuments())


        // then
        assertThat(result.hits).hasSize(4)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, fieldName) }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun search_countMaxResults() {

        // given
        index4Items()


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), 2))


        // then
        assertThat(result.hits).hasSize(2)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, fieldName) }).containsExactly("1", "2")
    }

    @Test
    fun search_sortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), sortFields = listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result.hits).hasSize(4)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, fieldName) }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun search_countMaxResultsAndSortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.search(SearchConfig(queries.allDocuments(), 2, listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result.hits).hasSize(2)
        assertThat(result.totalHits).isEqualTo(4)

        assertThat(result.hits.map { mapper.string(it, fieldName) }).containsExactly("4", "3")
    }


    @Test
    fun searchAndMap() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMap(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun searchAndMap_countMaxResults() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMap(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, 2))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("1", "2")
    }

    @Test
    fun searchAndMap_sortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMap(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                sortFields = listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun searchAndMap_countMaxResultsAndSortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMap(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                2, sortFields = listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("4", "3")
    }


    @Test
    fun searchAndMapLazily() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapLazily(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun searchAndMapLazily_countMaxResults() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapLazily(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, 2, countResultToPreload = 0))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("1", "2")
    }

    @Test
    fun searchAndMapLazily_sortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapLazily(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                sortFields = listOf(StringSortField(fieldName, true)), countResultToPreload = 0))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun searchAndMapLazily_countMaxResultsAndSortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapLazily(MappedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                2, listOf(StringSortField(fieldName, true)), 0))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("4", "3")
    }


    @Test
    fun searchAndMapCached() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCached(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, MapBasedCache()))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun searchAndMapCached_countMaxResults() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCached(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, MapBasedCache(), 2))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("1", "2")
    }

    @Test
    fun searchAndMapCached_sortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCached(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                MapBasedCache(), sortFields = listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun searchAndMapCached_countMaxResultsAndSortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCached(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                MapBasedCache(), 2, sortFields = listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("4", "3")
    }


    @Test
    fun searchAndMapCachedLazily() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCachedLazily(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, MapBasedCache()))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("1", "2", "3", "4")
    }

    @Test
    fun searchAndMapCachedLazily_countMaxResults() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCachedLazily(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties, MapBasedCache(), 2))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("1", "2")
    }

    @Test
    fun searchAndMapCachedLazily_sortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCachedLazily(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                MapBasedCache(), sortFields = listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result).hasSize(4)

        assertThat(result.map { it.id }).containsExactly("4", "3", "2", "1")
    }

    @Test
    fun searchAndMapCachedLazily_countMaxResultsAndSortedDescending() {

        // given
        index4Items()


        // when
        val result = searcher.searchAndMapCachedLazily(MapCachedSearchConfig(queries.allDocuments(), StringTestObject::class.java, StringTestObjectProperties,
                MapBasedCache(), 2, sortFields = listOf(StringSortField(fieldName, true))))


        // then
        assertThat(result).hasSize(2)

        assertThat(result.map { it.id }).containsExactly("4", "3")
    }


    private fun index4Items() {
        indexAsStrings(IntRange(1, 4))
    }

    private fun indexAsStrings(intRange: IntRange) {
        intRange.forEach {
            indexString(fieldName, it.toString())
        }
    }

}
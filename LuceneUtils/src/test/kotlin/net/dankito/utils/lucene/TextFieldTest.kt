package net.dankito.utils.lucene

import org.apache.lucene.index.Term
import org.apache.lucene.search.PrefixQuery
import org.apache.lucene.search.TermQuery
import org.apache.lucene.search.WildcardQuery
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class TextFieldTest : LuceneTestBase() {


    @Test
    fun `TermQuery - Only lower case search works`() {

        // given
        indexTextField(FieldName, "Mahatma Gandhi")

        // when
        val query = TermQuery(Term(FieldName, "mahatma gandhi"))
        val result = search(query)

        // then
        Assertions.assertThat(result).isEmpty()
    }

    @Test
    fun `TermQuery - Upper case search does not work`() {

        // given
        indexTextField(FieldName, "Mahatma Gandhi")

        // when
        val query = TermQuery(Term(FieldName, "Mahatma Gandhi"))
        val result = search(query)

        // then
        Assertions.assertThat(result).isEmpty()
    }

    @Test
    fun `TermQuery - single term matches`() {

        // given
        indexTextField(FieldName, "Mahatma Gandhi")

        // when
        val query = TermQuery(Term(FieldName, "gandhi"))
        val result = search(query)

        // then
        Assertions.assertThat(result).hasSize(1)
    }

    @Test
    fun `PrefixQuery ('startsWith()') matches`() {

        // given
        indexTextField(FieldName, "Mahatma Gandhi")

        // when
        val query = PrefixQuery(Term(FieldName, "mahatma"))
        val result = search(query)

        // then
        Assertions.assertThat(result).hasSize(1)
    }

    @Test
    fun `WildcardQuery ('contains()') matches`() {

        // given
        indexTextField(FieldName, "Mahatma Gandhi")

        // when
        val query = WildcardQuery(Term(FieldName, "*gand*"))
        val result = search(query)

        // then
        Assertions.assertThat(result).hasSize(1)
    }

    @Test
    fun `WildcardQuery ('endsWith()') matches`() {

        // given
        indexTextField(FieldName, "Mahatma Gandhi")

        // when
        val query = WildcardQuery(Term(FieldName, "*gandhi"))
        val result = search(query)

        // then
        Assertions.assertThat(result).hasSize(1)
    }
    
}
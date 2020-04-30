package net.dankito.utils.lucene

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


abstract class DocumentsWriterTestBase : LuceneTestBase() {

    companion object {
        const val Id1FieldValue = "Id1"
        const val Id2FieldValue = "Id2"
        const val Id3FieldValue = "Id3"
        const val Id4FieldValue = "Id4"
    }


    @Test
    fun deleteDocument() {

        // given
        indexString(Id1FieldValue)

        val searchResultBefore = searcher.search(queries.exact(FieldName, Id1FieldValue))
        assertThat(searchResultBefore.totalHits).isEqualTo(1)


        // when
        writer.deleteDocumentAndFlushChangesToDisk(FieldName, Id1FieldValue)


        // then
        val searchResultAfter = searcher.search(queries.exact(FieldName, Id1FieldValue))
        assertThat(searchResultAfter.totalHits).isEqualTo(0)
    }

    @Test
    fun deleteDocuments() {

        // given
        indexString(Id1FieldValue)
        indexString(Id2FieldValue)
        indexString(Id3FieldValue)
        indexString(Id4FieldValue)

        val searchResultBefore = searcher.search(queries.allDocuments())
        assertThat(searchResultBefore.totalHits).isEqualTo(4)


        // when
        writer.deleteDocumentsAndFlushChangesToDisk(FieldName, Id1FieldValue, Id2FieldValue, Id3FieldValue, Id4FieldValue)


        // then
        val searchResultAfter = searcher.search(queries.allDocuments())
        assertThat(searchResultAfter.totalHits).isEqualTo(0)
    }

    @Test
    fun deleteDocumentsPartially() {

        // given
        indexString(Id1FieldValue)
        indexString(Id2FieldValue)
        indexString(Id3FieldValue)
        indexString(Id4FieldValue)

        val searchResultBefore = searcher.search(queries.allDocuments())
        assertThat(searchResultBefore.totalHits).isEqualTo(4)


        // when
        writer.deleteDocumentsAndFlushChangesToDisk(FieldName, Id1FieldValue, Id3FieldValue)


        // then
        val searchResultAfter = searcher.search(queries.allDocuments())
        assertThat(searchResultAfter.totalHits).isEqualTo(2)

        assertThat(searchResultAfter.hits.map { mapper.string(it, FieldName) }).containsExactlyInAnyOrder(Id2FieldValue, Id4FieldValue)
    }

    @Test
    fun deleteDocumentsByQuery() {

        // given
        indexString(Id1FieldValue)
        indexString(Id2FieldValue)
        indexString(Id3FieldValue)
        indexString(Id4FieldValue)

        val searchResultBefore = searcher.search(queries.allDocuments())
        assertThat(searchResultBefore.totalHits).isEqualTo(4)


        // when
        writer.deleteDocumentsAndFlushChangesToDisk(queries.exact(FieldName, Id1FieldValue))
        writer.deleteDocumentsAndFlushChangesToDisk(queries.exact(FieldName, Id4FieldValue))


        // then
        val searchResultAfter = searcher.search(queries.allDocuments())
        assertThat(searchResultAfter.totalHits).isEqualTo(2)

        assertThat(searchResultAfter.hits.map { mapper.string(it, FieldName) }).containsExactlyInAnyOrder(Id2FieldValue, Id3FieldValue)
    }

}
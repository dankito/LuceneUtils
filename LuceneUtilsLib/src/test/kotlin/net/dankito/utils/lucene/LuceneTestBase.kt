package net.dankito.utils.lucene

import net.dankito.utils.io.FileUtils
import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import net.dankito.utils.lucene.search.QueryBuilder
import net.dankito.utils.lucene.search.SearchResult
import net.dankito.utils.lucene.search.Searcher
import org.apache.lucene.index.IndexableField
import org.apache.lucene.search.Query
import org.junit.jupiter.api.AfterEach


abstract class LuceneTestBase {

	companion object {
		const val FieldName = "test_field"
	}


	protected val fileUtils = FileUtils()

	protected val indexDirectory = fileUtils.createDirectoryInTempDir("LuceneUtilsTest")


	protected val fields = FieldBuilder()

	protected val queries = QueryBuilder()

	protected val writer = DocumentsWriter(indexDirectory)


	@AfterEach
	open fun tearDown() {
		writer.close()
	}


	protected open fun indexTextField(name: String, value: String, store: Boolean = true) {
		index(fields.textField(name, value, store))
	}

	protected open fun indexStringField(name: String, value: String, store: Boolean = true) {
		index(fields.stringField(name, value, store))
	}

	protected open fun index(field: IndexableField) {
		index(listOf(field))
	}

	protected open fun index(fields: List<IndexableField>) {
		writer.saveDocument(fields)

		writer.flushChangesToDisk()
	}


	protected open fun search(query: Query): List<SearchResult> {
		Searcher(indexDirectory).use { searcher ->
			val searchResults = searcher.search(query, 10)

			return searchResults.hits
		}
	}

}
package net.dankito.utils.lucene

import net.dankito.utils.io.FileUtils
import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import net.dankito.utils.lucene.search.FieldMapper
import net.dankito.utils.lucene.search.QueryBuilder
import net.dankito.utils.lucene.search.SearchResult
import net.dankito.utils.lucene.search.Searcher
import org.apache.lucene.index.IndexableField
import org.apache.lucene.search.Query
import org.junit.jupiter.api.AfterEach
import java.util.*


abstract class LuceneTestBase {

	companion object {
		const val FieldName = "test_field"
	}


	protected val fileUtils = FileUtils()

	protected val indexDirectory = fileUtils.createDirectoryInTempDir("LuceneUtilsTest")


	protected val fields = FieldBuilder()

	protected val queries = QueryBuilder()

	protected val mapper = FieldMapper()

	protected val writer = DocumentsWriter(indexDirectory)


	@AfterEach
	open fun tearDown() {
		writer.close()
	}


	protected open fun indexTextField(value: String, store: Boolean = true) {
		indexTextField(FieldName, value, store)
	}

	protected open fun indexTextField(name: String, value: String, store: Boolean = true) {
		index(fields.textField(name, value, store))
	}

	protected open fun indexTextField(values: List<String>, store: Boolean = true) {
		indexTextField(FieldName, values, store)
	}

	protected open fun indexTextField(name: String, values: List<String>, store: Boolean = true) {
		index(fields.textField(name, values, store))
	}

	protected open fun indexStringField(value: String, store: Boolean = true) {
		indexStringField(FieldName, value, store)
	}

	protected open fun indexStringField(name: String, value: String, store: Boolean = true) {
		index(fields.stringField(name, value, store))
	}

	protected open fun indexStringField(values: List<String>, store: Boolean = true) {
		indexStringField(FieldName, values, store)
	}

	protected open fun indexStringField(name: String, values: List<String>, store: Boolean = true) {
		index(fields.stringField(name, values, store))
	}


	protected open fun indexIntField(value: Int) {
		indexIntField(FieldName, value)
	}

	protected open fun indexIntField(name: String, value: Int) {
		index(fields.intField(name, value))
	}

	protected open fun indexIntField(values: List<Int>) {
		indexIntField(FieldName, values)
	}

	protected open fun indexIntField(name: String, values: List<Int>) {
		index(fields.intField(name, values))
	}


	protected open fun indexLongField(value: Long) {
		indexLongField(FieldName, value)
	}

	protected open fun indexLongField(name: String, value: Long) {
		index(fields.longField(name, value))
	}

	protected open fun indexLongField(values: List<Long>) {
		indexLongField(FieldName, values)
	}

	protected open fun indexLongField(name: String, values: List<Long>) {
		index(fields.longField(name, values))
	}


	protected open fun indexText(value: String) {
		indexTextField(FieldName, value)
	}

	protected open fun indexText(fieldName: String, value: String) {
		index(fields.textField(fieldName, value))
	}

	protected open fun indexString(value: String) {
		indexString(FieldName, value)
	}

	protected open fun indexString(fieldName: String, value: String) {
		index(fields.stringField(fieldName, value))
	}

	protected open fun index(value: Int) {
		index(FieldName, value)
	}

	protected open fun index(fieldName: String, value: Int) {
		index(fields.intField(fieldName, value))
	}

	protected open fun index(value: Long) {
		index(FieldName, value)
	}

	protected open fun index(fieldName: String, value: Long) {
		index(fields.longField(fieldName, value))
	}

	protected open fun index(value: Float) {
		index(FieldName, value)
	}

	protected open fun index(fieldName: String, value: Float) {
		index(fields.floatField(fieldName, value))
	}

	protected open fun index(value: Double) {
		index(FieldName, value)
	}

	protected open fun index(fieldName: String, value: Double) {
		index(fields.doubleField(fieldName, value))
	}

	protected open fun index(value: Date) {
		index(FieldName, value)
	}

	protected open fun index(fieldName: String, value: Date) {
		index(fields.dateField(fieldName, value))
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
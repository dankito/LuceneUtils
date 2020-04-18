package net.dankito.utils.lucene

import net.dankito.utils.lucene.index.FieldBuilderBase
import net.dankito.utils.lucene.search.QueryBuilderBase
import org.apache.lucene.document.Document
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexableField
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.Query
import org.apache.lucene.store.RAMDirectory
import org.junit.jupiter.api.AfterEach


abstract class LuceneTestBase<FieldBuilder : FieldBuilderBase<*, *, *, *>, QueryBuilder : QueryBuilderBase>(
		instanceCreator: ILuceneTestInstancesCreator<FieldBuilder, QueryBuilder>
) {

	companion object {
		const val FieldName = "test_field"
	}


	protected val directory = RAMDirectory()

	protected val analyzer = instanceCreator.createStandardAnalyzer()

	protected val writerConfig = instanceCreator.createIndexWriterConfig(analyzer)

	protected val writer = instanceCreator.createIndexWriter(directory, writerConfig)


	protected val fields = instanceCreator.createFieldBuilder()

	protected val queries = instanceCreator.createQueryBuilder()


	@AfterEach
	open fun tearDown() {
		writer.close()

		analyzer.close()

		directory.close()
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
		writer.addDocument(fields)

		writer.commit()
	}


	protected open fun search(query: Query): List<Document> {
		val reader = DirectoryReader.open(directory)
		val searcher = IndexSearcher(reader)

		val scoreDocs = searcher.search(query, 10).scoreDocs

		val result = scoreDocs.map { searcher.doc(it.doc) }

		reader.close()

		return result
	}

}
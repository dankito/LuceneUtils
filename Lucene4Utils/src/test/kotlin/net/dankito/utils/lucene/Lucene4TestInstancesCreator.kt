package net.dankito.utils.lucene

import net.dankito.utils.lucene.index.DocumentsWriter
import net.dankito.utils.lucene.index.FieldBuilder
import net.dankito.utils.lucene.search.QueryBuilder
import net.dankito.utils.lucene.search.Searcher
import java.io.File


class Lucene4TestInstancesCreator : ILuceneTestInstancesCreator<FieldBuilder, DocumentsWriter, QueryBuilder, Searcher> {

    override fun createFieldBuilder(): FieldBuilder {
        return FieldBuilder()
    }

    override fun createDocumentsWriter(indexDirectory: File): DocumentsWriter {
        return DocumentsWriter(indexDirectory)
    }


    override fun createQueryBuilder(): QueryBuilder {
        return QueryBuilder()
    }

    override fun createSearcher(indexDirectory: File): Searcher {
        return Searcher(indexDirectory)
    }

}
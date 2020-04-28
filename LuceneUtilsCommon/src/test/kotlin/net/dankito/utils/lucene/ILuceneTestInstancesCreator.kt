package net.dankito.utils.lucene

import net.dankito.utils.lucene.index.DocumentsWriterBase
import net.dankito.utils.lucene.index.FieldBuilderBase
import net.dankito.utils.lucene.search.QueryBuilderBase
import net.dankito.utils.lucene.search.SearcherBase
import java.io.File


interface ILuceneTestInstancesCreator<FieldBuilder : FieldBuilderBase, DocumentsWriter : DocumentsWriterBase,
        QueryBuilder : QueryBuilderBase, Searcher : SearcherBase> {


    fun createFieldBuilder(): FieldBuilder

    fun createDocumentsWriter(indexDirectory: File): DocumentsWriter


    fun createQueryBuilder(): QueryBuilder

    fun createSearcher(indexDirectory: File): Searcher

}
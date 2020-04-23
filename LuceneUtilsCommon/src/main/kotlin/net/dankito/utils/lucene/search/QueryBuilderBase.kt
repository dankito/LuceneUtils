package net.dankito.utils.lucene.search

import org.apache.lucene.index.Term
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.*


abstract class QueryBuilderBase {

	protected abstract fun createQueryForClauses(clauses: List<BooleanClause>): BooleanQuery


	open fun allDocuments(): Query {
		return MatchAllDocsQuery()
	}


	open fun createQueriesForSingleTerms(searchTerm: String, singleTermQueryBuilder: (singleTerm: String) -> List<Query>): Query {
		if (searchTerm.isBlank()) {
			return allDocuments()
		}

		val singleTerms = searchTerm.split(" ").filter { it.isNotBlank() }

		val clauses = singleTerms.map { singleTerm ->
			BooleanClause(createQueryForSingleTerm(singleTerm, singleTermQueryBuilder), BooleanClause.Occur.MUST)
		}

		return createQueryForClauses(clauses)
	}

	open fun createQueryForSingleTerm(singleTerm: String, singleTermQueryBuilder: (singleTerm: String) -> List<Query>): Query {
		val singleTermQueries = singleTermQueryBuilder(singleTerm)

		val clauses = singleTermQueries.map {
			BooleanClause(it, BooleanClause.Occur.SHOULD)
		}

		return createQueryForClauses(clauses)
	}


	/*		String queries		*/

	open fun fulltextQuery(fieldName: String, searchTerm: String): Query {
		return PrefixQuery(Term(fieldName, adjustSearchTerm(searchTerm, false)))
	}

	@JvmOverloads
	open fun exact(fieldName: String, searchTerm: String, caseInsensitive: Boolean = true): Query {
		return TermQuery(Term(fieldName, if (caseInsensitive) searchTerm.toLowerCase() else searchTerm))
	}

	@JvmOverloads
	open fun startsWith(fieldName: String, searchTerm: String, caseInsensitive: Boolean = true): Query {
		return wildcardQuery(fieldName, searchTerm, caseInsensitive, true, false)
	}

	@JvmOverloads
	open fun contains(fieldName: String, searchTerm: String, caseInsensitive: Boolean = true): Query {
		return wildcardQuery(fieldName, searchTerm, caseInsensitive)
	}

	@JvmOverloads
	open fun endsWith(fieldName: String, searchTerm: String, caseInsensitive: Boolean = true): Query {
		return wildcardQuery(fieldName, searchTerm, caseInsensitive, false, true)
	}

	@JvmOverloads
	open fun wildcardQuery(fieldName: String, searchTerm: String, caseInsensitive: Boolean = true): Query {
		return wildcardQuery(fieldName, searchTerm, caseInsensitive, true, true)
	}

	open fun wildcardQuery(fieldName: String, searchTerm: String, caseInsensitive: Boolean ,
						   prefixWildcard: Boolean, suffixWildcard: Boolean): Query {
		val adjustedSearchTerm = adjustSearchTermForWildcardQuery(searchTerm, caseInsensitive, prefixWildcard, suffixWildcard)

		return WildcardQuery(Term(fieldName, adjustedSearchTerm))
	}

	protected open fun adjustSearchTermForWildcardQuery(searchTerm: String, caseInsensitive: Boolean,
														prefixWildcard: Boolean, suffixWildcard: Boolean): String {
		val adjustedSearchTerm = adjustSearchTerm(searchTerm, caseInsensitive)

		if (prefixWildcard && suffixWildcard) {
			return "*$adjustedSearchTerm*"
		}
		else if (prefixWildcard) {
			return "$adjustedSearchTerm*"
		}
		else {
			return "*$adjustedSearchTerm"
		}
	}

	protected open fun adjustSearchTerm(searchTerm: String, caseInsensitive: Boolean): String {
		val adjustedSearchTerm = QueryParser.escape(searchTerm)

		return if (caseInsensitive) {
			adjustedSearchTerm.toLowerCase()
		}
		else {
			adjustedSearchTerm
		}
	}

}
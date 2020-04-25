package net.dankito.utils.lucene.search

import net.dankito.utils.lucene.Constants
import org.apache.lucene.index.Term
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.*
import java.math.BigDecimal
import java.util.*


abstract class QueryBuilderBase {

	protected abstract fun createQueryForClauses(clauses: List<BooleanClause>): BooleanQuery


	open fun allDocuments(): Query {
		return MatchAllDocsQuery()
	}

	open fun allDocumentsThatHaveField(fieldName: String): Query {
		return WildcardQuery(Term(fieldName, "*"))
	}

	open fun allDocumentsThatDoNotHaveField(fieldName: String): Query {
		// for an explanation see https://kb.ucla.edu/articles/pure-negation-query-in-lucene:
		// with an invert index one can only search for documents having a field, but not for documents 'not having field'.
		// So the trick is to get all documents and subtracting the ones we don't want.

		return createQueryForClauses(listOf(
			BooleanClause(allDocuments(), BooleanClause.Occur.MUST),
			BooleanClause(allDocumentsThatHaveField(fieldName), BooleanClause.Occur.MUST_NOT)
		))
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
	open fun exact(fieldName: String, searchTerm: String, caseInsensitive: Boolean = false): Query {
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


	/*		Numeric Queries		*/

	protected abstract fun matches(fieldName: String, value: Long): Query

	protected abstract fun greaterOrEqual(fieldName: String, value: Long): Query

	protected abstract fun lessOrEqual(fieldName: String, value: Long): Query

	protected abstract fun between(fieldName: String, lowerValueInclusive: Long, upperValueInclusive: Long): Query


	/**
	 * This only works if BigDecimal has been stored as Long.
	 *
	 * precision: The precision with which the BigDecimal has been stored as Long.
	 */
	open fun matches(fieldName: String, value: BigDecimal, precision: Int = Constants.DefaultBigDecimalPrecision): Query {
		return matches(fieldName, mapToLong(value, precision))
	}

	/**
	 * This only works if BigDecimal has been stored as Long.
	 *
	 * precision: The precision with which the BigDecimal has been stored as Long.
	 */
	open fun greaterOrEqual(fieldName: String, value: BigDecimal, precision: Int = Constants.DefaultBigDecimalPrecision): Query {
		return greaterOrEqual(fieldName, mapToLong(value, precision))
	}

	/**
	 * This only works if BigDecimal has been stored as Long.
	 *
	 * precision: The precision with which the BigDecimal has been stored as Long.
	 */
	open fun lessOrEqual(fieldName: String, value: BigDecimal, precision: Int = Constants.DefaultBigDecimalPrecision): Query {
		return lessOrEqual(fieldName, mapToLong(value, precision))
	}

	/**
	 * This only works if BigDecimal has been stored as Long.
	 *
	 * precision: The precision with which the BigDecimal has been stored as Long.
	 */
	open fun between(fieldName: String, lowerValueInclusive: BigDecimal, upperValueInclusive: BigDecimal, precision: Int = Constants.DefaultBigDecimalPrecision): Query {
		return between(fieldName, mapToLong(lowerValueInclusive, precision), mapToLong(upperValueInclusive, precision))
	}

	protected open fun mapToLong(value: BigDecimal, precision: Int): Long {
		return value.scaleByPowerOfTen(precision).toLong()
	}


	/*		Date queries		*/

	open fun matches(fieldName: String, date: Date): Query {
		return matches(fieldName, date.time)
	}

	open fun afterDate(fieldName: String, dateAfterThisInclusive: Date): Query {
		return greaterOrEqual(fieldName, dateAfterThisInclusive.time)
	}

	open fun beforeDate(fieldName: String, dateBeforeThisInclusive: Date): Query {
		return lessOrEqual(fieldName, dateBeforeThisInclusive.time)
	}

	open fun between(fieldName: String, dateAfterThisInclusive: Date, dateBeforeThisInclusive: Date): Query {
		return between(fieldName, dateAfterThisInclusive.time, dateBeforeThisInclusive.time)
	}

}
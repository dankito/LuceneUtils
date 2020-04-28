package net.dankito.utils.lucene.search

import org.apache.lucene.document.LongPoint
import org.apache.lucene.search.BooleanClause
import org.apache.lucene.search.BooleanQuery
import org.apache.lucene.search.Query
import java.util.*


open class QueryBuilder : QueryBuilderBase() {

	override fun createQueryForClauses(clauses: List<BooleanClause>): BooleanQuery {
		val builder = BooleanQuery.Builder()

		clauses.forEach { clause ->
			builder.add(clause)
		}

		return builder.build()
	}


	override fun matches(fieldName: String, value: Long): Query {
		return LongPoint.newExactQuery(fieldName,value)
	}

	override fun greaterOrEqual(fieldName: String, value: Long): Query {
		return LongPoint.newRangeQuery(fieldName, value, Long.MAX_VALUE)
	}

	override fun lessOrEqual(fieldName: String, value: Long): Query {
		return LongPoint.newRangeQuery(fieldName, Long.MIN_VALUE, value)
	}

	override fun between(fieldName: String, lowerValueInclusive: Long, upperValueInclusive: Long): Query {
		return LongPoint.newRangeQuery(fieldName, lowerValueInclusive, upperValueInclusive)
	}

}
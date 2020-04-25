package net.dankito.utils.lucene.search

import org.apache.lucene.search.BooleanClause
import org.apache.lucene.search.BooleanQuery
import org.apache.lucene.search.NumericRangeQuery
import org.apache.lucene.search.Query


open class QueryBuilder : QueryBuilderBase() {

	override fun createQueryForClauses(clauses: List<BooleanClause>): BooleanQuery {
		val builder = BooleanQuery()

		clauses.forEach { clause ->
			builder.add(clause)
		}

		return builder
	}


	override fun matches(fieldName: String, value: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, value, value, true, true)
	}

	override fun greaterOrEqual(fieldName: String, value: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, value, Long.MAX_VALUE, true, true)
	}

	override fun lessOrEqual(fieldName: String, value: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, Long.MIN_VALUE, value, true, true)
	}

	override fun between(fieldName: String, lowerValueInclusive: Long, upperValueInclusive: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, lowerValueInclusive, upperValueInclusive, true, true)
	}

}
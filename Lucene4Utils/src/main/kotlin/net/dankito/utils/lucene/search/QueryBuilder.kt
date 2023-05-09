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


	override fun matches(fieldName: String, value: Int): Query {
		return NumericRangeQuery.newIntRange(fieldName, value, value, true, true)
	}

	override fun matches(fieldName: String, value: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, value, value, true, true)
	}

	override fun matches(fieldName: String, value: Float): Query {
		return NumericRangeQuery.newFloatRange(fieldName, value, value, true, true)
	}

	override fun matches(fieldName: String, value: Double): Query {
		return NumericRangeQuery.newDoubleRange(fieldName, value, value, true, true)
	}

	override fun greaterOrEqual(fieldName: String, value: Int): Query {
		return NumericRangeQuery.newIntRange(fieldName, value, Int.MAX_VALUE, true, true)
	}

	override fun greaterOrEqual(fieldName: String, value: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, value, Long.MAX_VALUE, true, true)
	}

	override fun greaterOrEqual(fieldName: String, value: Float): Query {
		return NumericRangeQuery.newFloatRange(fieldName, value, Float.MAX_VALUE, true, true)
	}

	override fun greaterOrEqual(fieldName: String, value: Double): Query {
		return NumericRangeQuery.newDoubleRange(fieldName, value, Double.MAX_VALUE, true, true)
	}

	override fun lessOrEqual(fieldName: String, value: Int): Query {
		return NumericRangeQuery.newIntRange(fieldName, Int.MIN_VALUE, value, true, true)
	}

	override fun lessOrEqual(fieldName: String, value: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, Long.MIN_VALUE, value, true, true)
	}

	override fun lessOrEqual(fieldName: String, value: Float): Query {
		return NumericRangeQuery.newFloatRange(fieldName, Float.MIN_VALUE, value, true, true)
	}

	override fun lessOrEqual(fieldName: String, value: Double): Query {
		return NumericRangeQuery.newDoubleRange(fieldName, Double.MIN_VALUE, value, true, true)
	}

	override fun between(fieldName: String, lowerValueInclusive: Int, upperValueInclusive: Int): Query {
		return NumericRangeQuery.newIntRange(fieldName, lowerValueInclusive, upperValueInclusive, true, true)
	}

	override fun between(fieldName: String, lowerValueInclusive: Long, upperValueInclusive: Long): Query {
		return NumericRangeQuery.newLongRange(fieldName, lowerValueInclusive, upperValueInclusive, true, true)
	}

	override fun between(fieldName: String, lowerValueInclusive: Float, upperValueInclusive: Float): Query {
		return NumericRangeQuery.newFloatRange(fieldName, lowerValueInclusive, upperValueInclusive, true, true)
	}

	override fun between(fieldName: String, lowerValueInclusive: Double, upperValueInclusive: Double): Query {
		return NumericRangeQuery.newDoubleRange(fieldName, lowerValueInclusive, upperValueInclusive, true, true)
	}

}
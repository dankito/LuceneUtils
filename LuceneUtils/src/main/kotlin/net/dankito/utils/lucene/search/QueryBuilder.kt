package net.dankito.utils.lucene.search

import org.apache.lucene.document.DoublePoint
import org.apache.lucene.document.FloatPoint
import org.apache.lucene.document.IntPoint
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


	override fun matches(fieldName: String, value: Int): Query {
		return IntPoint.newExactQuery(fieldName,value)
	}

	override fun matches(fieldName: String, value: Long): Query {
		return LongPoint.newExactQuery(fieldName,value)
	}

	override fun matches(fieldName: String, value: Float): Query {
		return FloatPoint.newExactQuery(fieldName,value)
	}

	override fun matches(fieldName: String, value: Double): Query {
		return DoublePoint.newExactQuery(fieldName,value)
	}

	override fun greaterOrEqual(fieldName: String, value: Int): Query {
		return IntPoint.newRangeQuery(fieldName, value, Int.MAX_VALUE)
	}

	override fun greaterOrEqual(fieldName: String, value: Long): Query {
		return LongPoint.newRangeQuery(fieldName, value, Long.MAX_VALUE)
	}

	override fun greaterOrEqual(fieldName: String, value: Float): Query {
		return FloatPoint.newRangeQuery(fieldName, value, Float.MAX_VALUE)
	}

	override fun greaterOrEqual(fieldName: String, value: Double): Query {
		return DoublePoint.newRangeQuery(fieldName, value, Double.MAX_VALUE)
	}

	override fun lessOrEqual(fieldName: String, value: Int): Query {
		return IntPoint.newRangeQuery(fieldName, Int.MIN_VALUE, value)
	}

	override fun lessOrEqual(fieldName: String, value: Long): Query {
		return LongPoint.newRangeQuery(fieldName, Long.MIN_VALUE, value)
	}

	override fun lessOrEqual(fieldName: String, value: Float): Query {
		return FloatPoint.newRangeQuery(fieldName, Float.MIN_VALUE, value)
	}

	override fun lessOrEqual(fieldName: String, value: Double): Query {
		return DoublePoint.newRangeQuery(fieldName, Double.MIN_VALUE, value)
	}

	override fun between(fieldName: String, lowerValueInclusive: Int, upperValueInclusive: Int): Query {
		return IntPoint.newRangeQuery(fieldName, lowerValueInclusive, upperValueInclusive)
	}

	override fun between(fieldName: String, lowerValueInclusive: Long, upperValueInclusive: Long): Query {
		return LongPoint.newRangeQuery(fieldName, lowerValueInclusive, upperValueInclusive)
	}

	override fun between(fieldName: String, lowerValueInclusive: Float, upperValueInclusive: Float): Query {
		return FloatPoint.newRangeQuery(fieldName, lowerValueInclusive, upperValueInclusive)
	}

	override fun between(fieldName: String, lowerValueInclusive: Double, upperValueInclusive: Double): Query {
		return DoublePoint.newRangeQuery(fieldName, lowerValueInclusive, upperValueInclusive)
	}

}
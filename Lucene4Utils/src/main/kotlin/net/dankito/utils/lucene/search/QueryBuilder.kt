package net.dankito.utils.lucene.search

import org.apache.lucene.search.BooleanClause
import org.apache.lucene.search.BooleanQuery


open class QueryBuilder : QueryBuilderBase() {

	override fun createQueryForClauses(clauses: List<BooleanClause>): BooleanQuery {
		val builder = BooleanQuery()

		clauses.forEach { clause ->
			builder.add(clause)
		}

		return builder
	}


	// TODO:

	/*		Date queries		*/

//	open fun exactDateQuery(fieldName: String, date: Date): Query {
//		return LongPoint.newExactQuery(fieldName, date.time)
//	}
//
//	open fun afterDateQuery(fieldName: String, dateAfterThisInclusive: Date): Query {
//		return LongPoint.newRangeQuery(fieldName, dateAfterThisInclusive.time, Long.MAX_VALUE)
//	}
//
//	open fun beforeDateQuery(fieldName: String, dateBeforeThisInclusive: Date): Query {
//		return LongPoint.newRangeQuery(fieldName, Long.MIN_VALUE, dateBeforeThisInclusive.time)
//	}

}
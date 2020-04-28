package net.dankito.utils.lucene.index

import org.apache.lucene.document.*
import org.apache.lucene.util.NumericUtils


open class FieldBuilder : FieldBuilderBase() {

	override fun createIntField(name: String, value: Int): IntPoint {
		return IntPoint(name, value)
	}

	override fun createLongField(name: String, value: Long): LongPoint {
		return LongPoint(name, value)
	}

	override fun createFloatField(name: String, value: Float): FloatPoint {
		return FloatPoint(name, value)
	}

	override fun createDoubleField(name: String, value: Double): DoublePoint {
		return DoublePoint(name, value)
	}


	override fun sortField(name: String, value: Long): Field {
		return SortedNumericDocValuesField(name, value)
	}

}
package net.dankito.utils.lucene.index

import org.apache.lucene.document.*
import org.apache.lucene.util.NumericUtils


open class FieldBuilder : FieldBuilderBase<IntPoint, LongPoint, FloatPoint, DoublePoint>() {

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


	open fun sortField(name: String, value: Int): SortedNumericDocValuesField {
		return sortField(name, value.toLong())
	}

	open fun sortField(name: String, value: Long): SortedNumericDocValuesField {
		return SortedNumericDocValuesField(name, value)
	}

	open fun sortField(name: String, value: Float): SortedNumericDocValuesField {
		return sortField(name, NumericUtils.floatToSortableInt(value))
	}

	open fun sortField(name: String, value: Double): SortedNumericDocValuesField {
		return sortField(name, NumericUtils.doubleToSortableLong(value))
	}

}